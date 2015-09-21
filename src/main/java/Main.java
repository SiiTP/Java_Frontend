import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.AccountService;
import servlets.MainServlet;
import servlets.authorization.LogOut;
import servlets.authorization.SignIn;
import servlets.authorization.SignUp;

public class Main {
    public static final int PORT = 8000;

    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new SignIn(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUp(accountService)),"/signup");
        context.addServlet(new ServletHolder(new LogOut(accountService)),"/logout");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("public_html");
        HandlerList list = new HandlerList();
        list.setHandlers(new Handler[] {resourceHandler,context});
        Server server = new Server(PORT);

        server.setHandler(list);
        server.start();
        server.join();
    }
}
