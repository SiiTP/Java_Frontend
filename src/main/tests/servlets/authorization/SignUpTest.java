package servlets.authorization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.AccountService;

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
public class SignUpTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private StringWriter stringWriter;
    private SignUp signUp;
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

        signUp = new SignUp(service);


    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testDoPostIsAvailable() throws Exception {
        when(service.isAvailableName(anyString())).thenReturn(false);

        signUp.doPost(request,response);

        assert stringWriter.toString().contains("false");
    }
    @Test
    public void testDoPostEmptyPass() throws Exception {
        setCheckSetting("");
        assert stringWriter.toString().contains("false");
    }
    private void setCheckSetting(String passValue) throws ServletException, IOException {
        when(service.isAvailableName(anyString())).thenReturn(true);
        when(request.getParameter("username")).thenReturn(anyString());
        when(request.getParameter("password")).thenReturn(passValue);
        signUp.doPost(request,response);
    }
    @Test
    public void testDoPostNullPass() throws Exception {
        setCheckSetting(null);

        assert stringWriter.toString().contains("false");
    }
    @Test
    public void testDoPostSuccess() throws Exception {
        setCheckSetting("aaa");

        assert stringWriter.toString().contains("true");
    }
}
