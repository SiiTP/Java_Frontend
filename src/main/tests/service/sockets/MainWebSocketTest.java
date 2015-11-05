package service.sockets;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import game.sockets.MainWebSocket;
import game.user.UserProfile;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.account.AccountService;
import test.RemoteEndpointStub;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
public class MainWebSocketTest {
    TopLevelGameServer topLevelGameServer;
    AccountService accountService;
    MainWebSocket webSocket;
    String httpSession;
    UserProfile profile;
    RemoteEndpointStub endpoint;
    ResponseResources responseResources;
    @Before
    public void setup() throws IOException {
        responseResources =(ResponseResources) ResourceFactory.getResource("resources/data/responseCodes.json");

        httpSession = "session";
        accountService = spy(new AccountService());
        topLevelGameServer = spy(new TopLevelGameServer(accountService));
        webSocket = spy(new MainWebSocket(httpSession, topLevelGameServer));

        endpoint = spy(new RemoteEndpointStub());
        doReturn(endpoint).when(webSocket).getRemote();

        profile = new UserProfile("name","pass");
        accountService.addUser(profile);


    }



    @Test
    public void testOnWebSocketText() throws Exception {
        doReturn(false).when(topLevelGameServer).isCorrectPlayerInGame(anyString());
        String message = "{name:'test'}";
        webSocket.onWebSocketText(message);


    }
    @Test
    public void testOnWebSocketWongMessage() throws Exception {
        doReturn(false).when(topLevelGameServer).isCorrectPlayerInGame(anyString());
        String message = "{nam:''}";
        webSocket.onWebSocketText(message);

    }

    @Test
    public void testRoomFinished() throws Exception {
        doReturn(true).when(topLevelGameServer).isGameReady(anyString());
        Room room = spy(new RoomFFA("test"));
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        doReturn(true).when(room).isFinished();
        doReturn("test").when(room).getWinner();
        webSocket.processPlayerMessage(new JSONObject());

        assertTrue(Objects.equals(endpoint.getMessage().optString("winner"), "test"));
    }
    @Test
    public void testRoomNotFinished() throws Exception {
        doReturn(true).when(topLevelGameServer).isGameReady(anyString());
        Room room = spy(new RoomFFA("test"));
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        doReturn(false).when(room).isFinished();

        webSocket.processPlayerMessage(new JSONObject());

        assertTrue(endpoint.getMessage().has("players"));
    }
    @Test
    public void testRoomNotReady() throws Exception {
        doReturn(false).when(topLevelGameServer).isGameReady(anyString());

        webSocket.processPlayerMessage(new JSONObject());

        assertTrue(endpoint.getMessage().optInt("status")==responseResources.getRoomIsNotReadyCode());
    }
}