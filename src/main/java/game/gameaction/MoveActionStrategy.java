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
                        int x = gameProfile.getX();
                        int y = gameProfile.getY();
                        double newX = x + Math.cos(radian) * speed * gameProfile.getDeltaTime();
                        double newY = y + Math.sin(radian) * speed * gameProfile.getDeltaTime();
                        if(newX < width && newY < height){
                            if(newX > 0 && newY > 0) {
                                gameProfile.setX((int)newX);
                                gameProfile.setY((int)newY);
                            }
                        }
                        checkForCollision(gameProfile, room);

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
            for (GameProfile player : list) {
                if (!player.equals(gameProfile)) {
                    int x = player.getX() - gameProfile.getX();
                    int y = player.getY() - gameProfile.getY();
                    //  double myDirection = gameProfile.getDirection();
                    if (x * x + y * y <= radius * radius) {
                        Random random = new Random(System.currentTimeMillis());
                        double val = random.nextGaussian();
                        if (!player.isKilled() && !gameProfile.isKilled()) {
                            if (val >= 0) {
                                player.setIsKilled(true);
                                gameProfile.setScore(gameProfile.getScore() + 1);
                            } else {
                                gameProfile.setIsKilled(true);
                                player.setScore(player.getScore() + 1);
                            }
                        }
                    }
                }
            }
        }
    }


}