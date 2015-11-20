package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import service.account.AccountService;

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
    final
    AccountService accountService;
    public LogOut(@NotNull AccountService service) {
        this.accountService = service;
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req,@NotNull HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PrintWriter writer = resp.getWriter();

        JSONObject responseJSON = new JSONObject();
        if (httpSession != null) {
            String session = httpSession.getId();
            boolean auth = accountService.isAuthorized(session);
            if (auth) {
                accountService.deleteSession(session);
                responseJSON.put("success", true);
                responseJSON.put("message", "you have been logged out!");
            } else {
                responseJSON.put("success", false);
                responseJSON.put("message", "you are not being logged in!");

            }
        }
        if(writer != null) {
            writer.println(responseJSON.toString());
        }
    }
}
