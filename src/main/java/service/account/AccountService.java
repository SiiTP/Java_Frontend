package service.account;


import dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import persistance.UserProfile;
import resource.ResourceFactory;
import resource.ServletResources;
import service.ProjectDB;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ivan on 21.09.15.
 */

public class AccountService{
    private final UserDAO userDAO;
    @NotNull
    private final ConcurrentMap<String,UserProfile> sessions = new ConcurrentHashMap<>();

    private final Logger logger = LogManager.getLogger(AccountService.class);

    public AccountService(){
        userDAO = new UserDAO();
    }
    public boolean isAuthorized(String session){
        return sessions.containsKey(session);
    }

    public boolean isAvailableName(@Nullable String name){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        boolean isAvailable = userDAO.isAvailable(name);
        t.commit();
        return isAvailable;
    }

    public boolean isDataWrong(String username, String password){
        ServletResources servletResources =(ServletResources) ResourceFactory.getResource("src/main/resources/data/servlet.json");
        String passwordRegex = servletResources.getPasswordRegexPattern();
        String usernameRegex = servletResources.getUsernameRegexPattern();
        int passwordLength = servletResources.getMinPasswordLength();
        boolean isCorrectLength = password.length()>=passwordLength && username.length()>=passwordLength;
        return !(username.matches(usernameRegex) && password.matches(passwordRegex) && isCorrectLength);
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
            updateUserAuthorize(profile,true);
        }
        return isOk;
    }
    public void updateUserAuthorize(UserProfile profile,boolean isAuth){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        profile.setIsAuthorized(isAuth);
        userDAO.updateUserAuth(profile);
        session.getTransaction().commit();
    }
    public void updatePlayerInfo(UserProfile user){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        userDAO.updatePlayerInfo(user, user.getGameProfile().getScore());
        session.getTransaction().commit();
    }
    public void addUser(@NotNull UserProfile userProfile){
        String userName = userProfile.getUsername();
        if (isAvailableName(userName)) {
            Marker marker = new MarkerManager.Log4jMarker("REGISTER");
            logger.info(marker, "user name " + userName);
            Session session = ProjectDB.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            userDAO.create(userProfile);
            session.getTransaction().commit();
        }
    }

    @Nullable
    public UserProfile getUser(@Nullable String username){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        UserProfile profile = userDAO.get(username);
        session.getTransaction().commit();
        return profile;
    }
    public long getRegisterdUsersCount(){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        long count = userDAO.count();
        session.getTransaction().commit();
        return count;
    }
    public int getLoggedUsersCount(){
        return sessions.size();
    }
    public void addSession(String session, UserProfile userProfile){
        if(!sessions.containsKey(session)){
           sessions.put(session,userProfile);
        }
    }
    /*public PlayerDataSet getPlayerInfo(long user_id){
        Session session = ProjectDB.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        PlayerDataSet player = userDAO.getPlayerDataSetById(user_id);
        session.getTransaction().commit();
        return player;
    }*/

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
                updateUserAuthorize(profile,false);
            }
            sessions.remove(sess);
        }
    }
}

