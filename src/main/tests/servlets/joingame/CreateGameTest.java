package servlets.joingame;

import game.rooms.Room;
import game.server.GameServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistance.ProjectDB;
import resource.ResourceFactory;
import resource.ResponseResources;

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
 * Created by ivan on 11.10.15.
 */
public class CreateGameTest {
    private GameServer gameServer;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private CreateGame gameServlet;
    private ResponseResources responseResources;

    @Before
    public void setUp() throws IOException {
        responseResources =(ResponseResources) ResourceFactory.getResource("data/responseCodes.json");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        gameServer = mock(GameServer.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("roomName")).thenReturn("test");
        gameServlet = new CreateGame(gameServer);
    }
    @Test
    public void testDoPostRoomExist() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(false);
        gameServlet.doPost(request, response);
        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getRoomAlreadyExist())));
    }
    @Test
    public void testDoPostuccess() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(true);
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(true);
        Room room = mock(Room.class);
        when(gameServer.createRoom(anyString(), anyString(), anyString())).thenReturn(room);

        gameServlet.doPost(request, response);

        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getOk())));
    }
    @Test
    public void testDoPostAlreadyInRoom() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(true);
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(true);

        when(gameServer.createRoom(anyString(), anyString(), anyString())).thenReturn(null);

        gameServlet.doPost(request, response);

        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getUserAlreadyInRoom())));
    }
    @Test
    public void testDoPostNoAuthUser() throws ServletException, IOException {
        when(gameServer.checkIfRoomExist(anyString())).thenReturn(true);
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(false);

        gameServlet.doPost(request, response);

        assertTrue(stringWriter.toString().contains(Integer.toString(responseResources.getNotAuthorized())));
    }

}