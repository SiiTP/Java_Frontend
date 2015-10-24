package service.sockets;

import org.json.JSONObject;

/**
 * Created by ivan on 24.10.15.
 */
public interface GameSocket {
    void processMessage(JSONObject object);
}
