package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
    protected void doPost(@NotNull HttpServletRequest req,@NotNull HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PrintWriter writer = resp.getWriter();
        if(writer != null) {
            if (httpSession != null) {
                String session = httpSession.getId();
                boolean auth = accountService.isAuthorized(session);
                if (auth) {
                    accountService.deleteSession(session);
                    writer.println("you have been logged out!");
                } else {
                    writer.println("you are not being logged in!");
                }
            }
        }
    }
}
