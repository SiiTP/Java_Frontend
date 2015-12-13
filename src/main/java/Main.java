import game.server.GameServer;
import messages.MessageSystem;
import messages.socket.MessageFrontend;
import messages.socket.MessageSwitch;
import persistance.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import service.ProjectDB;
import service.account.AccountService;
import servlets.admins.AdminServlet;
import servlets.authorization.*;
import servlets.game.MainSocketWebServlet;
import servlets.game.ScoreServlet;
import servlets.game.room.RoomServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class Main {
        private static final Logger LOGGER = LogManager.getLogger(Main.class);
        @SuppressWarnings("OverlyBroadThrowsClause")
        public static void main(String[] args) throws Exception {
            LOGGER.info("main begin");
            LOGGER.info("connect to production BD");
            ProjectDB.initBD();

            AccountService accountService = new AccountService();
            accountService.addUser(new UserProfile("admin","admin"));
            accountService.addUser(new UserProfile("adminn","adminn"));
            GameServer gameServer = new GameServer(accountService);

            MessageSystem system = new MessageSystem();
            MessageFrontend messageFrontend = new MessageFrontend(system);
            MessageSwitch messageSwitch = new MessageSwitch(system,gameServer);
            system.addService(messageFrontend);
            system.addService(messageSwitch);
            system.getAddressService().registerMessageSwitch(messageSwitch);

            Thread thread = new Thread(messageFrontend);
            thread.setDaemon(true);
            thread.start();
            Thread thread2 = new Thread(messageSwitch);
            thread2.setDaemon(true);
            thread2.start();


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
            context.addServlet(new ServletHolder(new UserServlet(accountService)),"/user");
            context.addServlet(new ServletHolder(new AdminServlet(server, gameServer)), "/admin");
            context.addServlet(new ServletHolder(new RoomServlet(gameServer)), "/rooms");
            context.addServlet(new ServletHolder(new ScoreServlet(gameServer)), "/score");
            context.addServlet(new ServletHolder(new MainSocketWebServlet(gameServer,messageFrontend)), "/gameplay");
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
