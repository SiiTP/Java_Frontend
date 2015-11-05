package servlets.game;

import game.serverlevels.top.TopLevelGameServer;
import game.sockets.creators.MainWebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import resource.ResourceFactory;
import resource.ServletResources;

import javax.servlet.annotation.WebServlet;

/**
 * Created by ivan on 24.10.15.
 */
@WebServlet
public class MainSocketWebServlet extends WebSocketServlet {
    private final int idleTime;
    private final TopLevelGameServer topLevelGameServer;
    public MainSocketWebServlet(TopLevelGameServer topLevelGameServer) {
        this.topLevelGameServer = topLevelGameServer;
        ServletResources servletResources =(ServletResources) ResourceFactory.getResource("resources/data/servlet.json");
        idleTime = servletResources.getWebSocketIdleTimeMillisec();
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(idleTime);
        webSocketServletFactory.setCreator(new MainWebSocketCreator(topLevelGameServer));
    }
}
