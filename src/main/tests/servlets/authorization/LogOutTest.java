package servlets.authorization;

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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


/**
 * Created by Ivan on 10.10.15.
 */
@SuppressWarnings("unused")
public class LogOutTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    @Before
    public void setUp() throws IOException {
        service = mock(AccountService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoPostLogOutSuccess() throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(true);

        LogOut logOut = new LogOut(service);
        logOut.doPost(request, response);

        assertTrue(stringWriter.toString().contains("true"));
    }
    @Test
    public void testDoPostLogOutFail() throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(false);

        LogOut logOut = new LogOut(service);
        logOut.doPost(request,response);

        assertTrue(stringWriter.toString().contains("false"));
    }
}
