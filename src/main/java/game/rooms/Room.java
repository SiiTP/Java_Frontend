package game.rooms;

import exceptions.NoSuchPlayerException;
import exceptions.RoomFullException;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserProfile;

import javax.jws.soap.SOAPBinding;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Created by ivan on 14.10.15.
 */
public interface Room {
    JSONObject getJsonRoom();
    JSONArray getJsonRoomPlayers();
    int getPlayersCount();
    int getPlayersLimit();
    void setPlayersLimit(Integer playersLimit);
    String getPassword();
    void setPassword(String password);
    void setStartTime(Instant instant);
    void setFinishTime(Instant instant);
    boolean checkUser(UserProfile user);
    void addUser(UserProfile profile) throws RoomFullException;
    void kickPlayer(UserProfile profile) throws NoSuchPlayerException;
    Instant getCreateDate();
    UserProfile getCreator();
    Instant getFinishTime();
    String getWinner();
    String getRoomName();
    void setRoomName(String roomName);
    boolean isRoomReady();
    boolean isRoomHasPass();
    boolean isFull();
    boolean isFinished();
}
