package servlets.authorization;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;
import service.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Ivan on 10.10.15.
 */
public class SignInTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private StringWriter stringWriter;
    private SignIn signIn;
    @Before
    public void setUp() throws Exception {
        service = mock(AccountService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        signIn = new SignIn(service);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoPostSignInAlreadyAuth() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(true);
        when(service.getUserBySession(anyString())).thenReturn(new UserProfile("abc","abc"));
        signIn.doPost(request,response);
        assertTrue(stringWriter.toString().contains("false"));
    }
    @Test
    public void testDoPostSignInLoginSuccess() throws Exception {
        checkSignIn(false,true);
    }
    @Test
    public void testDoPostSignInLoginFail() throws Exception {
        checkSignIn(false,false);
    }
    private void checkSignIn(boolean isAuth, boolean auth) throws ServletException, IOException {
        when(service.isAuthorized(anyString())).thenReturn(isAuth);
        when(service.authtorize(anyString(), anyString(), anyString())).thenReturn(auth);
        signIn.doPost(request,response);
        assertTrue(stringWriter.toString().contains(Boolean.toString(auth)));
    }
}
