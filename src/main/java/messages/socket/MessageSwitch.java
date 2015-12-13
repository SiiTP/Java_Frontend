package messages.socket;

import game.action.processor.ActionProcessor;
import game.action.processor.MoveActionProcessor;
import game.server.GameServer;
import messages.Abonent;
import messages.Address;
import messages.MessageSystem;
import messages.transmitter.MoveMessageBack;
import org.json.JSONObject;

/**
 * Created by ivan on 12.12.15.
 */
public class MessageSwitch implements Runnable, Abonent{
    private Address address;
    private MessageSystem system;
    private GameServer server;
    public MessageSwitch(MessageSystem system, GameServer server) {
        this.system = system;
        this.server = server;
        address = new Address();
    }
    public void MoveMessage(MoveMessage message){
        ActionProcessor processor = new MoveActionProcessor(server);
        JSONObject response = processor.processMessage(message.getMessageData(), message.getSession());
        MoveMessageBack backMessage = new MoveMessageBack(message.getTo(),message.getFrom(),response,message.getSession());
        system.sendMessage(backMessage);
    }
    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while(true){
            system.execForAbonent(this);
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
