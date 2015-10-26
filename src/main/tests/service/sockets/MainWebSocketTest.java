package service.sockets;

import exceptions.TestException;
import game.serverlevels.top.TopLevelGameServer;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.UserProfile;
import service.account.AccountService;

import java.io.IOException;

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
    RemoteEndpoint endpoint;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws IOException {
        httpSession = "session";
        accountService = spy(new AccountService());
        topLevelGameServer = spy(new TopLevelGameServer(accountService));
        webSocket = spy(new MainWebSocket(httpSession, topLevelGameServer));

        endpoint = mock(RemoteEndpoint.class);
        doReturn(endpoint).when(webSocket).getRemote();

        profile = new UserProfile("name","pass");
        accountService.addUser(profile);


    }



    @Test
    public void testOnWebSocketText() throws Exception {
        doReturn(false).when(topLevelGameServer).isCorrectPlayerInGame(anyString(), anyString());
        String message = "{name:'test'}";
        webSocket.onWebSocketText(message);
        doThrow(new RuntimeException()).when(webSocket).processPlayerMessage(anyObject());

    }
    @Test
    public void testOnWebSocketWongMessage() throws Exception {
        doReturn(false).when(topLevelGameServer).isCorrectPlayerInGame(anyString(), anyString());
        String message = "{nam:''}";
        webSocket.onWebSocketText(message);

    }
    @Test
    public void testOnWebSocketOk() throws IOException {
        doReturn(true).when(topLevelGameServer).isCorrectPlayerInGame(anyString(), anyString());
        String message = "{name:'name'}";
        webSocket.onWebSocketText(message);
        doThrow(TestException.class).when(endpoint).sendString(anyString());
        doCallRealMethod().when(webSocket).processPlayerMessage(anyObject());

    }

}