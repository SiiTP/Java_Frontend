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
    private SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = ProjectDB.getSessionFactory();
    }

    public UserProfile get(@Nullable String username) {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Query query = session.getNamedQuery("userByName");
            query.setString("username", username);
            UserProfile p = (UserProfile) query.uniqueResult();
            session.getTransaction().commit();
            return p;
        }
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
}
