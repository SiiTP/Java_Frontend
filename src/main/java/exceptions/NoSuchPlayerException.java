package exceptions;

/**
 * Created by ivan on 14.10.15.
 */
public class NoSuchPlayerException extends Exception {

    public NoSuchPlayerException(String roomName) {
        super("No such player in room: "+roomName);
    }
}
