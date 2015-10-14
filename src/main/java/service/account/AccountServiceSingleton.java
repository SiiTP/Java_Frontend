package service.account;

/**
 * Created by ivan on 13.10.15.
 */
public final class AccountServiceSingleton {
    private static AccountService s_accountService;
    private AccountServiceSingleton(){}
    public static AccountService getInstance(){
        if(s_accountService == null){
            s_accountService = new AccountService();
        }
        return s_accountService;
    }
}
