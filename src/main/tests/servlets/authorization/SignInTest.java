package servlets.authorization;

import game.user.UserProfile;
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
 * Created by Ivan on 10.10.15.
 */
public class SignInTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private SignIn signIn;
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

        signIn = new SignIn(service);


    }

    @Test
    public void testDoPostSignInAlreadyAuth() throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(true);
        when(service.getUserBySession(anyString())).thenReturn(new UserProfile("abc","abc"));
        when(request.getParameter("username")).thenReturn(anyString());
        when(request.getParameter("password")).thenReturn(anyString());
        signIn.doPost(request,response);
        assertTrue(stringWriter.toString().contains("false"));
    }
    @Test
    public void testDoPostSignInLoginSuccess() throws ServletException, IOException {
        checkSignIn(true);
    }
    @Test
    public void testDoPostSignInLoginFail() throws ServletException, IOException {
        checkSignIn(false);
    }
    private void checkSignIn(boolean auth) throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(false);
        when(service.authtorize(anyString(), anyString(), anyString())).thenReturn(auth);
        when(request.getParameter("username")).thenReturn("aaaa");
        when(request.getParameter("password")).thenReturn("aaaa");
        signIn.doPost(request,response);
        assertTrue(stringWriter.toString().contains(Boolean.toString(auth)));
    }
}
