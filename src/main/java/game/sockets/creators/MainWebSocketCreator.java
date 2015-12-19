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
public class MainWebSocketCreator extends AbstractGameSocketCreator {
    public MainWebSocketCreator(GameServer gameServer, MessageFrontend frontend) {
        super(gameServer, frontend);
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String httpSession;
        if(servletUpgradeRequest.getSession() != null) {
            httpSession =servletUpgradeRequest.getSession().getId();
        }else {
            httpSession = servletUpgradeRequest.getHttpServletRequest().getHeader("dummySession");
        }
        return new MainWebSocket(httpSession, getGameServer(),getFrontend());
    }
}
