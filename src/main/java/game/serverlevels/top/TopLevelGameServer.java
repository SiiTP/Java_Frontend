package game.serverlevels.top;

import game.rooms.Room;
import game.rooms.RoomFFA;
import org.jetbrains.annotations.Nullable;
import game.user.UserProfile;
import service.account.AccountService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 02.10.15.
 */
public class TopLevelGameServer {
    private Map<String,Room> rooms;
    private AccountService accountService;

    public TopLevelGameServer(AccountService accountService) {
        rooms = new HashMap<>();
        this.accountService = accountService;
    }
    @Nullable
    public Room joinRoom(String roomname, @Nullable String password, String userSession) {
        UserProfile profile = accountService.getUserBySession(userSession);
        Room room = null;
        if(profile != null) {
            if (rooms.containsKey(roomname)) {
                if (profile.getCurrentroom() == null) {
                    room = rooms.get(roomname);
                    if (room.isRoomHasPass()) {
                        if (password != null && password.equals(room.getPassword())) {
                            room.addUser(profile);
                            profile.setCurrentroom(room);
                        }else{
                            room = null;
                        }
                    } else {
                        room.addUser(profile);
                        profile.setCurrentroom(room);
                    }
                }
            }
        }
        return room;
    }
    @Nullable
    public UserProfile getPlayerBySession(String session){
        return accountService.getUserBySession(session);
    }
    public Room getRoomByName(String roomname){
        return rooms.get(roomname);
    }
    @Nullable
    public Room getPlayerRoomBySession(String session){
        UserProfile userProfile = getPlayerBySession(session);
        Room room = null;
        if(userProfile != null){
            room = userProfile.getCurrentroom();
        }
        return room;
    }
    public boolean isGameReady(String session){
        UserProfile profile = accountService.getUserBySession(session);
        Room room = null;
        if (profile != null) {
            room = profile.getCurrentroom();
        }
        boolean isReady = false;
        if(room != null){
            if(room.isRoomReady()){
                isReady = true;
            }
        }
        return isReady;
    }
    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public Map<String,Room> getRoomsList(){
        return rooms;
    }
    public boolean checkIfRoomExist(String roomname){
        return rooms.containsKey(roomname);
    }
    @Nullable
    public Room createRoom(String session,String roomname, @Nullable String password) {
        Room room = null;
        if (!checkIfRoomExist(roomname)) {
            UserProfile profile = accountService.getUserBySession(session);
            if(profile!= null && profile.getCurrentroom() == null) {
                if (password == null || password.isEmpty()) {
                    room = new RoomFFA(roomname, profile);
                } else {
                    room = new RoomFFA(roomname, password, profile);
                }
                room.addUser(profile);

                rooms.put(roomname, room);
                profile.setCurrentroom(room);
            }
        }

        return room;
    }
    public boolean isAuthorizedPlayer(String session){
        return accountService.isAuthorized(session);
    }
    public boolean isCorrectPlayerInGame(String session){
        boolean auth = accountService.isAuthorized(session);
        if(auth) {
            UserProfile profile = accountService.getUserBySession(session);
            if (profile != null) {
                if(profile.getCurrentroom()==null){
                    auth = false;
                }
            }else{
                auth = false;
            }
        }
        return auth;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void clearRooms(){
        rooms = new HashMap<>();
    }

}
