package accountserver;

import main.ServerThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apomosov on 15.05.16.
 */
public class AccountServer extends ServerThread {
    private static final Logger log = LogManager.getLogger(AccountServer.class);
    private final int port;
    private Map<String, UserProfile> users = new HashMap<>();


    public AccountServer(int port) {
        super("account_server");
        this.port = port;
    }

    public boolean addUser(UserProfile userProfile) {
        return this.users.put(userProfile.getLogin(), userProfile) == null;
    }

    public UserProfile getUser(String login) {
        return this.users.get(login);
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
        try {
            server.join();
        } catch (InterruptedException e) {
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
