package service.account;


import persistance.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resource.ResourceFactory;
import resource.ServletResources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 21.09.15.
 */

public class AccountService{
    @NotNull
    private final Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private final Map<String,UserProfile> sessions = new HashMap<>();

    private final Logger logger = LogManager.getLogger(AccountService.class);

    public boolean isAuthorized(String session){
        return sessions.containsKey(session);
    }

    public boolean isAvailableName(@Nullable String name){
        return !users.containsKey(name);
    }

    public boolean isDataWrong(String username, String password){
        ServletResources servletResources =(ServletResources) ResourceFactory.getResource("data/servlet.json");
        String regex = servletResources.getPasswordRegexPattern();
        int passwordLength = servletResources.getMinPasswordLength();
        return !(username.matches(regex) && password.matches(regex) && password.length()>=passwordLength);
    }
    public boolean authtorize(@Nullable String username,@Nullable String password,@NotNull String session){
        UserProfile profile = getUser(username);
        boolean isOk = false;
        if(profile != null && !profile.isAuthorized()){
            isOk = profile.checkPassword(password);
        }
        if(isOk){
            addSession(session, profile);
            Marker marker = new MarkerManager.Log4jMarker("LOGIN");
            logger.info(marker,"user " + username + " with session " + session);
            profile.setIsAuthorized(true);
        }
        return isOk;
    }
    public void addUser(@NotNull UserProfile userProfile){
        String userName = userProfile.getUsername();
        if (!users.containsKey(userName)) {
            Marker marker = new MarkerManager.Log4jMarker("REGISTER");
            logger.info(marker,"user name " + userName);
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
        if(sessions.containsKey(sess)) {
            Marker marker = new MarkerManager.Log4jMarker("LOGOUT");
            logger.info(marker,"user with session " + sess +" and name "+sessions.get(sess).getUsername());
            UserProfile profile = getUserBySession(sess);
            if(profile != null) {
                profile.setIsAuthorized(false);
            }
            sessions.remove(sess);

        }
    }
}

