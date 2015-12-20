package servlets.game;

import game.sockets.creators.JoystickSocketCreator;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by ivan on 17.12.15.
 */
public class JoystickSocketServlet extends AbstractGameSocketServlet {

    public JoystickSocketServlet(MessageFrontend frontend) {
        super(frontend);
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(getIdleTime());
        webSocketServletFactory.setCreator(new JoystickSocketCreator(getFrontend()));
    }
}
