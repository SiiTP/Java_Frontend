package messages.mechanics;

import game.action.processor.ActionProcessor;
import game.action.processor.MoveActionProcessor;
import game.server.GameServer;
import messages.Abonent;
import messages.Address;
import messages.MessageSystem;
import messages.socket.MoveMessageBack;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

/**
 * Created by ivan on 12.12.15.
 */
public class MessageMechanics implements Runnable, Abonent{
    private final Address address;
    private final MessageSystem system;
    private final GameServer server;
    private final int serviceSleep;
    public MessageMechanics(MessageSystem system, GameServer server) {
        this.system = system;
        this.server = server;
        address = new Address();
        GameResources gameResources =(GameResources) ResourceFactory.getResource("data/game.json");
        serviceSleep = gameResources.getDefaultServiceSleep();
    }
    public void moveMessage(MoveMessage message){
        ActionProcessor processor = new MoveActionProcessor(server);
        JSONObject response = processor.processMessage(message.getMessageData(), message.getSession());
        MoveMessageBack backMessage = new MoveMessageBack(message.getTo(),message.getFrom(),response,message.getSession());
        system.sendMessage(backMessage);
    }
    @Override
    public Address getAddress() {
        return address;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true){
            system.execForAbonent(this);
            try {
                Thread.sleep(serviceSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
