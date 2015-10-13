import game.serverLevels.top.TopLevelGameServer;
import game.serverLevels.top.TopLevelGameServerSingleton;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.account.AccountService;
import service.account.AccountServiceSingleton;
import servlets.admins.AdminServlet;
import servlets.authorization.LogOut;
import servlets.authorization.LoginInfo;
import servlets.authorization.SignIn;
import servlets.authorization.SignUp;
import servlets.joinGame.CreateGame;
import servlets.joinGame.JoinGame;

public class Main {

    private static final int PORT = 8000;

    public static void main(String[] args) throws Exception{
        AccountService accountService = AccountServiceSingleton.getInstance();
        TopLevelGameServer topLevelGameServer = TopLevelGameServerSingleton.getInstance();
        int port = PORT;
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
        context.addServlet(new ServletHolder(new AdminServlet(server, accountService)), "/admin");
        context.addServlet(new ServletHolder(new CreateGame(topLevelGameServer)), "/create");
        context.addServlet(new ServletHolder(new JoinGame(topLevelGameServer)), "/join");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        HandlerList list = new HandlerList();
        list.setHandlers(new Handler[]{resourceHandler, context});


        server.setHandler(list);
        server.start();
        server.join();

    }
}
