import accountserver.UserProfile;
import dao.DatabaseService;
import dao.UserProfileDAO;
import dao.UserProfileDAOImpl;
import org.junit.Test;

/**
 * Created by apomosov on 22.05.16.
 */
public class PersistenceTest {
    @Test
    public void test() throws Exception {
        UserProfileDAO userProfileDAO = new UserProfileDAOImpl(DatabaseService.getInstance().getSessionFactory());
        userProfileDAO.addUserProfile(new UserProfile("log", "pass"));
        userProfileDAO.addUserProfile(new UserProfile("log2", "pass2"));
        userProfileDAO.listUserProfiles();
        userProfileDAO.getUserByLogin("log");
    }
}
