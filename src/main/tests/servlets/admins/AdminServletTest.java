package servlets.admins;

import game.serverlevels.top.TopLevelGameServer;
import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    @Before
    public void setup() throws IOException {
        accountService = new AccountService();
        topLevelGameServer = new TopLevelGameServer(accountService);
        server = spy(new Server());
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        PrintWriter writer = new PrintWriter(System.out);
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
}