package game.sockets.creators;

import game.server.GameServer;
import game.sockets.MainWebSocket;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocketCreator implements WebSocketCreator {
    private final GameServer gameServer;
    private final MessageFrontend frontend;
    public MainWebSocketCreator(GameServer gameServer,MessageFrontend frontend) {
        this.gameServer = gameServer;
        this.frontend = frontend;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String httpSession = servletUpgradeRequest.getSession().getId();
        return new MainWebSocket(httpSession, gameServer,frontend);
    }
}
