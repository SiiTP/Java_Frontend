package messages.socket;

import messages.Address;
import messages.MessageJSON;
import messages.mechanics.MessageMechanics;
import messages.socket.MessageFrontend;
import org.json.JSONObject;

/**
 * Created by ivan on 12.12.15.
 */
public class MoveMessageBack extends MessageToFrontend {
    private final String session;

    public MoveMessageBack(Address from, Address to, JSONObject messageData, String session) {
        super(from, to, messageData);
        this.session = session;
    }

    @Override
    public void exec(MessageFrontend mechanics) {
            mechanics.sendMessageToSocket(this);

    }

    public String getSession() {
        return session;
    }
}
