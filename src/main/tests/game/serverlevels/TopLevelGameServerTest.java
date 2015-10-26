package game.serverlevels;

import exceptions.RoomFullException;
import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import org.junit.Before;
import org.junit.Test;
import service.UserProfile;
import service.account.AccountService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 11.10.15.
 */
public class TopLevelGameServerTest {
    private AccountService service;
    private Map<String,Room> rooms = spy(new HashMap<>());
    private TopLevelGameServer gameServer;
    @Before
    public void setUp() throws Exception {
        service = mock(AccountService.class);
        gameServer = new TopLevelGameServer(service);
        gameServer.setRooms(rooms);
    }

    @Test
    public void testJoinRoomNoPlayer() throws RoomFullException {
        when(service.getUserBySession(anyString())).thenReturn(null);
        Room room = gameServer.joinRoom("aa","aa","aa");
        assertTrue(room == null);
    }
    @Test
    public void testJoinRoomNoRoom() throws RoomFullException {
        when(service.getUserBySession(anyString())).thenReturn(new UserProfile("aa", "bb"));
        when(rooms.containsKey(anyString())).thenReturn(false);
        Room room = gameServer.joinRoom("aa","aa","aa");
        assertTrue(room == null);
    }
    @Test
    public void testJoinRoomHasPass() throws RoomFullException {
        UserProfile profile = spy(new UserProfile("aa","bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(rooms.containsKey(anyString())).thenReturn(true);
        when(profile.getCurrentroom()).thenReturn(null);
        Room room = new RoomFFA("aaa","cc",new UserProfile("ss","ff"));
        when(rooms.get(anyString())).thenReturn(room);

        Room endRoom = gameServer.joinRoom("aaa", "aaa", "aa");
        assertNotNull(endRoom);
        assertTrue(endRoom.getPlayersCount() == 0);


        room = new RoomFFA("aaa",new UserProfile("ss","ff"));
        when(rooms.get(anyString())).thenReturn(room);
        endRoom = gameServer.joinRoom("aa","aa","aa");
        assertNotNull(endRoom);
        assertTrue(endRoom.getPlayersCount() == 1);

    }
    @Test
    public void testJoinRoomUserPassToRoom() throws RoomFullException {
        UserProfile profile = spy(new UserProfile("aa", "bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(rooms.containsKey(anyString())).thenReturn(true);
        when(profile.getCurrentroom()).thenReturn(null);
        Room room = new RoomFFA("aaa","cc",new UserProfile("ss","ff"));
        when(rooms.get(anyString())).thenReturn(room);

        Room endRoom = gameServer.joinRoom("aa", "", "aa");
        assertNotNull(endRoom);
        assertTrue(endRoom.getPlayersCount() == 0);
        endRoom = gameServer.joinRoom("aa", null, "aa");
        assertNotNull(endRoom);
        assertTrue(endRoom.getPlayersCount() == 0);

    }
    @Test
    public void joinRoomUserInRoom() throws RoomFullException {
        Room room = new RoomFFA("aaa",new UserProfile("ss","ff"));

        UserProfile profile = spy(new UserProfile("aa", "bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(rooms.containsKey(anyString())).thenReturn(true);
        when(profile.getCurrentroom()).thenReturn(room);

        when(rooms.get(anyString())).thenReturn(room);
        Room endRoom = gameServer.joinRoom("aa","aa","aa");

        assertTrue(endRoom == null);
    }

    @Test
    public void testCreateRoomByUser() throws RoomFullException {
        when(service.getUserBySession(anyString())).thenReturn(null);
        Room endRoom = gameServer.createRoom("aaa","aaa",null);
        assertTrue(endRoom == null);

        Room room = new RoomFFA("aaa",new UserProfile("ss","ff"));
        UserProfile profile = spy(new UserProfile("aa","bb"));
        when(service.getUserBySession(anyString())).thenReturn(profile);
        when(profile.getCurrentroom()).thenReturn(room);

        endRoom = gameServer.createRoom("aaa","aaa",null);
        assertTrue(endRoom == null);
    }
    @Test
    public void testCreateRoomAlreadyExist() throws RoomFullException {
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

        assertTrue(!gameServer.isCorrectPlayerInGame("aa", "ss"));
    }
    @Test
    public void testIsCorrectPlayerInGameNoRoom() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(true);
        when(rooms.containsKey(anyString())).thenReturn(false);

        assertTrue(!gameServer.isCorrectPlayerInGame("aa", "ss"));
    }
    @Test
    public void testIsCorrectPlayerNoPlayer() throws Exception {
        when(service.isAuthorized(anyString())).thenReturn(true);
        when(rooms.containsKey(anyString())).thenReturn(true);
        Room room = spy(new RoomFFA("ds",new UserProfile("aa","bb")));
        when(room.checkUser(anyObject())).thenReturn(false);
        when(rooms.get(anyString())).thenReturn(room);

        assertTrue(!gameServer.isCorrectPlayerInGame("aa", "ss"));
    }
}