package game.serverLevels.top;

import exceptions.RoomFullException;
import game.rooms.Room;
import game.rooms.RoomFFA;
import org.eclipse.jetty.server.Authentication;
import org.jetbrains.annotations.Nullable;
import service.account.AccountService;
import service.UserProfile;
import service.sockets.MainWebSocket;

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
    public Room joinRoom(String roomname, @Nullable String password, String userSession) throws RoomFullException {
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
        boolean isReady = false;
        if (profile != null) {
            room = profile.getCurrentroom();
        }
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

    public Room deleteUser(Room room, String userSession){
        return null;
    }
    public Map<String,Room> getRoomsList(){
        return rooms;
    }
    @Nullable
    public Room createRoom(String session,String roomname, @Nullable String password) throws RoomFullException {
        UserProfile profile = accountService.getUserBySession(session);
        Room room = null;
        if(profile!= null && profile.getCurrentroom() == null) {
            if (!rooms.containsKey(roomname)) {
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
    public boolean isCorrectPlayerInGame(String session,String roomName){
        boolean auth = accountService.isAuthorized(session);
        if(auth) {
            UserProfile profile = accountService.getUserBySession(session);
            if (rooms.containsKey(roomName)) {
                Room room = rooms.get(roomName);
                if (profile != null) {
                    if(!room.checkUser(profile)){
                        auth = false;
                    }
                }
            }else{
                auth = false;
            }
        }
        return auth;
    }


}
