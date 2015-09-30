package servlets.admins;

import org.eclipse.jetty.server.Server;
import org.jetbrains.annotations.NotNull;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ivan on 25.09.15.
 */
public class AdminServlet extends HttpServlet {
    @NotNull
    private Server server;
    @NotNull
    private AccountService accountService;

    public AdminServlet(@NotNull Server serv,@NotNull AccountService acc) {
        this.server = serv;
        this.accountService = acc;
    }

    @Override
    protected void doGet(@NotNull HttpServletRequest req,@NotNull HttpServletResponse resp) throws ServletException, IOException {
        String shutdown = req.getParameter("shutdown");
        String registeredUsers = req.getParameter("reg");
        String loggedUsers = req.getParameter("log");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        if (shutdown!= null && !shutdown.isEmpty()) {
            try {
                int shut = Integer.parseInt(shutdown);
                writer.println("выключение сервера через " + shut + " ms");
                shutDownServer(shut);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (registeredUsers!= null && !registeredUsers.isEmpty() && registeredUsers.equals("true")) {
                writer.println("Зарегестрировано пользователей: " + accountService.getRegisterdUsersCount());
            }
            if (loggedUsers != null && !loggedUsers.isEmpty() && loggedUsers.equals("true")) {
                writer.println("Залогинено пользователей: " + accountService.getLoggedUsersCount());
            }
        }

    }

    private void shutDownServer(int shutdownCount){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            try {
                server.stop();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, shutdownCount, TimeUnit.MILLISECONDS);
    }
}
