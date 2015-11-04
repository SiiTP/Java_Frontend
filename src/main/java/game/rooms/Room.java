package game.rooms;

import game.user.UserProfile;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ivan on 14.10.15.
 */
public interface Room {
    JSONObject getJsonRoom();
    JSONArray getJsonRoomPlayers();
    int getPlayersCount();
    int getPlayersLimit();
    boolean checkUser(UserProfile user);
    void addUser(UserProfile profile);
    void kickPlayer(UserProfile profile);
    String getWinner();
    String getRoomName();
    String getPassword();
    boolean isRoomReady();
    boolean isRoomHasPass();
    boolean isFull();
    boolean isFinished();
}
