package game.serverLevels.top;

import service.account.AccountServiceSingleton;

/**
 * Created by ivan on 13.10.15.
 */
public final class TopLevelGameServerSingleton {
    private static TopLevelGameServer s_topLevelGameServer;
    private TopLevelGameServerSingleton(){}
    public static TopLevelGameServer getInstance(){
        if (s_topLevelGameServer == null){
            s_topLevelGameServer = new TopLevelGameServer(AccountServiceSingleton.getInstance());
        }
        return s_topLevelGameServer;
    }
}
