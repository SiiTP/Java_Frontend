package messages.transmitter;

import messages.Abonent;
import messages.Address;
import messages.MessageJSON;
import messages.socket.MessageFrontend;
import org.json.JSONObject;

/**
 * Created by ivan on 12.12.15.
 */
public class MoveMessageBack extends MessageJSON {
    private final String session;

    public MoveMessageBack(Address from, Address to, JSONObject messageData, String session) {
        super(from, to, messageData);
        this.session = session;
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof MessageFrontend){
            ((MessageFrontend) abonent).sendMessageToSocket(this);
        }
    }

    public String getSession() {
        return session;
    }
}
