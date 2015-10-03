package game.rooms;

import org.json.JSONArray;
import org.json.JSONObject;
import service.UserProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ivan on 02.10.15.
 */
public class Room {
    private String roomName;
    private String creatorUser;//1 -1 userprofile
    private String roomPassword;
    private Date created;
    private Date finished;
    private Integer playersLimit;
    private List<UserProfile> users = new ArrayList<>();//??? лучше сделать связь от профиля к new профилю игровому
    private String type;


    public Room(String roomName, String creatorUser) {
        this.roomName = roomName;
        this.creatorUser = creatorUser;
        this.roomPassword = roomPassword;
        created = new Date();
    }

    public Room(String roomName, String roomPassword,String creator) {
        this.roomName = roomName;
        this.roomPassword = roomPassword;
        this.creatorUser = creator;
        created = new Date();
    }
    public boolean checkUser(UserProfile user){
        return users.contains(user);
    }
    public void addUser(UserProfile profile){
        users.add(profile);
    }
    public JSONObject getJsonRoom(){
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for(UserProfile profile : users){
            array.put(profile.getJson());
        }
        object.put("roomname",roomName);
        object.put("users",(Object)array);
        return object;
    }

}
