package game.gameaction;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import game.user.GameProfile;
import game.user.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by ivan on 25.10.15.
 */
public class MoveActionStrategy implements GameActionStrategy {
    private TopLevelGameServer topLevelGameServer;
    private int width;
    private int height;
    private int speed;
    private int radius;
    public MoveActionStrategy(TopLevelGameServer topLevelGameServer) {
        this.topLevelGameServer = topLevelGameServer;
        GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
        width = gameResources.getGameFieldWidth();
        height = gameResources.getGameFieldHeight();
        speed = gameResources.getDefaultSpeed();
        radius = gameResources.getDefaultPlayerRadius();
    }

    @Override
    public void processGameAction(@NotNull JSONObject message,@NotNull String httpSession) {
        Room room = topLevelGameServer.getPlayerRoomBySession(httpSession);
        if(room != null) {
            if (!room.isFinished()) {
                UserProfile profile = topLevelGameServer.getPlayerBySession(httpSession);
                if (profile != null) {
                    GameProfile gameProfile = profile.getGameProfile();
                    double direction = -1;
                    if(message.has("direction")) {
                        direction = message.getDouble("direction");
                    }
                    if (direction != -1) {
                        double radian = Math.toRadians(direction);
                        double x = gameProfile.getX();
                        double y = gameProfile.getY();
                        double delta = gameProfile.getDeltaTime();
                        double newX = x + Math.cos(radian) * speed * delta;
                        double newY = y + Math.sin(radian) * speed * delta;
                        if(newX < width && newY < height){
                            if(newX > 0 && newY > 0) {
                                if(Objects.equals(profile.getUsername(), "admin")) {
                                    System.out.println("old y " + y +" old x " + x + "X " + newX + " Y " + newY + " delta " +gameProfile.getDeltaTime());
                                }
                                gameProfile.setX((int)newX);
                                gameProfile.setY((int)newY);
                            }
                        }
                     //   checkForCollision(gameProfile, room);

                    }
                    gameProfile.setDirection(direction);
                }
            }
        }
    }
    private void checkForCollision(GameProfile gameProfile, @NotNull Room room){
        List<GameProfile> list = null;
        if (room instanceof RoomFFA) {
            RoomFFA roomFFA = (RoomFFA) room;
            list = roomFFA.getGameProfiles();
        }
        if (list != null) {
            list.stream().filter(enemy -> !enemy.equals(gameProfile)).forEach(enemy -> {
                double xBetweenPlayers = enemy.getX() - gameProfile.getX();
                double yBetweenPlayers = enemy.getY() - gameProfile.getY();
                double myDirection = gameProfile.getDirection();
                if (xBetweenPlayers * xBetweenPlayers + yBetweenPlayers * yBetweenPlayers <= radius * radius) {
                    double myProection = Math.toDegrees(getDegree(gameProfile, myDirection, xBetweenPlayers, yBetweenPlayers));
                    double enemyProection = Math.toDegrees(getDegree(gameProfile, myDirection, -xBetweenPlayers, -yBetweenPlayers));
                    if (!enemy.isKilled() && !gameProfile.isKilled()) {
                        boolean iWin = isIwin(myProection, enemyProection);
                        if (iWin) {
                            enemy.setIsKilled(true);
                            gameProfile.setScore(gameProfile.getScore() + 1);
                        } else {
                            gameProfile.setIsKilled(true);
                            enemy.setScore(enemy.getScore() + 1);
                        }
                    }
                }
            });
        }
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
      //  System.out.println("2y " + vector2Y + " 2x " + vector2X);
        double t1 = Math.sqrt(vectorX*vectorX+vectorY*vectorY);
        double t2 = Math.sqrt(vector2X * vector2X + vector2Y * vector2Y);
        return Math.acos((vectorX * vector2X + vectorY * vector2Y) / (t1 * t2));
    }
    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            double direction = scanner.nextDouble();
            double enemyX = scanner.nextDouble();
            double enemyY = scanner.nextDouble();
            GameProfile enemt = new GameProfile();
            enemt.setX(enemyX);
            enemt.setY(enemyY);
            GameProfile profile = new GameProfile();
            profile.setX(100);
            profile.setY(100);
            double ttt = enemt.getX() - profile.getX();
            double ttt2 = enemt.getY() - profile.getY();

            System.out.println(Math.toDegrees(getDegree(profile, direction, ttt, ttt2)));
            direction = scanner.nextDouble();
            System.out.println(Math.toDegrees(getDegree(enemt,direction,-ttt,-ttt2)));
            double x1=100,x2=150;
            double y1=100,y2=100;
            int radius = 20;

            double vector1X = x2-x1;
            double vector1Y = y2-y1;
            double testangle = Math.atan(vector1Y/vector1X);
            System.out.println(Math.toDegrees(testangle));
            double vector2X = (x1+radius*Math.cos(Math.toRadians(direction)) - x1);
            double vector2Y = (y1+radius*Math.sin(Math.toRadians(direction)) - y1);
            System.out.println("2y " + vector2Y + " 2x " + vector2X);
            double t1 = Math.sqrt(vector1X*vector1X+vector1Y*vector1Y);
            double t2 = Math.sqrt(vector2X*vector2X+vector2Y*vector2Y);
            double angle = Math.acos((vector1X * vector2X + vector1Y * vector2Y) /(t1*t2));
            System.out.println(Math.toDegrees(angle));
        }
    }*/
}