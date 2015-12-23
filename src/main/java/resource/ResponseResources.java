package resource;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class ResponseResources implements Resource {
    private int roomIsNotReady;
    private int ok;
    private int roomAlreadyExist;
    private int userAlreadyInRoom;
    private int notAuthorized;
    private int roomIsFull;
    private int noSuchRoom;
    private int wrongUsernameOrPassword;
    private int zeroPlayingRoomsNow;
    private int winnerMessage;
    private int noRoomName;

    public int getNoRoomName() {
        return noRoomName;
    }

    public int getWinnerMessageCode() {
        return winnerMessage;
    }

    public int getZeroPlayingRoomsNow() {
        return zeroPlayingRoomsNow;
    }

    public int getWrongUsernameOrPassword() {
        return wrongUsernameOrPassword;
    }

    public int getNoSuchRoom() {
        return noSuchRoom;
    }

    public int getRoomIsFull() {
        return roomIsFull;
    }

    public int getNotAuthorized() {
        return notAuthorized;
    }

    public int getUserAlreadyInRoom() {
        return userAlreadyInRoom;
    }

    public int getRoomAlreadyExist() {
        return roomAlreadyExist;
    }

    public int getRoomIsNotReadyCode() {
        return roomIsNotReady;
    }

    public int getOk() {
        return ok;
    }
}
