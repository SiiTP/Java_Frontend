package servlets.authorization;

import persistance.UserProfile;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class LoginInfoTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private UserServlet logInfo;
    @Before
    public void setUp() throws IOException {
        service = spy(new AccountService());
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        logInfo = new UserServlet(service);
    }
    @Test
    public void testDoPostSuccess() throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(true);
        UserProfile profile = new UserProfile("aaaa","bbbb");
        when(service.getUserBySession(anyString())).thenReturn(profile);

        logInfo.doGet(request, response);

        assertTrue(stringWriter.toString().contains("aaaa"));
    }
    @Test
    public void testDoPostNoUser() throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(true);
        when(service.getUserBySession(anyString())).thenReturn(null);

        logInfo.doGet(request, response);

        assertTrue(stringWriter.toString().contains("false"));
    }
    @Test
    public void testDoPostNoAuth() throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(false);

        logInfo.doGet(request, response);

        assertTrue(stringWriter.toString().contains("false"));
    }
}