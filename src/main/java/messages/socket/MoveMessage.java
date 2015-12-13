package messages.socket;

import messages.Abonent;
import messages.Address;
import messages.MessageJSON;
import org.json.JSONObject;

/**
 * Created by ivan on 12.12.15.
 */
public class MoveMessage extends MessageJSON {
    private final String session;

    public MoveMessage(Address from, Address to, JSONObject messageData, String session) {
        super(from, to, messageData);
        this.session = session;
    }


    public String getSession() {
        return session;
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof MessageSwitch){
            ((MessageSwitch) abonent).moveMessage(this);
        }
    }
}
