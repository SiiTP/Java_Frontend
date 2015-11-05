package game.gameaction;

import game.rooms.Room;
import game.rooms.RoomFFA;
import game.serverlevels.top.TopLevelGameServer;
import game.user.GameProfile;
import game.user.UserProfile;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by ivan on 26.10.15.
 */
public class MoveActionStrategyTest {
    private TopLevelGameServer topLevelGameServer;
    private String httpSession;
    @Before
    public void setUp() throws Exception {
        httpSession = "session";
        AccountService accountService = spy(new AccountService());
        topLevelGameServer = spy(new TopLevelGameServer(accountService));
    }

    @Test
    public void testProcessGameAction() throws JSONException {
        MoveActionStrategy moveActionStrategy = new MoveActionStrategy(topLevelGameServer);
        UserProfile profile = new UserProfile("aaaa","bbbb");
        GameProfile gameProfile = profile.getGameProfile();
        gameProfile.setX(1);
        gameProfile.setY(1);
        gameProfile.setScore(1);
        Room room = mock(Room.class);

        doReturn(profile).when(topLevelGameServer).getPlayerBySession(anyString());
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        when(room.isFinished()).thenReturn(true);

        JSONObject object = new JSONObject();

        object.put("direction",2);
        moveActionStrategy.processGameAction(object,httpSession);

        assertTrue(gameProfile.getX() == 1);

    }
    @Test
    public void roomReadyAndPlayerDontMove() throws JSONException, InterruptedException {
        MoveActionStrategy moveActionStrategy = new MoveActionStrategy(topLevelGameServer);
        UserProfile profile = new UserProfile("aaaa","bbbb");
        GameProfile gameProfile = profile.getGameProfile();
        final int defaultTestX = 500;
        gameProfile.setX(defaultTestX);
        final int defaultTestY = 500;
        gameProfile.setY(defaultTestY);
        gameProfile.setScore(1);
        Room room = mock(Room.class);

        doReturn(profile).when(topLevelGameServer).getPlayerBySession(anyString());
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        when(room.isFinished()).thenReturn(false);

        JSONObject object = new JSONObject();

        object.put("direction", -1);
        moveActionStrategy.processGameAction(object, httpSession);

        assertTrue(gameProfile.getX() == defaultTestX);

        object.put("direction", 100);
        moveActionStrategy.processGameAction(object, httpSession);
        moveActionStrategy.processGameAction(object, httpSession);

        assertTrue(gameProfile.getX() != 1 || gameProfile.getY() != 1);
    }
    @Test
    public void checkForCollision(){
        GameProfile profile = new GameProfile();
        GameProfile enemy = new GameProfile();
        final double x1=100;
        profile.setX(x1);
        final double y1 = 100;
        profile.setY(y1);
        final double x2 = 150;
        enemy.setX(x2);
        final double y2 = 150;
        enemy.setY(y2);
        MoveActionStrategy actionStrategy = new MoveActionStrategy(topLevelGameServer);
        final double myDirection = 90;
        final double enemyDirection = 90;
        final int testSuccessMyDegree = 45;
        final int testSuccessEnemyDegree = 135;


        double myDegree = Math.toDegrees(actionStrategy.getDegree(profile,myDirection,x2-x1,y2-y1));
        double enemyDegree = Math.toDegrees(actionStrategy.getDegree(enemy,enemyDirection,-x2+x1,-y2+y1));



        assertTrue((int)myDegree == testSuccessMyDegree && (int)enemyDegree == testSuccessEnemyDegree);
    }
    @SuppressWarnings("MagicNumber")
    @Test
    public void testCheckForCollision(){
        MoveActionStrategy actionStrategy =new MoveActionStrategy(topLevelGameServer);
        UserProfile myProfile = new UserProfile("test","test");
        RoomFFA ffa = new RoomFFA("test");
        UserProfile profile2 = new UserProfile("test2","test2");
        GameProfile enemy = profile2.getGameProfile();
        enemy.setX(125);
        enemy.setY(125);
        enemy.setDirection(180);
        GameProfile me = myProfile.getGameProfile();
        me.setX(100);
        me.setY(100);
        me.setDirection(35);
        ffa.addUser(profile2);
        ffa.addUser(myProfile);

        actionStrategy.checkForCollision(me, ffa);

        assertTrue(enemy.isKilled() && !me.isKilled());

        enemy.setIsKilled(false);
        enemy.setDirection(225);

        actionStrategy.checkForCollision(me, ffa);
        assertTrue(me.isKilled() || enemy.isKilled());
    }
}