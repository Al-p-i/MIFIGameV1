package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by apomosov on 22.05.16.
 */
public class DatabaseService {
    private static final Logger log = LogManager.getLogger(DatabaseService.class);

    private static final DatabaseService instance = new DatabaseService();

    public static DatabaseService getInstance() {
        return instance;
    }

    private @NotNull SessionFactory sessionFactory;

    protected DatabaseService() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            log.error(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
