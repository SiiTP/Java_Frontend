package game.gameAction;

import game.rooms.Room;
import game.serverLevels.top.TopLevelGameServer;
import game.serverLevels.top.TopLevelGameServerSingleton;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import service.UserProfile;

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

        //TODO actions
    }
}
