package servlets.game;

import game.server.GameServer;
import game.sockets.creators.MainWebSocketCreator;
import messages.socket.MessageFrontend;
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
    private final GameServer gameServer;
    private final MessageFrontend frontend;
    public MainSocketWebServlet(GameServer gameServer,MessageFrontend frontend) {
        this.gameServer = gameServer;
        this.frontend = frontend;
        ServletResources servletResources =(ServletResources) ResourceFactory.getResource("data/servlet.json");
        idleTime = servletResources.getWebSocketIdleTimeMillisec();
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(idleTime);
        webSocketServletFactory.setCreator(new MainWebSocketCreator(gameServer,frontend));
    }
}
