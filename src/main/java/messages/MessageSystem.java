package messages;

import service.AddressService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ivan on 12.12.15.
 */
public class MessageSystem {
    private AddressService addressService = new AddressService();
    private Map<Address,ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    public void addService(Abonent abonent){
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void sendMessage(Message message){
        messages.get(message.getTo()).add(message);
    }

    public void execForAbonent(Abonent abonent){
         ConcurrentLinkedQueue<Message> queue = messages.get(abonent.getAddress());
         while(!queue.isEmpty()){
             Message message = queue.poll();
             message.exec(abonent);
         }
    }
}
