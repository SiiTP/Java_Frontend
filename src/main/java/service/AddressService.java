package service;

import messages.Address;
import messages.socket.MessageFrontend;
import messages.socket.MessageSwitch;

/**
 * Created by ivan on 12.12.15.
 */
public class AddressService{
    private Address messageFrontend;
    private Address messageSwitch;

    public Address getMessageFrontendAddress() {
        return messageFrontend;
    }
    public Address getMessageSwitchAddress() {
        return messageSwitch;
    }
    public void registerMessageFrontend(MessageFrontend frontend){
        this.messageFrontend = frontend.getAddress();
    }
    public void registerMessageSwitch(MessageSwitch messageSwitch){
        this.messageSwitch = messageSwitch.getAddress();
    }
}
