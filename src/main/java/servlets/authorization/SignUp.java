package servlets.authorization;

import org.jetbrains.annotations.NotNull;
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
        if(writer != null) {
            boolean isAvailableName = accountService.isAvailableName(username);
            if(isAvailableName) {
                if (!username.equals("") && !password.equals("")) {
                    UserProfile profile = new UserProfile(username, password);
                    accountService.addUser(profile);
                    writer.println("you successfully registered!");
                } else {
                    writer.println("all fields required!");
                }
            }else{
                writer.println("you login have been already used");
            }
        }
    }

}
