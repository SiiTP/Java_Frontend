package service.sockets;

import game.server.GameServer;
import game.sockets.MainWebSocket;
import messages.MessageSystem;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistance.UserProfile;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.ProjectDB;
import service.account.AccountService;

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
    ResponseResources responseResources;
    @Before
    public void setup() {
        new ProjectDB().initBD("hibernate-test.cfg.xml");
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");

        httpSession = "session";
        accountService = spy(new AccountService());
        gameServer = spy(new GameServer(accountService));
        MessageSystem system = new MessageSystem();
        MessageFrontend frontend = new MessageFrontend(system);
        webSocket = spy(new MainWebSocket(httpSession, gameServer, frontend));


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
    public void sessionClosed(){
        Session session = mock(Session.class);
        when(session.isOpen()).thenReturn(false);
        doReturn(session).when(webSocket).getSession();
        webSocket.sendMessageBack(new JSONObject());

        doReturn(null).when(webSocket).getSession();
        webSocket.sendMessageBack(new JSONObject());
    }
    @After
    public void clear(){
        ProjectDB.truncateTables();
    }
}