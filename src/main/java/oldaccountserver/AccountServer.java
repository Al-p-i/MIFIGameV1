package oldaccountserver;

import dao.DatabaseService;
import dao.UserProfileDAO;
import dao.UserProfileDAOImpl;
import main.ServerThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by apomosov on 15.05.16.
 */
public class AccountServer extends ServerThread {
  @NotNull
  private static final Logger log = LogManager.getLogger(AccountServer.class);
  private final int port;


  public AccountServer(int port) {
    super("account_server");
    this.port = port;
  }

  public boolean addUser(@NotNull UserProfile userProfile) {
    UserProfileDAO userProfileDAO;
    try {
      userProfileDAO = new UserProfileDAOImpl(DatabaseService.getInstance().getSessionFactory());
    } catch (Exception e) {
      log.error(e);
      return false;
    }
    userProfileDAO.addUserProfile(userProfile);
    return true;
  }

  @NotNull
  public UserProfile getUser(@NotNull String login) {
    UserProfileDAO userProfileDAO;
    try {
      userProfileDAO = new UserProfileDAOImpl(DatabaseService.getInstance().getSessionFactory());
    } catch (Exception e) {
      log.error(e);
      return null;
    }
    return userProfileDAO.getUserByLogin(login);
  }

  @Override
  public void run() {
    super.run();
    log.info("Account server started on port " + port);
    Server server = new Server(port);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.addServlet(new ServletHolder(new SignInServlet(this)), "/signin");
    context.addServlet(new ServletHolder(new SignUpServlet(this)), "/signup");

    server.setHandler(context);

    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void interrupt() {
    super.interrupt();
    log.info("Account server was interrupted");
  }

  public static void main(String[] args) throws InterruptedException {
    AccountServer accountServer = new AccountServer(7000);
    accountServer.start();
    accountServer.join();
  }
}
