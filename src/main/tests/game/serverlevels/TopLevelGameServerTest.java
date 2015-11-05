package game.serverlevels;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import game.user.UserProfile;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 11.10.15.
 */
public class TopLevelGameServerTest {
    private AccountService service;
    private final Map<String,Room> rooms = spy(new HashMap<>());
    private TopLevelGameServer gameServer;
    @Before
    public void setUp() throws Exception {
        service = mock(AccountService.class);
        gameServer = new TopLevelGameServer(service);
        gameServer.setRooms(rooms);
    }

    @Test
    public void testJoinRoomNoPlayer(){
        when(service.getUserBySession(anyString())).thenReturn(null);
        Room room = gameServer.joinRoom("aa","aa","aa");
        assertTrue(room == null);
    }
    @Test
    public void testJoinRoomNoRoom(){
        when(service.getUserBySession(anyString())).thenReturn(new UserProfile("aa", "bb"));
        when(rooms.containsKey(anyString())).thenReturn(false);
        Room room = gameServer.joinRoom("aa","aa","aa");
        assertTrue(room == null);
    }
    @Test
    public void testJoinRoomHasPass(){
        UserProfile profile = spy(new UserProfile("aa","bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(rooms.containsKey(anyString())).thenReturn(true);
        when(profile.getCurrentroom()).thenReturn(null);
        Room room = new RoomFFA("aaa","cc");
        when(rooms.get(anyString())).thenReturn(room);

        Room endRoom = gameServer.joinRoom("aaa", "aaa", "aa");
        assertNull(endRoom);


        room = new RoomFFA("aaa");
        when(rooms.get(anyString())).thenReturn(room);
        endRoom = gameServer.joinRoom("aa","aa","aa");
        assertNotNull(endRoom);
        assertTrue(endRoom.getPlayersCount() == 1);

    }
    @Test
    public void testJoinRoomUserPassToRoom(){
        UserProfile profile = spy(new UserProfile("aa", "bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(rooms.containsKey(anyString())).thenReturn(true);
        when(profile.getCurrentroom()).thenReturn(null);
        Room room = new RoomFFA("aaa","cc");
        when(rooms.get(anyString())).thenReturn(room);

        Room endRoom = gameServer.joinRoom("aaa", "", "aa");

        assertNull(endRoom);

        endRoom = gameServer.joinRoom("aa", null, "aa");

        assertNull(endRoom);

    }
    @Test
    public void joinRoomUserInRoom(){
        Room room = new RoomFFA("aaa");

        UserProfile profile = spy(new UserProfile("aa", "bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(rooms.containsKey(anyString())).thenReturn(true);
        when(profile.getCurrentroom()).thenReturn(room);

        when(rooms.get(anyString())).thenReturn(room);
        Room endRoom = gameServer.joinRoom("aa","aa","aa");

        assertTrue(endRoom == null);
    }

    @Test
    public void testCreateRoomByUser() {
        when(service.getUserBySession(anyString())).thenReturn(null);
        Room endRoom = gameServer.createRoom("aaa","aaa",null);
        assertTrue(endRoom == null);

        Room room = new RoomFFA("aaa");
        UserProfile profile = spy(new UserProfile("aa","bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(profile.getCurrentroom()).thenReturn(room);

        endRoom = gameServer.createRoom("aaa","aaa",null);
        assertTrue(endRoom == null);
    }
    @Test
    public void testCreateRoomAlreadyExist() {
        UserProfile profile = spy(new UserProfile("aa","bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(profile.getCurrentroom()).thenReturn(null);
        when(rooms.containsKey(anyString())).thenReturn(true);

        Room endRoom = gameServer.createRoom("aaa","aaa",null);

        assertTrue(endRoom == null);
    }

    @Test
    public void testIsCorrectPlayerInGameNoAuth() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(false);

        assertTrue(!gameServer.isCorrectPlayerInGame("aa"));
    }
    @Test
    public void testIsCorrectPlayerInGameAlready() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(true);
        UserProfile profile = new UserProfile("test","test");
        Room room = new RoomFFA("testRoom");
        profile.setCurrentroom(room);
        when(service.getUserBySession(anyString())).thenReturn(profile);

        assertTrue(gameServer.isCorrectPlayerInGame("aa"));
    }
    @Test
    public void isCorrectPlayerInGameNoPlayer() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(true);
        when(service.getUserBySession(anyString())).thenReturn(null);

        assertTrue(!gameServer.isCorrectPlayerInGame("aa"));
    }

    @Test
    public void testGetPlayerRoomBySession() throws Exception {
        UserProfile profile = new UserProfile("test","test");
        Room room = new RoomFFA("testRoom");
        room.addUser(profile);
        profile.setCurrentroom(room);

        when(service.getUserBySession(anyString())).thenReturn(profile);

        Room testingRoom = gameServer.getPlayerRoomBySession(anyString());

        assertNotNull(testingRoom);
        assertTrue(testingRoom.checkUser(profile));

        when(service.getUserBySession(anyString())).thenReturn(null);

        assertNull(gameServer.getPlayerRoomBySession(anyString()));
    }

    @Test
    public void testIsGameReady() throws Exception {
        UserProfile profile = new UserProfile("test","test");

        Room room = new RoomFFA("testRoom");
        room.addUser(profile);
        profile.setCurrentroom(room);

        doReturn(profile).when(service).getUserBySession(anyString());

        assertTrue(!gameServer.isGameReady(anyString()));

        UserProfile profile1 = new UserProfile("test1","test1");
        room.addUser(profile1);

        assertTrue(gameServer.isGameReady(anyString()));
    }

}