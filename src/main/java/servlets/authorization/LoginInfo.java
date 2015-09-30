package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import service.AccountService;
import service.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by root on 30.09.15.
 */
public class LoginInfo extends HttpServlet {
    @NotNull
    private AccountService accountService;

    public LoginInfo(@NotNull AccountService service) {
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
                UserProfile profile = accountService.getUserBySession(session);
                if (profile != null) {
                    responseJSON.put("success", true);
                    responseJSON.put("username", profile.getUsername());
                    responseJSON.put("message", "You logged!");
                    // TODO передавть счет
                }
            } else {
                responseJSON.put("success", false);
                responseJSON.put("message", "You do not logged!");
            }
        }
        if (writer != null) {
            writer.println(responseJSON.toString());
        }
    }
}
