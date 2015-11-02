package game.gameaction;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;
import game.user.GameProfile;
import game.user.UserProfile;

import java.util.List;
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
    static final int TOP_DEGREE = 90;
    static final int LEFT_DEGREE = 180;
    static final int RIGHT_DEGREE = 360;
    static final int BOTTOM_DEGREE = 270;
    enum Quadrant{ FIRST,SECOND,THIRD,FOURTH};
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
        Random random = new Random();
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
                        int x = gameProfile.getX();
                        int y = gameProfile.getY();
                        x += Math.cos(radian) * speed * gameProfile.getDeltaTime();
                        y += Math.sin(radian) * speed * gameProfile.getDeltaTime();

                        gameProfile.setX(x);
                        gameProfile.setY(y);
                        gameProfile.setScore(random.nextInt(10));
                    }
                    gameProfile.setDirection(direction);
                }
            }
        }
    }
    private void checkForCollision(GameProfile gameProfile, Room room){
        List<GameProfile> list = null;
        if(room instanceof RoomFFA){
            RoomFFA roomFFA = (RoomFFA)room;
            list = roomFFA.getGameProfiles();
        }
        for(GameProfile player : list){
            if(!player.equals(gameProfile)){
                int x = player.getX()-gameProfile.getX();
                int y = player.getY()-gameProfile.getY();
                double myDirection = gameProfile.getDirection();
                if(x*x+y*y <= radius*radius){
                    switch (whichQuadrant(myDirection)){
                        case FIRST:
                            actionPlayerFirstQuadrant(gameProfile,player);
                        case SECOND:
                            break;
                        case THIRD:
                            break;
                        case FOURTH:
                            break;
                    }

                }

            }
        }

    }
    private void actionPlayerFirstQuadrant(GameProfile player,GameProfile enemyPlayer){
        double enemyDirection = player.getDirection();
        Quadrant enemy = whichQuadrant(enemyDirection);
        double x = player.getX();
        double y = player.getY();
        double enemyX = enemyPlayer.getX();
        double enemyY = enemyPlayer.getY();
        switch (enemy){
            case FIRST:
            case FOURTH:
            case SECOND:
                if(x < enemyX){
                    enemyPlayer.setIsKilled(true);
                }else{
                    player.setIsKilled(true);
                }
                break;
            case THIRD:
                double direction = player.getDirection();
                double diff = Math.abs(direction-enemyDirection);
                if(diff<10){

                }
                break;
        }
    }
    private Quadrant whichQuadrant(double direction){
        Quadrant quadrant = Quadrant.FIRST;
        if(isFirstQuadrant(direction)){
            quadrant = Quadrant.FIRST;
        }
        if(isSecondQuadrant(direction)){
            quadrant = Quadrant.SECOND;
        }
        if(isThirdQuadrant(direction)){
            quadrant = Quadrant.THIRD;
        }
        if(isFourthQuadrant(direction)){
            quadrant = Quadrant.FOURTH;
        }
        return quadrant;
    }
    private boolean isFirstQuadrant(double direction){
        return direction>=0 && direction< TOP_DEGREE;
    }
    private boolean isSecondQuadrant(double direction){
        return direction >= TOP_DEGREE && direction< LEFT_DEGREE;
    }
    private boolean isThirdQuadrant(double direction){
        return direction >= LEFT_DEGREE && direction < BOTTOM_DEGREE;
    }
    private boolean isFourthQuadrant(double direction){
        return direction >= BOTTOM_DEGREE && direction <= RIGHT_DEGREE;
    }
}