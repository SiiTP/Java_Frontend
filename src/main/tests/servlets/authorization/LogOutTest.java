package servlets.authorization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;


/**
 * Created by Ivan on 10.10.15.
 */
public class LogOutTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private StringWriter stringWriter;
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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoPostLogOutSuccess() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(true);

        LogOut logOut = new LogOut(service);
        logOut.doPost(request,response);

        assert stringWriter.toString().contains("true");
    }
    @Test
    public void testDoPostLogOutFail() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(false);

        LogOut logOut = new LogOut(service);
        logOut.doPost(request,response);

        assert stringWriter.toString().contains("false");
    }
}
