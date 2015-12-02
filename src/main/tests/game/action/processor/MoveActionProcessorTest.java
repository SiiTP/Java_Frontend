package game.action.processor;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.server.GameServer;
import game.sockets.MainWebSocket;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import persistance.UserProfile;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.account.AccountService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created by ivan on 30.11.15.
 */
@SuppressWarnings("unused")
public class MoveActionProcessorTest {
    GameServer gameServer;
    AccountService accountService;
    MoveActionProcessor actionProcessor;
    String httpSession;
    UserProfile profile;
    ResponseResources responseResources;
    MainWebSocket socket;
    @Before
    public void setup() {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");

        httpSession = "session";
        accountService = spy(new AccountService());
        gameServer = spy(new GameServer(accountService));
        actionProcessor = new MoveActionProcessor(gameServer);

        socket = spy(new MainWebSocket(httpSession,gameServer));
        doReturn(httpSession).when(socket).getHttpSession();

        profile = new UserProfile("name","pass");
        accountService.addUser(profile);
    }

    @Test
    public void testRoomFinished() throws Exception {
        doReturn(true).when(gameServer).isGameReady(anyString());
        Room room = spy(new RoomFFA("test"));
        doReturn(room).when(gameServer).getPlayerRoomBySession(anyString());
        doReturn(true).when(room).isFinished();
        UserProfile p = new UserProfile("test","test");
        doReturn(p).when(room).getWinner();

        JSONObject object = actionProcessor.processMessage(new JSONObject(),socket);

        assertEquals(object.optString("winner"), "test");
    }
    @Test
    public void testRoomNotFinished() throws Exception {
        doReturn(true).when(gameServer).isGameReady(anyString());
        Room room = spy(new RoomFFA("test"));
        doReturn(room).when(gameServer).getPlayerRoomBySession(anyString());
        doReturn(false).when(room).isFinished();

        JSONObject object = actionProcessor.processMessage(new JSONObject(),socket);

        assertTrue(object.has("players"));
    }
    @Test
    public void testRoomNotReady() throws Exception {
        doReturn(false).when(gameServer).isGameReady(anyString());

        JSONObject object = actionProcessor.processMessage(new JSONObject(),socket);

        assertEquals(object.optInt("status"), responseResources.getRoomIsNotReadyCode());
    }

}