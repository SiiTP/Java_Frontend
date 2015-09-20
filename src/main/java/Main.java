import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MainServlet;

public class Main {
    public static final int PORT = 8000;

    public static void main(String[] args) throws Exception {
        MainServlet mainServlet = new MainServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(mainServlet), "/index");

        Server server = new Server(PORT);

        server.setHandler(context);
        server.start();
        server.join();
    }
}
