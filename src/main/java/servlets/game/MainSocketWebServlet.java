package servlets.game;

import game.server.GameServer;
import game.sockets.creators.MainWebSocketCreator;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by ivan on 24.10.15.
 */
@WebServlet
public class MainSocketWebServlet extends AbstractGameSocketServlet {
    private GameServer gameServer;
    public MainSocketWebServlet(GameServer gameServer, MessageFrontend frontend) {
        super(frontend);
        this.gameServer = gameServer;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(getIdleTime());
        webSocketServletFactory.setCreator(new MainWebSocketCreator(gameServer,getFrontend()));
    }
}
