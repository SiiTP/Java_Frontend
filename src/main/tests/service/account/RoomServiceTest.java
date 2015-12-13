package service.account;

import game.rooms.RoomFFA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.ProjectDB;
import persistance.RoomDataSet;
import persistance.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by ivan on 21.11.15.
 */
@SuppressWarnings("unused")
public class RoomServiceTest {
    RoomService roomService;
    AccountService accountService;
    @Before
    public void setUp() {
        new ProjectDB().initBD("hibernate-test.cfg.xml");
        roomService = new RoomService();
        accountService = new AccountService();
    }
    @Test
    public void testFinish(){
        UserProfile profile = new UserProfile("test","test");
        profile.getGameProfile().setScore(2);
        UserProfile profile1 = new UserProfile("test2","test2");
        profile1.getGameProfile().setScore(5);
        RoomFFA ffa = new RoomFFA("testRoomFFa");
        ffa.addUser(profile);
        ffa.addUser(profile1);
        accountService.addUser(profile);
        accountService.addUser(profile1);
        roomService.addRoom(ffa.getRoomName(), ffa);
        roomService.finishRoom("testRoomFFa");

        RoomDataSet dataSet = roomService.getRoomInfoByName(ffa.getRoomName());

        assertEquals(dataSet.getWinner().getUsername(),profile1.getUsername());
    }

    @After
    public void clear(){
        ProjectDB.truncateTables();
    }
}