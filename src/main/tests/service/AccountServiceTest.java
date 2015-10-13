package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.account.AccountService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by Ivan on 11.10.15.
 */
public class AccountServiceTest {
    private AccountService accountService;
    @Before
    public void setUp() throws Exception {
        accountService = spy(new AccountService());
        accountService.addUser(new UserProfile("first","aaa"));
        accountService.addUser(new UserProfile("second","bbb"));
        accountService.authtorize("first","aaa","session");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsAuthorized() throws Exception {
        assertTrue(accountService.isAuthorized("session") && !accountService.isAuthorized("dsadsa"));
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
        doNothing().when(accountService).addSession("session2", new UserProfile("Third","ccc"));
        assertTrue(accountService.authtorize("second", "bbb", "session2"));
    }
    @Test
    public void testAuthtorizeNoUser() throws Exception {
        assertFalse(accountService.authtorize("gergerh", "bbb", "session2"));
    }

    @Test
    public void testGetUser() throws Exception {
        assertTrue(accountService.getUser("dsadsa")==null && accountService.getUser("first")!= null);
    }

    @Test
    public void testGetRegisterdUsersCount() throws Exception {
        assertTrue(accountService.getRegisterdUsersCount() == 2);
    }

    @Test
    public void testGetLoggedUsersCount() throws Exception {
        assertTrue(accountService.getLoggedUsersCount() == 1);
    }


    @Test
    public void testGetUserBySession() throws Exception {
        assertTrue(accountService.getUserBySession("session")!=null && accountService.getUserBySession("fewfew")==null);
    }

}
