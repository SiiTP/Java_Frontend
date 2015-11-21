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
import service.account.RoomService;

import java.util.Collection;
import java.util.Map;

/**
 * Created by ivan on 02.10.15.
 */
public class GameServer {
    private final AccountService accountService;
    private final RoomService roomService;
    private final Logger logger = LogManager.getLogger(GameServer.class);
    public GameServer(AccountService accountService) {
        this.accountService = accountService;
        roomService = new RoomService();//todo replace with context service
    }
    @Nullable
    public Room joinRoom(String roomname, @Nullable String password, String userSession) {
        UserProfile profile = accountService.getUserBySession(userSession);
        Room room = null;
        if(profile != null) {
            if (roomService.isAvailable(roomname)) {
                if (profile.getCurrentroom() == null) {
                    room = roomService.getRoomByName(roomname);
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
        roomService.setRooms(rooms);
    }

    @Nullable
    public JSONArray getRoomsListJSON(){
        JSONArray roomsJsonArray = null;
        if(roomService.isAnyRoomExist()) {
            roomsJsonArray = new JSONArray();
            Collection<Room> roomArray = roomService.getRooms();
            for (Room room : roomArray) {
                roomsJsonArray.put(room.getJsonRoom());
            }
        }
        return roomsJsonArray;
    }
    @Nullable
    public Room createRoom(String session,String roomname, @Nullable String password) {
        Room room = null;
        if (roomService.isAvailable(roomname)) {
            UserProfile profile = accountService.getUserBySession(session);
            if(profile!= null && profile.getCurrentroom() == null) {
                if (password == null || password.isEmpty()) {
                    room = new RoomFFA(roomname);
                } else {
                    room = new RoomFFA(roomname, password);
                }
                room.addUser(profile);

                roomService.addRoom(roomname,room);
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

            if(room != null) {
                roomService.kickPlayerFromRoom(room.getRoomName(), profile);
                GameProfile gameProfile = profile.getGameProfile();
                accountService.updatePlayerInfo(profile, gameProfile);
                gameProfile.resetSetting();
                logger.info("player " + profile.getUsername() + " kicked from the room " + room.getRoomName());
                if(room.getPlayersCount()==0){
                    roomService.finishRoom(room.getRoomName());
                    logger.warn("room " +room.getRoomName() + " deleted from room list");
                }
            }


        }
    }
    public void clearRooms(){
        roomService.clear();
        logger.warn("rooms were cleaned");
    }

    public boolean checkIfRoomExist(String roomName) {
        return !roomService.isAvailable(roomName);
    }

}
