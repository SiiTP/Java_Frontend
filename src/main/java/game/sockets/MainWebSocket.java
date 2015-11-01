package game.sockets;

import game.gameaction.GameActionStrategy;
import game.gameaction.MoveActionStrategy;
import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import resource.ResourceFactory;
import resource.ResponseResources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocket extends WebSocketAdapter implements GameSocket {
    private TopLevelGameServer topLevelGameServer;
    private String httpSession;
    private GameActionStrategy gameStrategy;
    private ResponseResources responseResources;
    public MainWebSocket(String httpSession,TopLevelGameServer topLevelGameServer) {
        responseResources =(ResponseResources) ResourceFactory.getResource("resources/data/responseCodes.json");
        this.topLevelGameServer = topLevelGameServer;
        this.httpSession = httpSession;
        gameStrategy = new MoveActionStrategy(topLevelGameServer);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("socket closed " + statusCode + ' ' + reason);
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketText(String message) {
        JSONObject data = null;
        try {
            data= new JSONObject(new JSONTokener(message));
        }catch (JSONException exc){
            exc.printStackTrace();
        }
        boolean isOkPlayer = false;
        if(data != null) {
            isOkPlayer = topLevelGameServer.isCorrectPlayerInGame(httpSession);
        }
        if(isOkPlayer){
            processPlayerMessage(data,gameStrategy);
        }
    }


    @Override
    public void processPlayerMessage(JSONObject message,GameActionStrategy strategy) {
        boolean isOkGame = topLevelGameServer.isGameReady(httpSession);
        JSONObject object = new JSONObject();
        if(isOkGame) {
            Room room = topLevelGameServer.getPlayerRoomBySession(httpSession);
            analyseActionType(message);
            if (room != null) {
                if(room.isFinished()){
                    String winner = room.getWinner();
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
            e.printStackTrace();
        }

    }
    public void analyseActionType(JSONObject message){
        if(!message.has("type") && gameStrategy instanceof MoveActionStrategy){
            gameStrategy.processGameAction(message,httpSession);
        }
    }
}
