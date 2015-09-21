package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ivan on 22.09.15.
 */
public class LogOut extends HttpServlet {
    @NotNull
    AccountService accountService;

    public LogOut(@NotNull AccountService service) {
        this.accountService = service;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session != null){
            String sess = session.getId();
            boolean auth = accountService.isAuthorized(sess);
            if(auth){
                accountService.deleteSession(sess);
                resp.getWriter().println("you have been logged out!");
                req.logout();//?????!?!?
            }else{
                resp.getWriter().println("you are not being logged in!");
            }
        }
    }
}
