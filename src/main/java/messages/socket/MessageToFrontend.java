package messages.socket;

import messages.Abonent;
import messages.Address;
import messages.Message;
import messages.mechanics.MessageMechanics;
import org.json.JSONObject;

/**
 * Created by ivan on 13.12.15.
 */
public abstract class MessageToFrontend extends Message {
    private final JSONObject messageData;

    public MessageToFrontend(Address from, Address to, JSONObject messageData) {
        super(from, to);
        this.messageData = messageData;
    }

    public JSONObject getMessageData() {
        return messageData;
    }

    @Override
    public void exec(Abonent abonent) {
        if( abonent instanceof MessageFrontend){
            exec((MessageFrontend)abonent);
        }
    }
    public abstract void exec(MessageFrontend mechanics);
}
