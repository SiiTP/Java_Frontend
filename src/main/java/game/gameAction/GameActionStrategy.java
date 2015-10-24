package game.gameAction;

import game.rooms.Room;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import service.UserProfile;

/**
 * Created by ivan on 25.10.15.
 */
public interface GameActionStrategy {
    void processGameAction(JSONObject message,@NotNull String httpSession);
}
