package game.sockets;

import game.gameaction.GameActionStrategy;
import org.json.JSONObject;

/**
 * Created by ivan on 24.10.15.
 */
public interface GameSocket {
    void processPlayerMessage(JSONObject object,GameActionStrategy gameActionStrategy);
}
