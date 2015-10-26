package service.sockets.creators;

import game.serverlevels.top.TopLevelGameServer;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import service.sockets.MainWebSocket;

/**
 * Created by ivan on 24.10.15.
 */
public class MainWebSocketCreator implements WebSocketCreator {
    private TopLevelGameServer topLevelGameServer;
    public MainWebSocketCreator(TopLevelGameServer topLevelGameServer) {
        this.topLevelGameServer = topLevelGameServer;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String httpSession = servletUpgradeRequest.getSession().getId();
        return new MainWebSocket(httpSession,topLevelGameServer);
    }
}
