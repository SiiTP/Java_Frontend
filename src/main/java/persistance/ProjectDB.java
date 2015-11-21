package persistance;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Objects;

/**
 * Created by ivan on 20.11.15.
 */
public class ProjectDB {
    private static SessionFactory s_sesssionFactory;
    private static String s_currentBD;
    public static void initBD(){
        initBD("");
    }
    public static void initBD(String source){
        if(s_sesssionFactory == null){
            StandardServiceRegistry serviceRegistry;
            if(source.isEmpty()) {
                s_currentBD="production";
                serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            }else{
                s_currentBD="test";
                serviceRegistry = new StandardServiceRegistryBuilder().configure(source).build();
            }
            s_sesssionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        }
    }
    @SuppressWarnings("SqlNoDataSourceInspection")
    public static void truncateTables(){
        if(Objects.equals(s_currentBD, "test")) {
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
            }
        }
    }

    public static SessionFactory getSessionFactory(){
        if(s_sesssionFactory == null){
            initBD();
        }
        return s_sesssionFactory;
    }

}
