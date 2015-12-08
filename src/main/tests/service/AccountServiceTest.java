package service;

import persistance.UserProfile;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import static org.junit.Assert.*;

/**
 * Created by Ivan on 11.10.15.
 */
@SuppressWarnings("unused")
public class AccountServiceTest {
    private AccountService accountService;
    @Before
    public void setUp() {
        ProjectDB.initBD("hibernate-test.cfg.xml");
        ProjectDB.truncateTables();
        accountService = new AccountService();
        accountService.addUser(new UserProfile("first","aaa"));
        accountService.addUser(new UserProfile("second","bbb"));
        accountService.authtorize("first","aaa","session");
    }
    @Test
    public void testIsAuthorized() throws Exception {
        assertTrue(accountService.isAuthorized("session"));
        assertFalse(accountService.isAuthorized("dsadsa"));
    }

    @Test
    public void testIsAvailableNameFail() throws Exception {
        assertFalse(accountService.isAvailableName("first"));
    }
    @Test
    public void testIsAvailableNameSuccess() throws Exception {
        assertTrue(accountService.isAvailableName("fdsfsfd"));
    }
    @Test
    public void testAuthtorizeSuccess() throws Exception {
        assertTrue(accountService.authtorize("second", "bbb", "session2"));
    }
    @Test
    public void testAuthtorizeNoUser() throws Exception {
        assertFalse(accountService.authtorize("gergerh", "bbb", "session2"));
    }

    @Test
    public void testGetUser() throws Exception {
        assertNull(accountService.getUser("dsadsa"));
        assertNotNull(accountService.getUser("first"));
    }

    @Test
    public void testGetRegisterdUsersCount() throws Exception {
        assertEquals(accountService.getRegisterdUsersCount(), 2);
    }

    @Test
    public void testGetLoggedUsersCount() throws Exception {
        assertEquals(accountService.getLoggedUsersCount(), 1);
    }


    @Test
    public void testGetUserBySession() throws Exception {
        assertNotNull(accountService.getUserBySession("session"));
        assertNull(accountService.getUserBySession("fewfew"));
    }

}
