package game.sockets.creators;

import game.serverlevels.top.GameServer;
import game.sockets.MainWebSocket;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocketCreator implements WebSocketCreator {
    private final GameServer gameServer;
    public MainWebSocketCreator(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String httpSession = servletUpgradeRequest.getSession().getId();
        return new MainWebSocket(httpSession, gameServer);
    }
}
