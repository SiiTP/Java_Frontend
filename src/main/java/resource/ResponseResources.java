package resource;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class ResponseResources implements Resource {
    private int roomIsNotReady;
    private int ok;

    public int getRoomIsNotReadyCode() {
        return roomIsNotReady;
    }

    public int getOk() {
        return ok;
    }
}
