package servlets.game;

import game.serverlevels.top.GameServer;
import game.user.UserProfile;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import resource.ResourceFactory;
import resource.ResponseResources;
import service.account.AccountService;

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
public class GetRoomListServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private GetRoomListServlet roomServlet;
    private GameServer gameServer;
    private ResponseResources responseResources;
    private AccountService accountService;
    @Before
    public void setUp() throws IOException {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");
        accountService = spy(new AccountService());
        gameServer = spy(new GameServer(accountService));
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        roomServlet = new GetRoomListServlet(gameServer);
    }
    @Test
    public void testDoPost() throws ServletException, JSONException, IOException {
        UserProfile profile = new UserProfile("test","test");
        doReturn(profile).when(accountService).getUserBySession(anyString());
        gameServer.createRoom("test", "testRoom", null);

        roomServlet.doPost(request, response);
        JSONObject object = new JSONObject(stringWriter.toString());
        int i = object.optInt("status");
        assertEquals(i,responseResources.getOk());
    }
    @Test
    public void testDoPostNoRooms() throws ServletException, JSONException, IOException {
        roomServlet.doPost(request, response);
        JSONObject object = new JSONObject(stringWriter.toString());
        int i = object.optInt("status");
        assertEquals(i, responseResources.getZeroPlayingRoomsNow());
    }

}