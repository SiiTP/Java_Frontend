package game.sockets;

import messages.socket.MessageFrontend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by ivan on 17.12.15.
 */
public class JoystickSocket extends WebSocketAdapter {
    private final MessageFrontend frontend;
    private String httpSession;
    private static final Logger LOGGER = LogManager.getLogger(JoystickSocket.class);
    public JoystickSocket(MessageFrontend frontend) {
        this.frontend = frontend;
    }

    public String getHttpSession() {
        return httpSession;
    }

    @Override
    public void onWebSocketText(String message) {
        try {
            JSONObject data = new JSONObject(new JSONTokener(message));
            if(httpSession == null  && data.has("session")){
                if(!data.isNull("session")){
                    httpSession = data.getString("session");
                }
                if(!frontend.isJoystickExist(httpSession)) {
                    frontend.addJoySocket(this);
                }
            }
            if(httpSession != null) {
                frontend.sendMoveMessage(data, httpSession);
            }
        }catch (JSONException exc){
            LOGGER.error("bad json message " + message, exc);
        }

    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        frontend.deleteJoySocket(httpSession);
        super.onWebSocketClose(statusCode, reason);
    }
}
