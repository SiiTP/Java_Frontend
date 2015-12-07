package dao;

import game.user.GameProfile;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistance.PlayerDataSet;
import service.ProjectDB;
import persistance.UserProfile;

import static org.junit.Assert.*;

/**
 * Created by ivan on 21.11.15.
 */
@SuppressWarnings("unused")
public class UserDAOTest {
    UserDAO userDAO;
    Transaction t;
    SessionFactory sessionFactory;
    @Before
    public void setUp() {
        ProjectDB.initBD("hibernate-test.cfg.xml");
        userDAO = new UserDAO();
        sessionFactory = ProjectDB.getSessionFactory();
    }
    @Test
    public void testCreateUser(){
        UserProfile p = new UserProfile("test","test");

        t = sessionFactory.getCurrentSession().beginTransaction();
        userDAO.create(p);

        t = sessionFactory.getCurrentSession().beginTransaction();
        boolean isAvailable = userDAO.isAvailable("test");
        t.commit();
        assertFalse(isAvailable);
    }
    @Test
    public void testGetUser(){
        UserProfile p = new UserProfile("test","test");
        t = sessionFactory.getCurrentSession().beginTransaction();
        userDAO.create(p);
        t.commit();
        t = sessionFactory.getCurrentSession().beginTransaction();
        UserProfile p2 = userDAO.get("test");
        t.commit();
        assertNotNull(p2);
        assertEquals(p2.getUsername(), p.getUsername());
    }
    @Test
    public void testUsersCount(){
        UserProfile p = new UserProfile("test","test");
        UserProfile p2 = new UserProfile("test2","test2");
        t = sessionFactory.getCurrentSession().beginTransaction();
        userDAO.create(p);
        userDAO.create(p2);
        t.commit();
        t = sessionFactory.getCurrentSession().beginTransaction();
        long count = userDAO.count();
        t.commit();
        assertEquals(count,2);
    }
    @Test
    public void testUpdateInfo(){
        UserProfile p = new UserProfile("test","test");
        GameProfile gameProfile = p.getGameProfile();
        gameProfile.setScore(5);

        t = sessionFactory.getCurrentSession().beginTransaction();
        userDAO.create(p);
        userDAO.updatePlayerInfo(p, gameProfile.getScore());
        t.commit();

        t = sessionFactory.getCurrentSession().beginTransaction();
        PlayerDataSet player = userDAO.getPlayerDataSetById(p.getId());
        t.commit();

        assertEquals(player.getScoreCount().longValue(),5);
    }
    @After
    public void clear(){
        ProjectDB.truncateTables();
    }
}