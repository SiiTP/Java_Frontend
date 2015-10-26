package game.gameaction;

import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;
import service.GameProfile;
import service.UserProfile;

import java.util.Random;

/**
 * Created by ivan on 25.10.15.
 */
public class MoveActionStrategy implements GameActionStrategy {
    private TopLevelGameServer topLevelGameServer;
    private int width;
    private int height;
    public MoveActionStrategy(TopLevelGameServer topLevelGameServer) {
        this.topLevelGameServer = topLevelGameServer;//TopLevelGameServerSingleton.getInstance();
        GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
        width = gameResources.getGameFieldWidth();
        height = gameResources.getGameFieldHeight();
    }

    @Override
    public void processGameAction(@NotNull JSONObject message,@NotNull String httpSession) {
        Random random = new Random();
        Room room = topLevelGameServer.getRoomByName(message.getString("name"));
        if(!room.isFinished()) {
            int randomX = 100 + random.nextInt(width);
            int randomY = 100 + random.nextInt(height);
            final int maxScore = 10;
            int randomScore = random.nextInt(maxScore);
            UserProfile profile = topLevelGameServer.getPlayerBySession(httpSession);
            if (profile != null) {
                GameProfile gameProfile = profile.getGameProfile();
                gameProfile.setX(randomX);
                gameProfile.setY(randomY);
                gameProfile.setScore(randomScore);
            }
        }
    }
}
