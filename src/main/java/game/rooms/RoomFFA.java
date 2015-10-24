package game.rooms;

import exceptions.NoSuchPlayerException;
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
    public void kickPlayer(UserProfile profile) throws NoSuchPlayerException{
        if(!users.contains(profile)){
            throw new NoSuchPlayerException(getRoomName());
        }
        users.remove(profile);
    }

    @Override
    public boolean isRoomReady() {
        return users.size()>1;
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
    public JSONObject getJsonRoom(){
        JSONObject object = new JSONObject();
        object.put("roomID",1);//TODO еще нет БД для ID
        object.put("roomname",getRoomName());
        object.put("players",getPlayersCount());
        object.put("maxPlayers",getPlayersLimit());
        return object;
    }

    @Override
    public JSONArray getJsonRoomPlayers() {

        JSONArray array = new JSONArray();
        for(UserProfile profile : users){
            array.put(profile.getJson());
        }
        return array;
    }

    @Override
    public int getPlayersCount() {return users.size();}

}
