package service;

import messages.Address;
import messages.mechanics.MessageMechanics;

/**
 * Created by ivan on 12.12.15.
 */
public class AddressService{
    private Address messageSwitch;

    public Address getMessageSwitchAddress() {
        return messageSwitch;
    }
    public void registerMessageSwitch(MessageMechanics mSwitch){
        this.messageSwitch = mSwitch.getAddress();
    }
}
