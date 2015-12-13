package messages;

import org.json.JSONObject;

/**
 * Created by ivan on 13.12.15.
 */
public abstract class MessageJSON extends Message {
    private JSONObject messageData;

    public MessageJSON(Address from, Address to, JSONObject messageData) {
        super(from, to);
        this.messageData = messageData;
    }

    public JSONObject getMessageData() {
        return messageData;
    }
}
