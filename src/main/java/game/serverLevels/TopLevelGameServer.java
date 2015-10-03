package game.serverLevels;

import game.rooms.Room;
import org.jetbrains.annotations.Nullable;
import service.AccountService;
import service.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 02.10.15.
 */
public class TopLevelGameServer {
    private Map<String,Room> rooms = new HashMap<>();
    private AccountService accountService;

    public TopLevelGameServer(AccountService accountService) {
        this.accountService = accountService;
    }
    public Room joinUser(Room room, UserProfile profile){
        return null;
    }
    public Room deleteUser(Room room, UserProfile profile){
        return null;
    }
    public Map<String,Room> getRoomsList(){
        return rooms;
    }
    @Nullable
    public Room createRoom(String session,String roomname,String password) {
        UserProfile profile = accountService.getUserBySession(session);
        Room room = null;
        if(!rooms.containsKey(roomname)){
            if(password == null){
                room = new Room(roomname,profile.getUsername());
            }else{
                room = new Room(roomname,password,profile.getUsername());
            }
            room.addUser(profile);

            rooms.put(roomname,room);
        }
        return room;
    }
    public boolean isCorrectPlayer(String session,String roomName){
        boolean auth = accountService.isAuthorized(session);
        if(auth) {
            UserProfile profile = accountService.getUserBySession(session);
            if (rooms.containsKey(roomName)) {
                Room room = rooms.get(roomName);
                if(!room.checkUser(profile)){
                    auth = false;
                }
            }else{
                auth = false;
            }
        }
        return auth;
    }


}
