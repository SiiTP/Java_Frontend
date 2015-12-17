package servlets.game;

import game.server.GameServer;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.jetbrains.annotations.Nullable;
import resource.ResourceFactory;
import resource.ServletResources;

/**
 * Created by ivan on 17.12.15.
 */
public abstract class AbstractGameSocketServlet extends WebSocketServlet {
    private final int idleTime;
    private final GameServer gameServer;
    private final MessageFrontend frontend;
    public AbstractGameSocketServlet(@Nullable GameServer gameServer, MessageFrontend frontend) {
        this.gameServer = gameServer;
        this.frontend = frontend;
        ServletResources servletResources =(ServletResources) ResourceFactory.getResource("data/servlet.json");
        idleTime = servletResources.getWebSocketIdleTimeMillisec();
    }

    public int getIdleTime() {
        return idleTime;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public MessageFrontend getFrontend() {
        return frontend;
    }
}
