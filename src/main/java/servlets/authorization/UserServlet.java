package servlets.authorization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
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
    private static final Logger LOGGER = LogManager.getLogger("ReqResp");
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
            LOGGER.info(new MarkerManager.Log4jMarker("REQUEST"),"user info data: " + session);
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
                responseJSON.put("message", "You are not logged!");
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
    private JSONObject loginRequest(HttpServletRequest req) {
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
                    LOGGER.info(new MarkerManager.Log4jMarker("REQUEST"),"login data: " + username + ' ' + password + ' ' + session);
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
                            UserProfile profile = accountService.getUserBySession(session);
                            if (profile != null) {
                                responseJSON.put("success", true);
                                responseJSON.put("message", "you successfully have been logined in!");
                                responseJSON.put("id", profile.getId());
                            }
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
    private JSONObject registerRequest(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        LOGGER.info(new MarkerManager.Log4jMarker("REQUEST")," register data: " + username + ' ' + password + ' ' + req.getSession().getId());
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
                        responseJSON.put("id", JSONObject.NULL);
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
            LOGGER.info(new MarkerManager.Log4jMarker("REQUEST"),"logout data: " + session);
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
