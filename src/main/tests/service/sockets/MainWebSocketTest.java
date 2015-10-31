package service.sockets;

import game.gameaction.GameActionStrategy;
import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import game.sockets.MainWebSocket;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import game.user.UserProfile;
import resource.GameResources;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.account.AccountService;
import test.RemoteEndpointStub;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Objects;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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
    GameResources gameResources;
    @Before
    public void setup() throws IOException {
        responseResources =(ResponseResources) ResourceFactory.getResource("resources/data/responseCodes.json");
        gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
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
        UserProfile userProfile = new UserProfile("test","test");
        Room room = spy(new RoomFFA("test",userProfile));
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        doReturn(true).when(room).isFinished();
        doReturn("test").when(room).getWinner();
        GameActionStrategy gameActionStrategy = mock(GameActionStrategy.class);
        webSocket.processPlayerMessage(new JSONObject(),gameActionStrategy);

        assertTrue(Objects.equals(endpoint.getMessage().optString("winner"), "test"));
    }
    @Test
    public void testRoomNotFinished() throws Exception {
        doReturn(true).when(topLevelGameServer).isGameReady(anyString());
        UserProfile userProfile = new UserProfile("test","test");
        Room room = spy(new RoomFFA("test", userProfile));
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        doReturn(false).when(room).isFinished();
        GameActionStrategy gameActionStrategy = mock(GameActionStrategy.class);

        webSocket.processPlayerMessage(new JSONObject(), gameActionStrategy);

        assertTrue(endpoint.getMessage().has("players"));
    }
    @Test
    public void testRoomNotReady() throws Exception {
        doReturn(false).when(topLevelGameServer).isGameReady(anyString());

        webSocket.processPlayerMessage(new JSONObject(), mock(GameActionStrategy.class));

        assertTrue(endpoint.getMessage().optInt("status")==responseResources.getRoomIsNotReadyCode());
    }
}