package dao;

import game.rooms.RoomFFA;
import game.user.GameProfile;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistance.ProjectDB;
import persistance.RoomDataSet;
import persistance.UserProfile;
import service.account.AccountService;

import static org.junit.Assert.*;

/**
 * Created by ivan on 21.11.15.
 */
public class RoomDAOTest {
    RoomDAO roomDAO;
    Transaction t;
    SessionFactory sessionFactory;
    @Before
    public void setUp() throws Exception {
        ProjectDB.initBD("hibernate-test.cfg.xml");
        roomDAO = new RoomDAO();
        sessionFactory = ProjectDB.getSessionFactory();
    }
    @Test
    public void testRoomInfo(){
        AccountService service1 = new AccountService();
        UserProfile p = new UserProfile("test2","test2");
        UserProfile p2 = new UserProfile("test1","test1");

        service1.addUser(p);
        service1.addUser(p2);
        RoomFFA ffa = new RoomFFA("test","test");
        GameProfile gameProfile = p.getGameProfile();
        gameProfile.setScore(23);
        GameProfile gameProfile1 = p2.getGameProfile();
        gameProfile1.setScore(1);
        ffa.addUser(p);
        ffa.addUser(p2);
        sessionFactory.getCurrentSession().beginTransaction();
        roomDAO.saveRoomInfo(new RoomDataSet(ffa));
        sessionFactory.getCurrentSession().getTransaction().commit();
        sessionFactory.getCurrentSession().beginTransaction();
        RoomDataSet dataSet = roomDAO.getRoomInfoByName(ffa.getRoomName());
        UserProfile winner = dataSet.getWinner();
        sessionFactory.getCurrentSession().getTransaction().commit();
        assertEquals(winner.getUsername(),p.getUsername());
    }
    @After
    public void clear(){
        ProjectDB.truncateTables();
    }
}