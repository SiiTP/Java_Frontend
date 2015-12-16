package messages.mechanics;

import messages.Address;
import messages.MessageJSON;
import messages.mechanics.MessageMechanics;
import org.json.JSONObject;

/**
 * Created by ivan on 12.12.15.
 */
public class MoveMessage extends MessageToMechanics {
    private final String session;

    public MoveMessage(Address from, Address to, JSONObject messageData, String session) {
        super(from, to, messageData);
        this.session = session;
    }


    public String getSession() {
        return session;
    }

    @Override
    public void exec(MessageMechanics mechanics) {
            mechanics.moveMessage(this);
    }
}
