package servlets.joinGame;

import game.serverLevels.TopLevelGameServer;
import org.junit.Before;
import org.junit.Test;
import service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ivan on 11.10.15.
 */
public class CreateGameTest {
    private TopLevelGameServer gameServer;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private StringWriter stringWriter;
    private CreateGame gameServlet;
    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        gameServer = mock(TopLevelGameServer.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        gameServlet = new CreateGame(gameServer);
    }

    @Test
    public void testDoPostNoAuthUser() throws Exception {
        when(gameServer.isAuthorizedPlayer(anyString())).thenReturn(false);
        gameServlet.doPost(request, response);
        assertTrue(stringWriter.toString().contains("no auth"));
    }

}