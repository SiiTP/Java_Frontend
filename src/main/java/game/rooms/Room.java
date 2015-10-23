package game.rooms;

import exceptions.NoSuchPlayerException;
import exceptions.RoomFullException;
import org.json.JSONObject;
import service.UserProfile;

import java.util.Date;

/**
 * Created by ivan on 14.10.15.
 */
public interface Room {
    JSONObject getJson();
    int getPlayersCount();
    int getPlayersLimit();
    void setPlayersLimit(Integer playersLimit);
    String getPassword();
    void setPassword(String password);
    boolean checkUser(UserProfile user);
    void addUser(UserProfile profile) throws RoomFullException;
    void kickPlayer(UserProfile profile) throws NoSuchPlayerException;
    Date getCreateDate();
    UserProfile getCreator();
    Date getFinishedDate();
    boolean isRoomHasPass();
    boolean isFull();
}
