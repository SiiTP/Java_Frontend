package messages.socket;

import game.sockets.MainWebSocket;
import messages.*;
import messages.transmitter.MoveMessageBack;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 13.12.15.
 */
public class MessageFrontend implements Abonent,Runnable {
    private final Address address = new Address();
    private final Map<String,MainWebSocket> sockets = new HashMap<>();
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
    public void sendMessageForward(JSONObject data,String session){
        MoveMessage moveMessage = new MoveMessage(address,messageSystem.getAddressService().getMessageSwitchAddress(),data,session);
        messageSystem.sendMessage(moveMessage);
    }
    public void sendMessageToSocket(MessageJSON message){
        if(message instanceof MoveMessageBack){
            MainWebSocket socket = sockets.get(((MoveMessageBack) message).getSession());
            if(socket != null) {
                socket.sendMessageBack(message.getMessageData());
            }
        }
    }
    public void deleteSocket(String session){
        sockets.remove(session);
    }
}
