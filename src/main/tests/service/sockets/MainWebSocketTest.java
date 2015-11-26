package service.sockets;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.server.GameServer;
import game.sockets.MainWebSocket;
import persistance.UserProfile;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.account.AccountService;
import test.RemoteEndpointStub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class MainWebSocketTest {
    GameServer gameServer;
    AccountService accountService;
    MainWebSocket webSocket;
    String httpSession;
    UserProfile profile;
    RemoteEndpointStub endpoint;
    ResponseResources responseResources;
    @Before
    public void setup() {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");

        httpSession = "session";
        accountService = spy(new AccountService());
        gameServer = spy(new GameServer(accountService));
        webSocket = spy(new MainWebSocket(httpSession, gameServer));

        endpoint = new RemoteEndpointStub();
        doReturn(endpoint).when(webSocket).getRemote();

        profile = new UserProfile("name","pass");
        accountService.addUser(profile);
    }



    @Test
    public void testOnWebSocketText() throws Exception {
        doReturn(false).when(gameServer).isCorrectPlayerInGame(anyString());
        String message = "{name:'test'}";
        webSocket.onWebSocketText(message);
    }
    @Test
    public void testOnWebSocketWongMessage() throws Exception {
        doReturn(false).when(gameServer).isCorrectPlayerInGame(anyString());
        String message = "{nam:''}";
        webSocket.onWebSocketText(message);
    }

    @Test
    public void testRoomFinished() throws Exception {
        doReturn(true).when(gameServer).isGameReady(anyString());
        Room room = spy(new RoomFFA("test"));
        doReturn(room).when(gameServer).getPlayerRoomBySession(anyString());
        doReturn(true).when(room).isFinished();
        UserProfile p = new UserProfile("test","test");
        doReturn(p).when(room).getWinner();
        webSocket.processPlayerMessage(new JSONObject());

        assertEquals(endpoint.getMessage().optString("winner"), "test");
    }
    @Test
    public void testRoomNotFinished() throws Exception {
        doReturn(true).when(gameServer).isGameReady(anyString());
        Room room = spy(new RoomFFA("test"));
        doReturn(room).when(gameServer).getPlayerRoomBySession(anyString());
        doReturn(false).when(room).isFinished();

        webSocket.processPlayerMessage(new JSONObject());

        assertTrue(endpoint.getMessage().has("players"));
    }
    @Test
    public void testRoomNotReady() throws Exception {
        doReturn(false).when(gameServer).isGameReady(anyString());

        webSocket.processPlayerMessage(new JSONObject());

        assertEquals(endpoint.getMessage().optInt("status"), responseResources.getRoomIsNotReadyCode());
    }
}