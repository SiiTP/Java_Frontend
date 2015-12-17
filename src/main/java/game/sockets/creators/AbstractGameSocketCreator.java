package game.sockets.creators;

import game.server.GameServer;
import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.jetbrains.annotations.Nullable;

/**
 * Created by ivan on 17.12.15.
 */
public abstract class AbstractGameSocketCreator implements WebSocketCreator {
    private final GameServer gameServer;
    private final MessageFrontend frontend;
    public AbstractGameSocketCreator(@Nullable GameServer gameServer,MessageFrontend frontend) {
        this.gameServer = gameServer;
        this.frontend = frontend;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public MessageFrontend getFrontend() {
        return frontend;
    }
}
