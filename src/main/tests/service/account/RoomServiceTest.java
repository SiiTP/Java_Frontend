package service.account;

import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by ivan on 21.11.15.
 */
public class RoomServiceTest {
    RoomService roomService;
    AccountService accountService;
    @Before
    public void setUp() throws Exception {
        roomService = new RoomService();
        accountService = new AccountService();
    }

}