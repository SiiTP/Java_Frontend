package game.rooms;

import exceptions.RoomFullException;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ivan on 02.10.15.
 */
public class RoomFFA extends RoomAbstractImpl {

    private List<UserProfile> users = new ArrayList<>();//TODO лучше сделать связь от профиля к new профилю игровому
    @Override
    public boolean isFull(){
        return users.size() == getPlayersLimit();
    }
    public RoomFFA(String roomName, UserProfile creatorUser) {
        super(roomName,creatorUser);
    }

    public RoomFFA(String roomName, String roomPassword, UserProfile creator) {
        super(roomName,roomPassword,creator);
    }

    @Override
    public void kickPlayer(UserProfile profile) {
        users.remove(profile);
    }


    @Override
    public boolean checkUser(UserProfile user){
        return users.contains(user);
    }
    @Override
    public void addUser(UserProfile profile) throws RoomFullException {
        if(isFull()){
            throw new RoomFullException(getPlayersLimit());
        }
        users.add(profile);
    }
    @Override
    public JSONObject getJson(){
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for(UserProfile profile : users){
            array.put(profile.getJson());
        }
        object.put("roomname",getRoomName());
        object.put("users",(Object)array);
        return object;
    }
    @Override
    public int getPlayersCount() {return users.size();}

}
