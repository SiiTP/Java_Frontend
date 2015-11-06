package servlets.authorization;

import org.jetbrains.annotations.Nullable;
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

import static org.mockito.Mockito.*;

/**
 * Created by Ivan on 10.10.15.
 */
public class SignUpTest {
    private AccountService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private SignUp signUp;
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

        signUp = new SignUp(service);
    }


    @Test
    public void testDoPostIsAvailable() throws ServletException, IOException {
        when(service.isAvailableName(anyString())).thenReturn(false);
        when(request.getParameter("username")).thenReturn(anyString());
        when(request.getParameter("password")).thenReturn(anyString());

        signUp.doPost(request,response);

        assert stringWriter.toString().contains("false");
    }
    @Test
    public void testDoPostEmptyPass() throws ServletException, IOException {
        setCheckPassword("");
        assert stringWriter.toString().contains("false");
    }
    private void setCheckPassword(@Nullable String passValue) throws ServletException, IOException {
        when(service.isAvailableName(anyString())).thenReturn(true);
        when(request.getParameter("username")).thenReturn("aaaa");
        when(request.getParameter("password")).thenReturn(passValue);
        signUp.doPost(request,response);
    }
    @Test
    public void testDoPostNullPass() throws ServletException, IOException {
        setCheckPassword(null);

        assert stringWriter.toString().isEmpty();
    }
    @Test
    public void testDoPostSuccess() throws ServletException, IOException {
        setCheckPassword("aaaa");

        assert stringWriter.toString().contains("true");
    }
}
