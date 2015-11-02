import game.serverlevels.top.TopLevelGameServer;
import game.user.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.account.AccountService;
import servlets.admins.AdminServlet;
import servlets.authorization.LogOut;
import servlets.authorization.LoginInfo;
import servlets.authorization.SignIn;
import servlets.authorization.SignUp;
import servlets.game.GetRoomListServlet;
import servlets.game.MainSocketWebServlet;
import servlets.joingame.CreateGame;
import servlets.joingame.JoinGame;

import java.io.FileInputStream;
import java.util.Properties;
public class Main {
        @SuppressWarnings("OverlyBroadThrowsClause")
        public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        accountService.addUser(new UserProfile("admin","admin"));
        TopLevelGameServer topLevelGameServer = new TopLevelGameServer(accountService);
        Properties properties = new Properties();
        try(FileInputStream inputStream = new FileInputStream("resources/cfg/server.properties")){
            properties.load(inputStream);
        }
        int port = Integer.parseInt(properties.getProperty("server.PORT"));
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.parseInt(portString);
            if(port <= 0){
                throw  new IllegalArgumentException("port must be above zero");
            }
        }
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignIn(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUp(accountService)),"/signup");
        context.addServlet(new ServletHolder(new LogOut(accountService)), "/logout");
        context.addServlet(new ServletHolder(new LoginInfo(accountService)), "/logininfo");
        context.addServlet(new ServletHolder(new AdminServlet(server, topLevelGameServer)), "/admin");
        context.addServlet(new ServletHolder(new CreateGame(topLevelGameServer)), "/create");
        context.addServlet(new ServletHolder(new JoinGame(topLevelGameServer)), "/join");
        context.addServlet(new ServletHolder(new MainSocketWebServlet(topLevelGameServer)), "/gameplay");
        context.addServlet(new ServletHolder(new GetRoomListServlet(topLevelGameServer)), "/getRoomList");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        HandlerList list = new HandlerList();
        list.setHandlers(new Handler[]{resourceHandler, context});

        server.setHandler(list);
        server.start();
        server.join();
    }
}
