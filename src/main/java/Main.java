import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import service.AccountService;
import servlets.admins.AdminServlet;
import servlets.authorization.LogOut;
import servlets.authorization.SignIn;
import servlets.authorization.SignUp;

public class Main {

    private static final int PORT = 8000;

    public static void main(String[] args) throws NumberFormatException {
        AccountService accountService = new AccountService();
        int port = PORT;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.parseInt(portString);
        }
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignIn(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUp(accountService)),"/signup");
        context.addServlet(new ServletHolder(new LogOut(accountService)), "/logout");
        context.addServlet(new ServletHolder(new AdminServlet(server, accountService)), "/admin");
        ResourceHandler resourceHandler = new ResourceHandler();
        //resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("public_html");
        HandlerList list = new HandlerList();
        list.setHandlers(new Handler[]{resourceHandler, context});


        server.setHandler(list);
        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
