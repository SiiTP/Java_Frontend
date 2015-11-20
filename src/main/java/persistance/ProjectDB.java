package persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by ivan on 20.11.15.
 */
public class ProjectDB {
    private static SessionFactory s_sesssionFactory;

    public static void initBD(){
        if(s_sesssionFactory == null){
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            s_sesssionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        }
    }
    public static SessionFactory getSessionFactory(){
        if(s_sesssionFactory == null){
            initBD();
        }
        return s_sesssionFactory;
    }
}