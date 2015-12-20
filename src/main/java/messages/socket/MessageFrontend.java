package messages.socket;

import game.sockets.JoystickSocket;
import game.sockets.MainWebSocket;
import messages.*;
import messages.mechanics.MoveMessage;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ivan on 13.12.15.
 */
public class MessageFrontend implements Abonent,Runnable {
    private final Address address = new Address();
    private final ConcurrentMap<String,MainWebSocket> sockets = new ConcurrentHashMap<>();
    private final ConcurrentMap<String,JoystickSocket> joystickMap = new ConcurrentHashMap<>();
    private final MessageSystem messageSystem;
    private final int sleepTime;
    public MessageFrontend(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        GameResources gameResources =(GameResources) ResourceFactory.getResource("data/game.json");
        sleepTime = gameResources.getDefaultServiceSleep();
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true){
            messageSystem.execForAbonent(this);
            try{
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void addSocket(MainWebSocket socket){
        sockets.put(socket.getHttpSession(), socket);
    }
    public void sendBrowserMoveMessageForward(JSONObject data, String session){
        if(!isJoystickExist(session)) {
            sendMoveMessage(data,session);
        }
    }
    public void sendMoveMessage(JSONObject data,String session){
        MoveMessage moveMessage = new MoveMessage(address, messageSystem.getAddressService().getMessageSwitchAddress(), data, session);
        messageSystem.sendMessage(moveMessage);
    }

    public boolean isJoystickExist(String httpSession){
        return joystickMap.containsKey(httpSession);
    }
    public void addJoySocket(JoystickSocket socket){
        joystickMap.put(socket.getHttpSession(),socket);
    }
    public void deleteJoySocket(String httpSession){
        joystickMap.remove(httpSession);
    }
    public void sendMessageToSocket(MessageToFrontend message){
            MainWebSocket socket = sockets.get(((MoveMessageBack) message).getSession());
            if(socket != null && socket.isConnected()) {
                socket.sendMessageBack(message.getMessageData());
            }
    }
    public void deleteSocket(String session){
        sockets.remove(session);
    }
}
