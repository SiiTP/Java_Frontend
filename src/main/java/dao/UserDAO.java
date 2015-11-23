package dao;

import persistance.UserProfile;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.Nullable;
import persistance.PlayerDataSet;
import persistance.ProjectDB;

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
        Query query = sessionFactory.getCurrentSession().getNamedQuery("updatePlayer");
        query.setLong("score", score);
        query.setLong("user_id", user.getId());
        query.executeUpdate();
    }

    public PlayerDataSet getPlayerDataSetById(long user_id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("getPlayerInfo");
        query.setLong("id", user_id);
        return (PlayerDataSet) query.uniqueResult();
    }
}
