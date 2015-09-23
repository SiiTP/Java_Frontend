import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MainServlet;

public class Main {
    public static final int PORT = 8001;

    public static void main(String[] args) throws Exception {
        MainServlet mainServlet = new MainServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(mainServlet), "/index");
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
