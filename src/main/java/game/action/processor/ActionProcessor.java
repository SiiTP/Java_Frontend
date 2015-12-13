package game.action.processor;

import org.json.JSONObject;

/**
 * Created by ivan on 29.11.15.
 */
public interface ActionProcessor {
    JSONObject processMessage(JSONObject message,String session);
}
