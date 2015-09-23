package servlets.authorization;

import org.jetbrains.annotations.NotNull;
import service.AccountService;
import service.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String username = req.getParameter("login");
        String password = req.getParameter("pass");


        if (username != null && password != null) {
            UserProfile profile = new UserProfile(username,password);
            accountService.addUser(profile);
            resp.getWriter().println("you successfully registered!");
        }else{
            resp.getWriter().println("all fields required!");
        }
    }

}
