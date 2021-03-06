package servlets.game;

import game.server.GameServer;
import org.junit.After;
import persistance.UserProfile;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.ProjectDB;
import service.account.AccountService;
import servlets.game.room.RoomServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class GetRoomListServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private RoomServlet roomServlet;
    private GameServer gameServer;
    private ResponseResources responseResources;
    private AccountService accountService;
    @Before
    public void setUp() throws IOException {
        new ProjectDB().initBD("hibernate-test.cfg.xml");
        responseResources =(ResponseResources) ResourceFactory.getResource("src/main/resources/data/responseCodes.json");
        accountService = spy(new AccountService());
        gameServer = spy(new GameServer(accountService));
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        roomServlet = new RoomServlet(gameServer);
    }
    @Test
    public void testDoPost() throws ServletException, JSONException, IOException {
        UserProfile profile = new UserProfile("test","test");
        doReturn(profile).when(accountService).getUserBySession(anyString());
        gameServer.createRoom("test", "testRoom", null);

        roomServlet.doGet(request, response);
        JSONObject object = new JSONObject(stringWriter.toString());
        int i = object.optInt("status");
        assertEquals(i,responseResources.getOk());
    }
    @Test
    public void testDoPostNoRooms() throws ServletException, JSONException, IOException {
        roomServlet.doGet(request, response);
        JSONObject object = new JSONObject(stringWriter.toString());
        int i = object.optInt("status");
        assertEquals(i, responseResources.getZeroPlayingRoomsNow());
    }
    @After
    public void clear(){
        ProjectDB.truncateTables();
    }

}