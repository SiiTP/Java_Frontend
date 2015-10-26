package exceptions;

/**
 * Created by ivan on 03.10.15.
 */
public class RoomFullException extends Exception {
    public RoomFullException(Integer limit) {
        super("room is full:" + limit.toString());
    }

}
