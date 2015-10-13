package service.account;

/**
 * Created by ivan on 13.10.15.
 */
public class AccountServiceSingleton {
    private static AccountService accountService;
    public static AccountService getInstance(){
        if(accountService == null){
            accountService = new AccountService();
        }
        return accountService;
    }
}
