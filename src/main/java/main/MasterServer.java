package main;

import accountserver.AccountServer;
import mechanics.Mechanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ticker.Ticker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
    private final static Logger log = LogManager.getLogger(MasterServer.class);
    private final List<ServerThread> serverThreads = new ArrayList<>();


    private void start() throws ExecutionException, InterruptedException {
        log.info("MasterServer started");
        AccountServer accountServer = new AccountServer(7000);
        Mechanics mechanics = new Mechanics();
        Ticker ticker = new Ticker(1);
        ticker.registerTickable(mechanics);

        serverThreads.add(accountServer);
        serverThreads.add(mechanics);
        serverThreads.add(ticker);

        serverThreads.forEach(ServerThread::start);

        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        ticker.interrupt();

        for (ServerThread serverThread : serverThreads) {
            serverThread.join();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MasterServer server = new MasterServer();
        server.start();
    }
}
