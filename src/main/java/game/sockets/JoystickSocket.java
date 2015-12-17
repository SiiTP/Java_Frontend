package game.sockets;

import game.server.GameServer;
import messages.socket.MessageFrontend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by ivan on 17.12.15.
 */
public class JoystickSocket extends WebSocketAdapter {
    private MessageFrontend frontend;
    private static final Logger LOGGER = LogManager.getLogger(JoystickSocket.class);
    public JoystickSocket(MessageFrontend frontend) {
        this.frontend = frontend;
    }

    @Override
    public void onWebSocketText(String message) {
        JSONObject data = null;
        try {
            data= new JSONObject(new JSONTokener(message));
        }catch (JSONException exc){
            LOGGER.error("bad json message " + message, exc);
        }
        String session = null;
        if(data != null && data.has("session")){
            if(!data.isNull("session")){
                session = data.getString("session");
            }
            if(session != null) {
                frontend.sendMessageForward(data, session);
            }
        }
    }
}
