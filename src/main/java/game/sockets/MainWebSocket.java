package game.sockets;

import game.server.GameServer;
import messages.socket.MessageFrontend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocket extends WebSocketAdapter{
    private final MessageFrontend messageFrontend;//todo add constructor
    private final GameServer gameServer;
    private final String httpSession;
    //private ActionProcessor actionProcessor;
    private static final Logger LOGGER = LogManager.getLogger(MainWebSocket.class);
    public MainWebSocket(String httpSession,GameServer gameServer,MessageFrontend frontend) {
        this.gameServer = gameServer;
        this.httpSession = httpSession;
        this.messageFrontend = frontend;
        frontend.addSocket(this);
    }
    public String getHttpSession(){
        return httpSession;
    }
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        if(statusCode == StatusCode.SHUTDOWN || statusCode == StatusCode.NORMAL) {
            LOGGER.info("socket closed with code " + statusCode);
        }else{
            LOGGER.warn("socket closed with code " + statusCode + " for reason " + reason);
        }
        gameServer.kickPlayer(httpSession);
        messageFrontend.deleteSocket(httpSession);
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketText(String message) {
        JSONObject data = null;
        try {
            data= new JSONObject(new JSONTokener(message));
        }catch (JSONException exc){
            LOGGER.error("bad json message " + message, exc);
        }
        boolean isOkPlayer = false;
        if(data != null) {
            isOkPlayer = gameServer.isCorrectPlayerInGame(httpSession);
        }
        if(isOkPlayer){
            messageFrontend.sendMessageForward(data, httpSession);
        }
    }

    /*public void processPlayerMessage(JSONObject message) {
        JSONObject response = null;
        if(!message.has("type")){
            if(!(actionProcessor instanceof MoveActionProcessor)) {
                actionProcessor = new MoveActionProcessor(gameServer);
            }
            response = actionProcessor.processMessage(message,httpSession);
        }
        try {
            if(response != null) {
                getRemote().sendString(response.toString());
            }else{
                LOGGER.error("wrong message from " + httpSession + " message:" + message);
            }
        } catch (IOException e) {
            LOGGER.error("cant send message back, user session " + httpSession);
            e.printStackTrace();
        }

    }*/
    public void sendMessageBack(JSONObject response){
        try {
            if(response != null) {
                getRemote().sendString(response.toString());
            }else{
                LOGGER.error("wrong message from " + httpSession);
            }
        } catch (IOException e) {
            LOGGER.error("cant send message back, user session " + httpSession);
            e.printStackTrace();
        }

    }
}
