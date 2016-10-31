package dao;

import persistantaccountserver.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by apomosov on 21.05.16.
 */
public class UserProfileDAOImpl implements UserProfileDAO {
    private static final Logger log = LogManager.getLogger(UserProfileDAO.class);
    private SessionFactory sessionFactory;
    public UserProfileDAOImpl(SessionFactory sessionFactory) throws Exception {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUserProfile(@NotNull UserProfile userProfile) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(userProfile);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from UserProfile U where U.login = " + '\'' + login + '\'');
        List<UserProfile> result = query.list();
        System.out.println(query.getQueryString());
        result.forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
        if (result.isEmpty()) {
            return null;
        }
        assert result.size() == 1;
        return result.get(0);
    }

    public List<UserProfile> listUserProfiles() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from UserProfile");
        List<UserProfile> result = query.list();
        System.out.println(query.getQueryString());
        result.forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
