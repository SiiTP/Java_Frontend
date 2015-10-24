package service.sockets;

import game.gameAction.GameActionStrategy;
import game.gameAction.MoveActionStrategy;
import game.rooms.Room;
import game.serverLevels.top.TopLevelGameServer;
import game.serverLevels.top.TopLevelGameServerSingleton;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocket extends WebSocketAdapter implements GameSocket {
    private static final int roomIsNotReady = 301;
    TopLevelGameServer topLevelGameServer;
    String httpSession;
    GameActionStrategy strategy;
    public MainWebSocket(String httpSession) {
        this.topLevelGameServer = TopLevelGameServerSingleton.getInstance();
        this.httpSession = httpSession;
        strategy = new MoveActionStrategy();
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        //TODO посмотреть возможность передачи имени комнаты при коннекте, чтобы делать проверку правильности пользователя здесь 1 раз
        super.onWebSocketConnect(sess);
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
            processMessage(data);
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace();
    }

    @Override
    public void processMessage(JSONObject message) {
        boolean isOkGame = topLevelGameServer.isGameReady(httpSession);
        JSONObject object = new JSONObject();
        if(isOkGame) {
            strategy.processGameAction(message, httpSession);
            Room room = topLevelGameServer.getPlayerRoomBySession(httpSession);
            if (room != null) {
                object.put("players",room.getJsonRoomPlayers());
                object.put("status",200);
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
