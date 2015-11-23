package game.serverlevels.top;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.user.GameProfile;
import persistance.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import service.account.AccountService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 02.10.15.
 */
public class GameServer {
    private Map<String,Room> rooms;
    private final AccountService accountService;
    private final Logger logger = LogManager.getLogger(GameServer.class);
    public GameServer(AccountService accountService) {
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
                    logger.info("player " + profile.getUsername() + " joined the room " + roomname);
                }
            }
        }
        return room;
    }
    @Nullable
    public UserProfile getPlayerBySession(String session){
        return accountService.getUserBySession(session);
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

    @Nullable
    public JSONArray getRoomsListJSON(){
        JSONArray roomsJsonArray = null;
        if(!rooms.isEmpty()) {
            roomsJsonArray = new JSONArray();
            Collection<Room> roomArray = rooms.values();
            for (Room room : roomArray) {
                roomsJsonArray.put(room.getJsonRoom());
            }
        }
        return roomsJsonArray;
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
                    room = new RoomFFA(roomname);
                } else {
                    room = new RoomFFA(roomname, password);
                }
                room.addUser(profile);

                rooms.put(roomname, room);
                profile.setCurrentroom(room);
                logger.info("player " + profile.getUsername() + " created the room " + roomname);
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
            boolean rightRoom = true;
            if (profile != null) {
                if(profile.getCurrentroom()==null){
                    rightRoom = false;
                }
            }else{
                auth = false;
            }
        }
        return auth ;//todo add && rightroom;
    }

    public AccountService getAccountService() {
        return accountService;
    }
    public void kickPlayer(@NotNull String httpSession){
        UserProfile profile = accountService.getUserBySession(httpSession);
        if(profile != null) {
            Room room = profile.getCurrentroom();
            GameProfile gameProfile = profile.getGameProfile();
            gameProfile.resetSetting();
            if(room != null) {
                room.kickPlayer(profile);
                logger.info("player " + profile.getUsername() + " kicked from the room " + room.getRoomName());
                if(room.getPlayersCount()==0){
                    rooms.remove(room.getRoomName());
                    logger.warn("room " +room.getRoomName() + " deleted from room list");
                }
            }
        }
    }
    public void clearRooms(){
        rooms = new HashMap<>();
        logger.warn("rooms were cleaned");
    }

}
