package game.rooms;

import exceptions.NoSuchPlayerException;
import exceptions.RoomFullException;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserProfile;

/**
 * Created by ivan on 14.10.15.
 */
public interface Room {
    JSONObject getJsonRoom();
    JSONArray getJsonRoomPlayers();
    int getPlayersCount();
    int getPlayersLimit();
    boolean checkUser(UserProfile user);
    void addUser(UserProfile profile) throws RoomFullException;
    void kickPlayer(UserProfile profile) throws NoSuchPlayerException;
    String getWinner();
    String getPassword();
    boolean isRoomReady();
    boolean isRoomHasPass();
    boolean isFull();
    boolean isFinished();
}
