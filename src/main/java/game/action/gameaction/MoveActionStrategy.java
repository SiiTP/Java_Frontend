package game.action.gameaction;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.server.GameServer;
import game.user.GameProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import persistance.UserProfile;
import resource.GameResources;
import resource.ResourceFactory;

import java.util.List;
import java.util.Random;

/**
 * Created by ivan on 25.10.15.
 */
public class MoveActionStrategy implements GameActionStrategy {
    private final GameServer gameServer;
    private final int width;
    private final int height;
    private final int speed;
    private final int radius;
    private final int defaultDontMoveValue;
    private final int collisionDelta;
    private static final Logger LOGGER = LogManager.getLogger(MoveActionStrategy.class);
    public MoveActionStrategy(GameServer gameServer) {
        this.gameServer = gameServer;
        GameResources gameResources =(GameResources) ResourceFactory.getResource(System.getProperty("user.dir")+"/config/game.json");
        collisionDelta = gameResources.getCollisionDelta();
        defaultDontMoveValue = gameResources.getDefaultStopDirectionValue();
        width = gameResources.getGameFieldWidth();
        height = gameResources.getGameFieldHeight();
        speed = gameResources.getDefaultSpeed();
        radius = gameResources.getDefaultPlayerRadius();
    }

    @Override
    public void processGameAction(@NotNull JSONObject message,@NotNull String httpSession) {
        Room room = gameServer.getPlayerRoomBySession(httpSession);
        if(room != null) {
            if (!room.isFinished()) {
                UserProfile profile = gameServer.getPlayerBySession(httpSession);
                if (profile != null) {
                    GameProfile gameProfile = profile.getGameProfile();
                    double dt = gameProfile.getDeltaTime();
                    double direction = getDrection(message);
                    gameProfile.setIsMoving(isMoving(message));
                    if (gameProfile.isMoving()) {
                        double radian = Math.toRadians(direction);
                        double x = gameProfile.getX();
                        double y = gameProfile.getY();
                        double newX = x + Math.cos(radian) * speed * dt;
                        double newY = y - Math.sin(radian) * speed * dt;
                        if(newX < width && newY < height){
                            if(newX > 0 && newY > 0) {
                                gameProfile.setX((int)newX);
                                gameProfile.setY((int)newY);
                            }
                        }
                        checkCollision(gameProfile, room);
                    }
                    gameProfile.checkIsKilled();
                    gameProfile.setDirection(direction);
                }
            }
        }
    }
    private double getDrection(JSONObject message){
        double direction = defaultDontMoveValue;
        if(message.has("direction")) {
            if(!message.isNull("direction")) {
                direction = message.getDouble("direction");
            }
        }
        return direction;
    }
    private boolean isMoving(JSONObject message){
        boolean moving = true;
        if(message.has("isMoving")){
            if(!message.isNull("isMoving")){
                moving = message.getBoolean("isMoving");
            }
        }
        return moving;
    }
    public void checkCollision(GameProfile gameProfile, @NotNull Room room){
        List<GameProfile> list = null;
        if (room instanceof RoomFFA) {
            RoomFFA roomFFA = (RoomFFA) room;
            list = roomFFA.getGameProfiles();
        }
        if (list != null) {
            list.stream().filter(enemy -> !enemy.equals(gameProfile) && !enemy.isKilled()).forEach(enemy -> {
                double xBetweenPlayers = enemy.getX() - gameProfile.getX();
                double yBetweenPlayers = enemy.getY() - gameProfile.getY();
                double myDirection = gameProfile.getDirection();
                if (xBetweenPlayers * xBetweenPlayers + yBetweenPlayers * yBetweenPlayers <= (4+2) * radius * radius) {
                    if(isCollisionAllowed(enemy,gameProfile)) {
                        enemy.setCollisionTimeStamp(System.currentTimeMillis());
                        gameProfile.setCollisionTimeStamp(System.currentTimeMillis());
                        double myProection = Math.toDegrees(getDegree(gameProfile, myDirection, xBetweenPlayers, -yBetweenPlayers));
                        double enemyProection = Math.toDegrees(getDegree(enemy, enemy.getDirection(), -xBetweenPlayers, yBetweenPlayers));
                        /*LOGGER.info(new MarkerManager.Log4jMarker("COLLISION"),"first score " + gameProfile.getScore() + "\n\t" + myProection
                                + ' ' + gameProfile.getDirection() + ' ' + gameProfile.getX() + ' ' + gameProfile.getY());
                        LOGGER.info(new MarkerManager.Log4jMarker("COLLISION"),"second " + enemy.getScore() + "\n\t" + enemyProection + ' ' + enemy.getDirection() +
                         ' ' + enemy.getX() + ' ' + enemy.getY());*/
                        if (!enemy.isKilled() && !gameProfile.isKilled()) {
                            boolean iWin = isIwin(myProection, enemyProection);
                            if (iWin) {
                                enemy.setIsKilled(true);
                                gameProfile.setScore(gameProfile.getScore() + 1);
                                LOGGER.info(new MarkerManager.Log4jMarker("WIN"),"first");
                            } else {
                                gameProfile.setIsKilled(true);
                                enemy.setScore(enemy.getScore() + 1);
                                LOGGER.info(new MarkerManager.Log4jMarker("WIN"),"second");
                            }
                        }
                    }
                }
            });
        }
    }
    private boolean isCollisionAllowed(GameProfile firstProfile,GameProfile secondProfile){
        boolean isAllowed = false;
        long now = System.currentTimeMillis();
        if(firstProfile.getCollisionTimeStamp() == 0 || secondProfile.getCollisionTimeStamp() == 0){
            isAllowed = true;
        }else if(now - firstProfile.getCollisionTimeStamp() > collisionDelta || now - secondProfile.getCollisionTimeStamp() > collisionDelta ){
            isAllowed = true;
        }
        return isAllowed;
    }
    private boolean isIwin(double myProection, double enemyProection){
        boolean enemyKilled;
        if (myProection < 10 && enemyProection < 10) {
            Random random = new Random(System.currentTimeMillis());
            double val = random.nextGaussian();
            enemyKilled = val >= 0;
        }else enemyKilled = myProection < enemyProection;
        return enemyKilled;
    }
    public double getDegree(GameProfile gameProfile,double direction,double vectorX,double vectorY){
        double x = gameProfile.getX();
        double y = gameProfile.getY();
        double vector2X = (x+radius*Math.cos(Math.toRadians(direction)) - x);
        double vector2Y = (y+radius*Math.sin(Math.toRadians(direction)) - y);
        double t1 = Math.sqrt(vectorX*vectorX+vectorY*vectorY);
        double t2 = Math.sqrt(vector2X * vector2X + vector2Y * vector2Y);
        return Math.acos((vectorX * vector2X + vectorY * vector2Y) / (t1 * t2));
    }
}