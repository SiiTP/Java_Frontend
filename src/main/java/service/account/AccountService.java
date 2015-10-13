package service.account;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import service.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 21.09.15.
 */

public class AccountService{
    @NotNull
    private Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private Map<String,UserProfile> sessions = new HashMap<>();



    public boolean isAuthorized(String session){
        return sessions.containsKey(session);
    }

    public boolean isAvailableName(@Nullable String name){
        return !users.containsKey(name);
    }
    public boolean checkData(String username,String password){
        return username.matches("^[a-zA-Z]+$") && password.matches("^[a-zA-Z]+$");
    }
    public boolean authtorize(@Nullable String username,@Nullable String password,@NotNull String session){
        UserProfile profile = getUser(username);
        boolean isOk = false;
        if(profile != null){
            isOk = profile.checkPassword(password);

        }
        if(isOk){
            addSession(session, profile);
        }
        return isOk;
    }
    public void addUser(@NotNull UserProfile userProfile){
        String userName = userProfile.getUsername();
        if (!users.containsKey(userName)) {
            users.put(userName, userProfile);
        }
    }

    @Nullable
    public UserProfile getUser(@Nullable String username){
        return users.get(username);
    }
    public int getRegisterdUsersCount(){
        return users.size();
    }
    public int getLoggedUsersCount(){
        return sessions.size();
    }
    public void addSession(String session, UserProfile userProfile){
        if(!sessions.containsKey(session)){
           sessions.put(session,userProfile);
        }
    }
    @Nullable
    public UserProfile getUserBySession(String sessionId){
        return sessions.get(sessionId);
    }

    public void deleteSession(String sess) {
        sessions.remove(sess);
    }

}

