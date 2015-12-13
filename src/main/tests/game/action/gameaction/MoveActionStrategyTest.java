package game.action.gameaction;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.server.GameServer;
import game.user.GameProfile;
import persistance.UserProfile;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
@SuppressWarnings("unused")
public class MoveActionStrategyTest {
    private GameServer gameServer;
    private String httpSession;
    private MoveActionStrategy moveActionStrategy;
    @Before
    public void setUp() {
        httpSession = "session";
        AccountService accountService = spy(new AccountService());
        gameServer = spy(new GameServer(accountService));
        moveActionStrategy = new MoveActionStrategy(gameServer);
    }

    @Test
    public void testProcessGameFinished() throws JSONException {

        UserProfile profile = new UserProfile("aaaa","bbbb");
        GameProfile gameProfile = profile.getGameProfile();
        gameProfile.setX(1);
        gameProfile.setY(1);
        gameProfile.setScore(1);
        Room room = mock(Room.class);

        doReturn(profile).when(gameServer).getPlayerBySession(anyString());
        doReturn(room).when(gameServer).getPlayerRoomBySession(anyString());
        when(room.isFinished()).thenReturn(true);

        JSONObject object = new JSONObject();

        object.put("direction", 2);
        moveActionStrategy.processGameAction(object, httpSession);

        assertEquals(gameProfile.getX(), 1.0d, 0);

    }
    @Test
    public void roomReadyAndPlayerDontMove() throws JSONException {
        UserProfile profile = new UserProfile("aaaa","bbbb");
        GameProfile gameProfile = profile.getGameProfile();
        final int defaultTestX = 500;
        gameProfile.setX(defaultTestX);
        final int defaultTestY = 500;
        gameProfile.setY(defaultTestY);
        gameProfile.setScore(1);
        Room room = mock(Room.class);

        doReturn(profile).when(gameServer).getPlayerBySession(anyString());
        doReturn(room).when(gameServer).getPlayerRoomBySession(anyString());
        when(room.isFinished()).thenReturn(false);

        JSONObject object = new JSONObject();

        object.put("direction", -1);
        moveActionStrategy.processGameAction(object, httpSession);

        assertEquals(gameProfile.getX(), defaultTestX, 0);

        object.put("direction", 100);
        moveActionStrategy.processGameAction(object, httpSession);
        moveActionStrategy.processGameAction(object, httpSession);

        assertNotEquals(gameProfile.getX(), 1.0d, 0d);
        assertNotEquals(gameProfile.getY(), 1.0d, 0d);
    }

    @SuppressWarnings("MagicNumber")
    @Test
    public void checkDegreeBetweenPlayers(){
        GameProfile playerOne = new GameProfile();
        GameProfile playerTwo = new GameProfile();
        final double x1=100;
        playerOne.setX(x1);
        final double y1 = 100;
        playerOne.setY(y1);
        final double x2 = 150;
        playerTwo.setX(x2);
        final double y2 = 150;
        playerTwo.setY(y2);
        MoveActionStrategy actionStrategy = new MoveActionStrategy(gameServer);
        final double myDirection = 90;
        final double enemyDirection = 90;

        double playerOneDegree = Math.toDegrees(actionStrategy.getDegree(playerOne,myDirection,x2-x1,y2-y1));
        double playerTwoDegree = Math.toDegrees(actionStrategy.getDegree(playerTwo,enemyDirection,-x2+x1,-y2+y1));

        assertEquals("wrong degree for player 1", 45.0d, Math.floor(playerOneDegree), 0);
        assertEquals("wrong degree for player 2", 135.0d, Math.floor(playerTwoDegree), 0);
    }
    @SuppressWarnings("MagicNumber")
    @Test
    public void checkForCollision(){
        RoomFFA ffa = new RoomFFA("test");
        GameProfile enemy = getTestGameProfileForRoom(ffa,125,125,180);
        GameProfile me = getTestGameProfileForRoom(ffa,100,100,35);

        moveActionStrategy.checkCollision(me, ffa);

        assertTrue("enemy was not killed", enemy.isKilled());
        assertFalse("me should not be killed", me.isKilled());

        enemy.setIsKilled(false);
        enemy.setDirection(225);

        moveActionStrategy.checkCollision(me, ffa);
        boolean directAttack = me.isKilled() || enemy.isKilled();
        assertTrue("someone must be killed", directAttack);
    }
    @SuppressWarnings("MagicNumber")
    @Test
    public void checkForDirectAttack(){
        RoomFFA ffa = new RoomFFA("test");
        GameProfile enemy = getTestGameProfileForRoom(ffa,125,125,225);
        GameProfile me = getTestGameProfileForRoom(ffa,100,100,35);

        moveActionStrategy.checkCollision(me, ffa);
        boolean directAttack = me.isKilled() || enemy.isKilled();
        assertTrue("someone must be killed",directAttack);
    }
    private GameProfile getTestGameProfileForRoom(Room room, double x, double y, double direction){
        UserProfile testProfile = new UserProfile("test","test");
        room.addUser(testProfile);
        GameProfile profile = testProfile.getGameProfile();
        profile.setX(x);
        profile.setY(y);
        profile.setDirection(direction);
        return profile;
    }
}