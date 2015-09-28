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

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        HttpSession httpSession = req.getSession();
        PrintWriter writer = resp.getWriter();
        JSONObject responseJSON = new JSONObject();
        if (httpSession != null) {
            String session = httpSession.getId();
            boolean auth = accountService.isAuthorized(session);
            if (auth) {
                UserProfile profile = accountService.getUserBySession(session);
                if(profile != null) {
                    responseJSON.put("success", false);
                    responseJSON.put("message", "you have already auth as" + profile.getUsername());
                }
            } else {
                auth = accountService.authtorize(username, password, session);
                if (auth) {
                    responseJSON.put("success", true);
                    responseJSON.put("message","you successfully have been logined in!");
                } else {
                    responseJSON.put("success", false);
                    responseJSON.put("message", "wrong login or password");
                }
            }
        }
        if(writer != null) {
            writer.println(responseJSON.toString());
        }

    }

}
