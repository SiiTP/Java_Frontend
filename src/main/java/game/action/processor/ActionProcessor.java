package game.action.processor;

import game.sockets.MainWebSocket;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;

/**
 * Created by ivan on 29.11.15.
 */
public interface ActionProcessor {
    JSONObject processMessage(JSONObject message,String session);
}
