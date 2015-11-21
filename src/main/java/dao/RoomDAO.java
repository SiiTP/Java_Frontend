package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import persistance.ProjectDB;
import persistance.RoomDataSet;

/**
 * Created by ivan on 20.11.15.
 */
public class RoomDAO {
    private SessionFactory sessionFactory;

    public RoomDAO() {
        this.sessionFactory = ProjectDB.getSessionFactory();
    }
    public void saveRoomInfo(RoomDataSet dataSet){
        Session session = sessionFactory.getCurrentSession();
        session.save(dataSet);
    }
}
