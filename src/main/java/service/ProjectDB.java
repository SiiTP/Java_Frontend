package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import persistance.PlayerDataSet;
import persistance.RoomDataSet;
import persistance.UserProfile;

import java.util.Objects;

/**
 * Created by ivan on 20.11.15.
 */
public class ProjectDB {
    private static final Logger LOGGER = LogManager.getLogger(ProjectDB.class);
    private static SessionFactory s_sesssionFactory;
    private static ServiceRegistry s_serviceRegistry;
    private static String s_currentBD;

    public void initBD(){
        s_serviceRegistry = getRegistry();
        createFactory();
    }
    public void initBD(String sourceFile){
        s_serviceRegistry = getRegistry(sourceFile);
        createFactory();
    }
    public void initBD(String dbUser,String dbPass,String dbName){
        Configuration configuration = new Configuration();//.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.username",dbUser);
        configuration.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("connection.driver_class","com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.current_session_context_class","thread");
        configuration.setProperty("hbm2ddl.auto","update");
        configuration.setProperty("hibernate.connection.password",dbPass);
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/"+dbName);
        s_serviceRegistry = getRegistry(configuration);
        createFactory();
    }
    public ServiceRegistry getRegistry(){
        s_currentBD="production";
        return new StandardServiceRegistryBuilder().configure().build();
    }
    public ServiceRegistry getRegistry(String source){
        if(source.equals("hibernate-test.cfg.xml")){
            s_currentBD="test";
        }
        return new StandardServiceRegistryBuilder().configure(source).build();
    }
    public ServiceRegistry getRegistry(Configuration configuration){
        return new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    }

    private void createFactory(){
        s_sesssionFactory = new MetadataSources(s_serviceRegistry)
                .addAnnotatedClass(UserProfile.class)
                .addAnnotatedClass(PlayerDataSet.class)
                .addAnnotatedClass(RoomDataSet.class)
                .buildMetadata().buildSessionFactory();
    }
    public void dropAuth(){
        try(Session session = s_sesssionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update user u set u.isAuthorized=0");
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
    public static void truncateTables(){
        if(s_sesssionFactory != null) {
            if (Objects.equals(s_currentBD, "test")) {
                try (Session session = s_sesssionFactory.openSession()) {
                    session.beginTransaction();
                    Query query = session.createSQLQuery(" SET FOREIGN_KEY_CHECKS=0");
                    query.executeUpdate();
                    query = session.createSQLQuery("TRUNCATE TABLE room");
                    query.executeUpdate();
                    query = session.createSQLQuery("TRUNCATE TABLE player");
                    query.executeUpdate();
                    query = session.createSQLQuery("TRUNCATE TABLE user");
                    query.executeUpdate();
                    query = session.createSQLQuery(" SET FOREIGN_KEY_CHECKS=1");
                    query.executeUpdate();
                    session.getTransaction().commit();
                }catch (HibernateException exc){
                    LOGGER.error("Truncate failed",exc);
                }
            }
        }else{
            Marker marker = new MarkerManager.Log4jMarker("FAILTRUNC");
            LOGGER.error(marker,"db failed");
        }
    }

    public static SessionFactory getSessionFactory(){
        if(s_sesssionFactory == null){
            throw new HibernateException("no DB init");
        }
        return s_sesssionFactory;
    }

}
