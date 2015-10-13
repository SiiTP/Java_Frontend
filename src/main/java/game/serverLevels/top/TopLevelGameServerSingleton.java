package game.serverLevels.top;

import service.account.AccountServiceSingleton;

/**
 * Created by ivan on 13.10.15.
 */
public class TopLevelGameServerSingleton {
    private static TopLevelGameServer topLevelGameServer;
    public static TopLevelGameServer getInstance(){
        if (topLevelGameServer == null){
            topLevelGameServer = new TopLevelGameServer(AccountServiceSingleton.getInstance());
        }
        return topLevelGameServer;
    }
}
