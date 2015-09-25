package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import service.AccountService;
import service.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by ivan on 21.09.15.
 */
public class SignIn extends HttpServlet {
    @NotNull
    private AccountService accountService;

    public SignIn(@NotNull AccountService service) {
        this.accountService = service;
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        System.out.println("In servlet");
        if(session != null){
            String sess = session.getId();
            boolean auth = false;
            auth = accountService.isAuthorized(sess);
            if (auth){
                resp.getWriter().println("you have already auth as"+accountService.getUserBySession(sess).getUsername());
            }else{
                auth = accountService.authtorize(username,password,sess);
                if(auth){
                    resp.getWriter().println("you successfully have been logined in!");
                }else{
                    resp.getWriter().println("wrong login or password");
                }
            }
        }
    }
}
