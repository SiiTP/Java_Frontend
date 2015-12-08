package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.ProjectDB;
import persistance.RoomDataSet;

import java.util.List;

/**
 * Created by ivan on 20.11.15.
 */
public class RoomDAO {
    private final SessionFactory sessionFactory;

    public RoomDAO() {
        this.sessionFactory = ProjectDB.getSessionFactory();
    }
    public void saveRoomInfo(RoomDataSet dataSet){
        Session session = sessionFactory.getCurrentSession();
        session.save(dataSet);
    }
    public List getTopPlayers(int limit){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.getNamedQuery("getTopPlayers");
        query.setMaxResults(limit);
        return query.list();
    }
    public RoomDataSet getRoomInfoByName(String roomname){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.getNamedQuery("roomInfoByName");
        query.setString("name", roomname);
        return (RoomDataSet)query.uniqueResult();
    }

}
