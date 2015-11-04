package servlets.admins;

import game.serverlevels.top.TopLevelGameServer;
import game.user.UserProfile;
import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
public class AdminServletTest {
    private Server server;
    private AccountService accountService;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private TopLevelGameServer topLevelGameServer;
    private StringWriter stringWriter;
    @Before
    public void setup() throws IOException {
        accountService = spy(new AccountService());
        topLevelGameServer = new TopLevelGameServer(accountService);
        server = spy(new Server());
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);

    }
    @Test
    public void testDoGet() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet(server, topLevelGameServer);
        when(req.getParameter("shutdown")).thenReturn("vdbdfb");
        adminServlet.doGet(req, resp);
    }
    @Test
    public void testDoGetStop() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet(server, topLevelGameServer);
        when(req.getParameter("shutdown")).thenReturn("1000");
        adminServlet.doGet(req, resp);

    }
    @Test
    public void testDoGetLogged() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet(server, topLevelGameServer);
        accountService.addUser(new UserProfile("test", "test"));
        when(req.getParameter("log")).thenReturn("true");

        adminServlet.doGet(req, resp);

        assertTrue(stringWriter.toString().contains("0"));

        accountService.authtorize("test", "test", "testSession");
        stringWriter.getBuffer().setLength(0);
        adminServlet.doGet(req, resp);

        assertTrue(!stringWriter.toString().contains("0"));

        when(req.getParameter("log")).thenReturn("gregreg");
        stringWriter.getBuffer().setLength(0);
        adminServlet.doGet(req, resp);

        assertTrue(stringWriter.toString().isEmpty());

    }
    @Test
    public void testDoGetRegistered() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet(server, topLevelGameServer);
        when(req.getParameter("reg")).thenReturn("true");

        adminServlet.doGet(req, resp);

        assertTrue(stringWriter.toString().contains("0"));

        accountService.addUser(new UserProfile("test", "test"));
        stringWriter.getBuffer().setLength(0);
        adminServlet.doGet(req, resp);

        assertTrue(stringWriter.toString().contains("1"));

        when(req.getParameter("reg")).thenReturn("gregreg");
        stringWriter.getBuffer().setLength(0);
        adminServlet.doGet(req, resp);

        assertTrue(stringWriter.toString().isEmpty());
    }
    @Test
    public void testDoGetClear() throws ServletException, IOException {
        AdminServlet adminServlet = new AdminServlet(server, topLevelGameServer);
        UserProfile profile = new UserProfile("test","test");
        doReturn(profile).when(accountService).getUserBySession(anyString());
        topLevelGameServer.createRoom("test", "testRoom", null);
        when(req.getParameter("clear")).thenReturn("");

        adminServlet.doGet(req, resp);

        assertTrue(topLevelGameServer.getRoomsListJSON() == null);
    }
}