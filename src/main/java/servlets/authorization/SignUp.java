package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import service.AccountService;
import service.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ivan on 21.09.15.
 */
public class SignUp extends HttpServlet {
    @NotNull
    private AccountService accountService;

    public SignUp(@NotNull AccountService service) {
        this.accountService = service;
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req,@NotNull HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        PrintWriter writer = resp.getWriter();
        JSONObject responseJSON = new JSONObject();

        boolean isAvailableName = accountService.isAvailableName(username);
        if(isAvailableName) {
            if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
                UserProfile profile = new UserProfile(username, password);
                accountService.addUser(profile);
                responseJSON.put("success", "true");
                responseJSON.put("message", "you successfully registered!");

            } else {
                responseJSON.put("success", "false");
                responseJSON.put("message", "all fields required!");
            }
        }else{
            responseJSON.put("success", "false");
            responseJSON.put("message", "you login have been already used");
        }
        if(writer != null) {
            writer.println(responseJSON.toString());
        }

    }

}
