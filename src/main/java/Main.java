import game.serverlevels.top.GameServer;
import persistance.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import persistance.ProjectDB;
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
import java.io.IOException;
import java.util.Properties;
public class Main {
        private static final Logger LOGGER = LogManager.getLogger(Main.class);
        @SuppressWarnings("OverlyBroadThrowsClause")
        public static void main(String[] args) throws Exception {
            ProjectDB.initBD();
            LOGGER.info("main begin");
            LOGGER.info("connect to production BD");
            ProjectDB.initBD();
            AccountService accountService = new AccountService();
            accountService.addUser(new UserProfile("admin","admin"));
            accountService.addUser(new UserProfile("adminn","adminn"));
            GameServer gameServer = new GameServer(accountService);
            Properties properties = new Properties();
            try(FileInputStream inputStream = new FileInputStream("src/main/resources/cfg/server.properties")){
                properties.load(inputStream);
                LOGGER.info("start prop loaded");
            }catch (IOException exc){
                    LOGGER.fatal("wrong prop file",exc);
            }
            int port = Integer.parseInt(properties.getProperty("server.PORT"));
            if (args.length == 1) {
                String portString = args[0];
                port = Integer.parseInt(portString);
                if(port <= 0){
                    LOGGER.fatal("wrong port argument");
                    throw  new IllegalArgumentException("port must be above zero");
                }
            }
            Server server = new Server(port);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.addServlet(new ServletHolder(new SignIn(accountService)), "/signin");
            context.addServlet(new ServletHolder(new SignUp(accountService)),"/signup");
            context.addServlet(new ServletHolder(new LogOut(accountService)), "/logout");
            context.addServlet(new ServletHolder(new LoginInfo(accountService)), "/user/read");
            context.addServlet(new ServletHolder(new AdminServlet(server, gameServer)), "/admin");
            context.addServlet(new ServletHolder(new CreateGame(gameServer)), "/create");
            context.addServlet(new ServletHolder(new JoinGame(gameServer)), "/join");
            context.addServlet(new ServletHolder(new MainSocketWebServlet(gameServer)), "/gameplay");
            context.addServlet(new ServletHolder(new GetRoomListServlet(gameServer)), "/getRoomList");
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("public_html");
            HandlerList list = new HandlerList();
            list.setHandlers(new Handler[]{resourceHandler, context});

            server.setHandler(list);
            server.start();
            LOGGER.info("server started");
            server.join();
        }
}
