package servlets.game;

import game.server.GameServer;
import game.sockets.creators.JoystickSocketCreator;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import resource.ResourceFactory;
import resource.ServletResources;

/**
 * Created by ivan on 17.12.15.
 */
public class JoystickSocketServlet extends AbstractGameSocketServlet {

    public JoystickSocketServlet(MessageFrontend frontend) {
        super(null, frontend);
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(getIdleTime());
        webSocketServletFactory.setCreator(new JoystickSocketCreator(getFrontend()));
    }
}
