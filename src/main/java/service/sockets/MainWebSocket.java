package service.sockets;

import game.gameaction.GameActionStrategy;
import game.gameaction.MoveActionStrategy;
import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.eclipse.jetty.websocket.api.Session;
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
public class MainWebSocket extends WebSocketAdapter implements GameSocket {
    TopLevelGameServer topLevelGameServer;
    String httpSession;
    GameActionStrategy strategy;
    private final int roomIsNotReady;
    private final int ok;
    public MainWebSocket(String httpSession,TopLevelGameServer topLevelGameServer) {
        ResponseResources responseResources =(ResponseResources) ResourceFactory.getResource("resources/data/responseCodes.json");
        roomIsNotReady = responseResources.getRoomIsNotReadyCode();
        ok = responseResources.getOk();
        this.topLevelGameServer = topLevelGameServer;
        this.httpSession = httpSession;
        strategy = new MoveActionStrategy(topLevelGameServer);
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
            if (data.has("name")) {
                if (!data.isNull("name")) {
                    String roomname = data.getString("name");
                    isOkPlayer = topLevelGameServer.isCorrectPlayerInGame(httpSession, roomname);
                }
            }
        }
        if(isOkPlayer){
            processPlayerMessage(data);
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace();
    }

    @Override
    public void processPlayerMessage(JSONObject message) {
        boolean isOkGame = topLevelGameServer.isGameReady(httpSession);
        JSONObject object = new JSONObject();
        if(isOkGame) {
            Room room = topLevelGameServer.getPlayerRoomBySession(httpSession);
            strategy.processGameAction(message, httpSession);
            if (room != null) {
                if(room.isFinished()){
                    String winner = room.getWinner();
                    object.put("winner", winner);
                    object.put("status", ok);
                }else {
                    object.put("players", room.getJsonRoomPlayers());
                    object.put("status", ok);
                }
            }
        }else{

            object.put("status", roomIsNotReady);
            object.put("message","Wait for your enemy!");
        }
        try {
            getRemote().sendString(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
