package game.rooms;

import persistance.UserProfile;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ivan on 14.10.15.
 */
public interface Room {
    JSONObject getJsonRoom();
    JSONArray getJsonRoomPlayers();
    int getPlayersCount();
    boolean checkUser(UserProfile user);
    void addUser(UserProfile profile);
    void kickPlayer(UserProfile profile);
    UserProfile getWinner();
    String getRoomName();
    String getPassword();
    boolean isRoomReady();
    boolean isRoomHasPass();
    boolean isFinished();
}
