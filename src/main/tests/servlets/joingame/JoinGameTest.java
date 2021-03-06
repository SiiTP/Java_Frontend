package servlets.joingame;

import game.rooms.Room;
import game.server.GameServer;
import org.junit.Before;
import org.junit.Test;
import resource.ResourceFactory;
import resource.ResponseResources;
import servlets.game.room.RoomServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ivan on 29.10.15.
 */
@SuppressWarnings("unused")
public class JoinGameTest {
    private GameServer gameServer;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private RoomServlet joinGame;
    private ResponseResources responseResources;

    @Before
    public void setUp() throws IOException {
        responseResources =(ResponseResources) ResourceFactory.getResource("src/main/resources/data/responseCodes.json");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        gameServer = mock(GameServer.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        joinGame = new RoomServlet(gameServer);
    }
    @Test
    public void testDoPostNoAuth() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(false);
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(false);

        joinGame.doPut(request, response);

        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getNotAuthorized())));

    }
    @Test
    public void testDoPostWrongUserOrPass() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(false);
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(true);
        when(gameServer.joinRoom(anyString(), anyString(),anyString())).thenReturn(null);

        joinGame.doPut(request, response);

        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getWrongUsernameOrPassword())));
    }
    @Test
    public void testDoPostSuccess() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(false);
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(true);
        when(gameServer.joinRoom(anyString(), anyString(),anyString())).thenReturn(mock(Room.class));

        joinGame.doPut(request, response);

        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getOk())));
    }
}