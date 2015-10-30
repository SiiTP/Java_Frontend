package test;

import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.extensions.OutgoingFrames;
import org.eclipse.jetty.websocket.common.LogicalConnection;
import org.eclipse.jetty.websocket.common.WebSocketRemoteEndpoint;
import org.json.JSONObject;

import java.io.IOException;

import static org.mockito.Mockito.mock;

/**
 * Created by ivan on 30.10.15.
 */
public class RemoteEndpointStub extends WebSocketRemoteEndpoint {
    private JSONObject message;
    public RemoteEndpointStub(){
        super(mock(LogicalConnection.class),mock(OutgoingFrames.class));

    }

    @Override
    public void sendString(String text) throws IOException {
        message = new JSONObject(text);
    }

    public JSONObject getMessage() {
        return message;
    }
}
