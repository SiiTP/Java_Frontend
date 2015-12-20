package game.sockets.creators;

import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by ivan on 17.12.15.
 */
public abstract class AbstractGameSocketCreator implements WebSocketCreator {
    private final MessageFrontend frontend;
    public AbstractGameSocketCreator(MessageFrontend frontend) {
        this.frontend = frontend;
    }

    public MessageFrontend getFrontend() {
        return frontend;
    }
}
