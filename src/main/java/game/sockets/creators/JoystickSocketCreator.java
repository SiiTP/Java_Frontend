package game.sockets.creators;

import game.server.GameServer;
import game.sockets.JoystickSocket;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;

/**
 * Created by ivan on 17.12.15.
 */
public class JoystickSocketCreator extends AbstractGameSocketCreator {
    public JoystickSocketCreator(MessageFrontend frontend) {
        super(null, frontend);
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {

        return new JoystickSocket(getFrontend());
    }
}
