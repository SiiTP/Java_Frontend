package game.gameAction;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverLevels.top.TopLevelGameServer;
import game.serverLevels.top.TopLevelGameServerSingleton;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import service.GameProfile;
import service.UserProfile;

import java.util.List;
import java.util.Random;

/**
 * Created by ivan on 25.10.15.
 */
public class MoveActionStrategy implements GameActionStrategy {
    TopLevelGameServer topLevelGameServer;

    public MoveActionStrategy() {
        this.topLevelGameServer = TopLevelGameServerSingleton.getInstance();
    }

    @Override
    public void processGameAction(@NotNull JSONObject message,@NotNull String httpSession) {
        Random random = new Random();
        Room room = topLevelGameServer.getRoomByName(message.getString("name"));
        if(!room.isFinished()) {
            if (room instanceof RoomFFA) {
                RoomFFA roomFFA = (RoomFFA) room;
                List<UserProfile> players = roomFFA.getPlayers();
                final int minBorder = 100;
                final int maxBorder = 400;
                int randomX = minBorder + random.nextInt(maxBorder);
                int randomY = minBorder + random.nextInt(maxBorder);
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
}
