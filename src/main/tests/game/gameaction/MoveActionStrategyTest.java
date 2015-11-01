package game.gameaction;

import game.rooms.Room;
import game.serverlevels.top.TopLevelGameServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import game.user.GameProfile;
import game.user.UserProfile;
import service.account.AccountService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
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
        gameProfile.setX(1);
        gameProfile.setY(1);
        gameProfile.setScore(1);
        Room room = mock(Room.class);

        doReturn(profile).when(topLevelGameServer).getPlayerBySession(anyString());
        doReturn(room).when(topLevelGameServer).getPlayerRoomBySession(anyString());
        when(room.isFinished()).thenReturn(false);

        JSONObject object = new JSONObject();

        object.put("direction", -1);
        moveActionStrategy.processGameAction(object, httpSession);

        assertTrue(gameProfile.getX() == 1);

        object.put("direction", 100);
        moveActionStrategy.processGameAction(object, httpSession);
        Thread.sleep(20);
        moveActionStrategy.processGameAction(object, httpSession);

        assertTrue(gameProfile.getX() != 1 || gameProfile.getY() != 1);
    }

}