package game.rooms;

import org.junit.Before;
import org.junit.Test;
import resource.GameResources;
import resource.ResourceFactory;
import game.user.UserProfile;

import java.time.Instant;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by ivan on 26.10.15.
 */
public class RoomFFATest {
    private  RoomFFA roomFFA;
    private int scoreLimit;
    @Before
    public void setup(){
        roomFFA = new RoomFFA("room",new UserProfile("aaaa","bbbb"));
        GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
        scoreLimit = gameResources.getDefaultWinScore();
    }
    @Test
    public void testIsRoomReady()  {
        UserProfile profile = new UserProfile("aaac","bbbc");
        UserProfile profile1 = new UserProfile("aaac","bbbc");
        roomFFA.addUser(profile);

        assertTrue(!roomFFA.isRoomReady());


        roomFFA.addUser(profile1);
        assertTrue(roomFFA.isRoomReady());
    }

    @Test
    public void testIsFinishedScore()  {
        UserProfile profile = new UserProfile("aaac","bbbc");
        UserProfile profile1 = new UserProfile("aaac","bbbc");
        roomFFA.addUser(profile);
        roomFFA.addUser(profile1);
        roomFFA.isRoomReady();
        assertTrue(!roomFFA.isFinished());

        profile.getGameProfile().setScore(scoreLimit);

        assertTrue(roomFFA.isFinished());

        profile.getGameProfile().setScore(2);

        assertTrue(!roomFFA.isFinished());
    }
    @Test
    public void testIsFinishedTime()  {

        UserProfile profile = new UserProfile("aaac","bbbc");
        UserProfile profile1 = new UserProfile("aaac","bbbc");
        roomFFA.addUser(profile);
        roomFFA.addUser(profile1);
        roomFFA.isRoomReady();
        roomFFA.setFinishTime(Instant.now().plusSeconds(1000));

        assertTrue(!roomFFA.isFinished());

        roomFFA.setFinishTime(Instant.now().minusSeconds(1000));
        assertTrue(roomFFA.isFinished());
    }
    @Test
    public void testGetWinner()  {
        UserProfile profile = new UserProfile("aaac","bbbc");
        UserProfile profile1 = new UserProfile("aaad","bbbd");
        roomFFA.addUser(profile);
        roomFFA.addUser(profile1);
        roomFFA.isRoomReady();
        profile.getGameProfile().setScore(scoreLimit);
        profile1.getGameProfile().setScore(2);
        assertTrue(!Objects.equals(roomFFA.getWinner(), profile1.getUsername()));

        assertTrue(Objects.equals(roomFFA.getWinner(), profile.getUsername()));
    }

    @Test
    public void testCheckUser()  {
        UserProfile profile = new UserProfile("aaac","bbbc");
        UserProfile profile1 = new UserProfile("aaad","bbbd");
        roomFFA.addUser(profile);
        assertTrue(roomFFA.checkUser(profile));
        assertTrue(!roomFFA.checkUser(profile1));
    }

    public void testAddUser()  {
        roomFFA.setPlayersLimit(1);
        UserProfile profile = new UserProfile("aaad","bbbd");
        roomFFA.addUser(profile);

        UserProfile profile1 = new UserProfile("aaad","bbbd");
        roomFFA.addUser(profile1);

    }
}