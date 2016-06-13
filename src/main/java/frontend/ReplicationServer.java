package frontend;

import accountserver.SignInServlet;
import main.ApplicationContext;
import main.MasterServer;
import main.ServerThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by apomosov on 13.06.16.
 */
public class ReplicationServer extends ServerThread {
    private final static Logger log = LogManager.getLogger(MasterServer.class);
    private final int port;

    public ReplicationServer(int port) {
        super("replication_server");
        this.port = port;
    }

    @Override
    public void run() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        server.setHandler(ApplicationContext.getInstance());

        // Add a websocket to a specific path spec
        ReplicationServlet replicationServlet = new ReplicationServlet();
        ApplicationContext.getInstance().addServlet(new ServletHolder(replicationServlet), "/replication");

        log.info("ReplicationServer started on port " + port);

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
}
