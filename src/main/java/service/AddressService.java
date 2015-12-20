package service;

import messages.Address;
import messages.mechanics.MessageMechanics;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ivan on 12.12.15.
 */
public class AddressService{
    private ArrayList<Address> mechanicsAddress;
    private static AtomicInteger currentMechanics;
    public AddressService() {
        mechanicsAddress = new ArrayList<>();
        currentMechanics = new AtomicInteger(0);
    }

    public Address getMessageSwitchAddress() {
        int current = currentMechanics.incrementAndGet();
        return mechanicsAddress.get(current % mechanicsAddress.size());
    }

    public void registerMessageSwitch(MessageMechanics mSwitch){
        mechanicsAddress.add(mSwitch.getAddress());
    }
}
