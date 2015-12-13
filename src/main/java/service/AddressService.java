package service;

import messages.Address;
import messages.socket.MessageSwitch;

/**
 * Created by ivan on 12.12.15.
 */
public class AddressService{
    private Address messageSwitch;

    public Address getMessageSwitchAddress() {
        return messageSwitch;
    }
    public void registerMessageSwitch(MessageSwitch mSwitch){
        this.messageSwitch = mSwitch.getAddress();
    }
}
