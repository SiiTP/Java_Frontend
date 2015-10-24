package servlets.game;

import game.serverLevels.top.TopLevelGameServer;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import service.sockets.creators.MainWebSocketCreator;

import javax.servlet.annotation.WebServlet;

/**
 * Created by ivan on 24.10.15.
 */
@WebServlet
public class MainSocketWebServlet extends WebSocketServlet {
    private static final int IDLE_TIME = 60 * 1000;


    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(IDLE_TIME);
        webSocketServletFactory.setCreator(new MainWebSocketCreator());
    }
}
