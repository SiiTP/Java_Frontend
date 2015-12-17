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
public class MainSocketWebServlet extends AbstractGameSocketServlet {

    public MainSocketWebServlet(GameServer gameServer, MessageFrontend frontend) {
        super(gameServer, frontend);
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(getIdleTime());
        webSocketServletFactory.setCreator(new MainWebSocketCreator(getGameServer(),getFrontend()));
    }
}
