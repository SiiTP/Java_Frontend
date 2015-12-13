package messages.socket;

import game.sockets.MainWebSocket;
import messages.*;
import messages.transmitter.MoveMessageBack;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 13.12.15.
 */
public class MessageFrontend implements Abonent,Runnable {
    private Address address = new Address();
    private Map<String,MainWebSocket> sockets = new HashMap<>();
    private MessageSystem messageSystem;

    public MessageFrontend(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true){
            messageSystem.execForAbonent(this);
            try{
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void addSocket(MainWebSocket socket){
        sockets.put(socket.getHttpSession(),socket);
    }
    public void sendMessageForward(JSONObject data,String session){
        MoveMessage moveMessage = new MoveMessage(address,messageSystem.getAddressService().getMessageSwitchAddress(),data,session);
        messageSystem.sendMessage(moveMessage);
    }
    public void sendMessageToSocket(MessageJSON message){
        if(message instanceof MoveMessageBack){
            MainWebSocket socket = sockets.get(((MoveMessageBack) message).getSession());
            socket.sendMessageBack(message.getMessageData());
        }
    }
    public void deleteSocket(String session){
        sockets.remove(session);
    }
}
