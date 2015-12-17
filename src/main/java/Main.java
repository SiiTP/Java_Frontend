import game.server.GameServer;
import messages.MessageSystem;
import messages.socket.MessageFrontend;
import messages.mechanics.MessageMechanics;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
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
import servlets.game.JoystickSocketServlet;
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
            ProjectDB db = new ProjectDB();
            if(args.length == 4 || args.length == 3){
                String user;
                String pass;
                String dbName;
                if(args.length == 4){
                    user = args[1];
                    pass = args[2];
                    dbName = args[3];
                }else{
                    user = args[0];
                    pass = args[1];
                    dbName = args[2];
                }
                db.initBD(user,pass,dbName);
            }else {
                db.initBD();
            }
            db.dropAuth();
            AccountService accountService = new AccountService();
            accountService.addUser(new UserProfile("admin","admin"));
            accountService.addUser(new UserProfile("adminn","adminn"));
            GameServer gameServer = new GameServer(accountService);

            MessageSystem system = new MessageSystem();
            MessageFrontend messageFrontend = new MessageFrontend(system);
            MessageMechanics messageMechanics = new MessageMechanics(system,gameServer);
            system.addService(messageFrontend);
            system.addService(messageMechanics);
            system.getAddressService().registerMessageSwitch(messageMechanics);

            Thread thread = new Thread(messageFrontend);
            thread.setDaemon(true);
            thread.start();
            Thread thread2 = new Thread(messageMechanics);
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
            if (args.length == 1 || args.length==4) {
                String portString = args[0];
                port = Integer.parseInt(portString);
                if(port <= 0){
                    LOGGER.fatal("wrong port argument");
                    throw  new IllegalArgumentException("port must be above zero");
                }
            }
            Server server = new Server(port);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

            context.addServlet(new ServletHolder(new UserServlet(accountService)), "/user");
            context.addServlet(new ServletHolder(new AdminServlet(server, gameServer)), "/admin");
            context.addServlet(new ServletHolder(new RoomServlet(gameServer)), "/rooms");
            context.addServlet(new ServletHolder(new ScoreServlet(gameServer)), "/score");
            context.addServlet(new ServletHolder(new MainSocketWebServlet(gameServer, messageFrontend)), "/gameplay");
            context.addServlet(new ServletHolder(new JoystickSocketServlet(messageFrontend)), "/gameplay/mobile");

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setEtags(true);

            GzipHandler gzipHandler = new GzipHandler();
            gzipHandler.setIncludedMimeTypes("text/plain,text/css,application/json,application/javascript,text/xml,application/xml,application/xml+rss,text/javascript");
            gzipHandler.setHandler(resourceHandler);
            resourceHandler.setResourceBase("public_html");
            HandlerList list = new HandlerList();
            list.setHandlers(new Handler[]{gzipHandler,resourceHandler, context});

            server.setHandler(list);
            server.start();
            LOGGER.info("server started");
            server.join();
        }
}
