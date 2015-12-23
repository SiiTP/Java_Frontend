package servlets.game;

import messages.socket.MessageFrontend;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import resource.ResourceFactory;
import resource.ServletResources;

/**
 * Created by ivan on 17.12.15.
 */
public abstract class AbstractGameSocketServlet extends WebSocketServlet {
    private final int idleTime;
    private final MessageFrontend frontend;
    public AbstractGameSocketServlet(MessageFrontend frontend) {
        this.frontend = frontend;
        ServletResources servletResources =(ServletResources) ResourceFactory.getResource("src/main/resources/data/servlet.json");
        idleTime = servletResources.getWebSocketIdleTimeMillisec();
    }

    public int getIdleTime() {
        return idleTime;
    }


    public MessageFrontend getFrontend() {
        return frontend;
    }
}
