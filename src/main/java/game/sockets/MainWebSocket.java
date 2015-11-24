package game.sockets;

import game.gameaction.GameActionStrategy;
import game.gameaction.MoveActionStrategy;
import game.rooms.Room;
import game.serverlevels.top.GameServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import resource.ResourceFactory;
import resource.ResponseResources;

import java.io.IOException;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocket extends WebSocketAdapter {
    private final GameServer gameServer;
    private final String httpSession;
    private final GameActionStrategy gameStrategy;
    private final ResponseResources responseResources;
    private static final Logger LOGGER = LogManager.getLogger(MainWebSocket.class);
    public MainWebSocket(String httpSession,GameServer gameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");
        this.gameServer = gameServer;
        this.httpSession = httpSession;
        gameStrategy = new MoveActionStrategy(gameServer);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        if(statusCode == StatusCode.SHUTDOWN || statusCode == StatusCode.NORMAL) {
            LOGGER.info("socket closed with code " + statusCode);
        }else{
            LOGGER.warn("socket closed with code " + statusCode + " for reason " + reason);
        }
        gameServer.kickPlayer(httpSession);
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
            processPlayerMessage(data);
        }
    }

    public void processPlayerMessage(JSONObject message) {
        boolean isOkGame = gameServer.isGameReady(httpSession);
        JSONObject object = new JSONObject();
        if(isOkGame) {
            analyseActionType(message);
            Room room = gameServer.getPlayerRoomBySession(httpSession);
            if (room != null) {
                if(room.isFinished()){
                    String winner = room.getWinner();
                    LOGGER.info("winner in room " + room.getRoomName() + ':' + winner );
                    object.put("winner", winner);
                    object.put("status", responseResources.getWinnerMessageCode());
                }else {
                    object.put("players", room.getJsonRoomPlayers());
                    object.put("status", responseResources.getOk());
                }
            }
        }else{
            object.put("status", responseResources.getRoomIsNotReadyCode());
            object.put("message", "Wait for your enemy!");
        }
        try {
            getRemote().sendString(object.toString());
        } catch (IOException e) {
            LOGGER.error("cant send message back, user session " + httpSession);
            e.printStackTrace();
        }

    }
    public void analyseActionType(JSONObject message){
        if(!message.has("type") && gameStrategy instanceof MoveActionStrategy){
            gameStrategy.processGameAction(message,httpSession);
        }
    }
}
