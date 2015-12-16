package messages.mechanics;

import messages.Abonent;
import messages.Address;
import messages.Message;
import org.json.JSONObject;

/**
 * Created by ivan on 13.12.15.
 */
public abstract class MessageToMechanics extends Message {
    private final JSONObject messageData;

    public MessageToMechanics(Address from, Address to, JSONObject messageData) {
        super(from, to);
        this.messageData = messageData;
    }

    public JSONObject getMessageData() {
        return messageData;
    }

    @Override
    public void exec(Abonent abonent) {
        if( abonent instanceof MessageMechanics){
            exec((MessageMechanics)abonent);
        }
    }
    public abstract void exec(MessageMechanics mechanics);
}
