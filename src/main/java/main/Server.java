package main;

import mechanics.Mechanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ticker.Ticker;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by apomosov on 14.05.16.
 */
public class Server {
    private final static Logger log = LogManager.getLogger(Server.class);
    private final Ticker ticker = new Ticker(1);

    private void start() throws ExecutionException, InterruptedException {
        log.info("Server started");

        Mechanics mechanics = new Mechanics();
        ticker.registerTickable(mechanics);

        ticker.start();

        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        ticker.interrupt();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Server server = new Server();
        server.start();
    }
}
