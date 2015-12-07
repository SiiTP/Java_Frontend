package dao;

import persistance.UserProfile;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.Nullable;
import persistance.PlayerDataSet;
import service.ProjectDB;

/**
 * Created by ivan on 20.11.15.
 */
public class UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = ProjectDB.getSessionFactory();
    }

    public UserProfile get(@Nullable String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.getNamedQuery("userByName");
        query.setString("username", username);
        return (UserProfile) query.uniqueResult();
    }

    public boolean isAvailable(@Nullable String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.getNamedQuery("isAvailable");
        query.setString("username", name);
        UserProfile profile = (UserProfile)query.uniqueResult();
        return profile == null;
    }

    public void create(UserProfile dataSet) {
        PlayerDataSet playerDataSet = new PlayerDataSet();
        dataSet.setPlayer(playerDataSet);
        playerDataSet.setUser(dataSet);
        Session session = sessionFactory.getCurrentSession();
        session.save(dataSet);
    }

    public long count() {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from user");
        return (long)query.uniqueResult();
    }

    public void updatePlayerInfo(UserProfile user, long score) {
        Session session = sessionFactory.getCurrentSession();
        PlayerDataSet dataSet = user.getPlayerDataSet();
        dataSet.setScoreCount(dataSet.getScoreCount()+score);
        session.update(dataSet);
    }

    public PlayerDataSet getPlayerDataSetById(long user_id) {
        Session session = sessionFactory.getCurrentSession();
        UserProfile profile = session.get(UserProfile.class,user_id);
        return profile.getPlayerDataSet();
    }


    public void updateUserAuth(UserProfile profile) {
        Session session = sessionFactory.getCurrentSession();
        session.update(profile);
    }
}
