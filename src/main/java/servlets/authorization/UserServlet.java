package servlets.authorization;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import persistance.UserProfile;
import service.account.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ivan on 25.11.15.
 */
public class UserServlet extends HttpServlet{
    private final AccountService accountService;

    public UserServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                }else{
                    responseJSON.put("success", false);
                    responseJSON.put("message", "something wrong!");
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        JSONObject responseJSON;
        if(req.getParameter("type") != null){
            responseJSON = registerRequest(req);
        }else{
            responseJSON = loginRequest(req);
        }
        if (writer != null && responseJSON != null) {
            writer.println(responseJSON.toString());
        }
    }
    @Nullable
    private JSONObject loginRequest(HttpServletRequest req) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        JSONObject responseJSON = new JSONObject();
        if (username != null && password != null) {
            if (accountService.isDataWrong(username, password)) {
                responseJSON.put("success", false);
                responseJSON.put("message", "wrong data");
            } else {
                HttpSession httpSession = req.getSession();
                if (httpSession != null) {
                    String session = httpSession.getId();
                    boolean auth = accountService.isAuthorized(session);
                    if (auth) {
                        UserProfile profile = accountService.getUserBySession(session);
                        if (profile != null) {
                            responseJSON.put("success", false);
                            responseJSON.put("message", "you have already auth as" + profile.getUsername());
                        }
                    } else {
                        auth = accountService.authtorize(username, password, session);
                        if (auth) {
                            responseJSON.put("success", true);
                            responseJSON.put("message", "you successfully have been logined in!");
                        } else {
                            responseJSON.put("success", false);
                            responseJSON.put("message", "wrong login or password");
                        }
                    }
                }
            }
            return responseJSON;
        }
        return null;
    }
    @Nullable
    private JSONObject registerRequest(HttpServletRequest req) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        JSONObject responseJSON = new JSONObject();
        if (username != null && password != null) {
            if (accountService.isDataWrong(username, password)) {
                responseJSON.put("success", false);
                responseJSON.put("message", "wrong data");
            } else {
                boolean isAvailableName = accountService.isAvailableName(username);
                if (isAvailableName) {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        UserProfile profile = new UserProfile(username, password);
                        accountService.addUser(profile);
                        responseJSON.put("success", true);
                        responseJSON.put("message", "you successfully registered!");
                    }
                } else {
                    responseJSON.put("success", false);
                    responseJSON.put("message", "you login have been already used");
                }
            }
            return responseJSON;
        }
        return null;
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PrintWriter writer = resp.getWriter();
        JSONObject responseJSON = new JSONObject();
        if (httpSession != null) {
            String session = httpSession.getId();
            boolean auth = accountService.isAuthorized(session);
            if (auth) {
                accountService.deleteSession(session);
                responseJSON.put("success", true);
                responseJSON.put("message", "you have logged out!");
            } else {
                responseJSON.put("success", false);
                responseJSON.put("message", "you have not logged in!");
            }
        }
        if(writer != null) {
            writer.println(responseJSON.toString());
        }
    }
}
