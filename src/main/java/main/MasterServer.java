package main;

import accountserver.AccountServer;
import frontend.ReplicationServer;
import mechanics.Mechanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
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
    @NotNull
    private final static Logger log = LogManager.getLogger(MasterServer.class);
    @NotNull
    private final List<ServerThread> serverThreads = new ArrayList<>();


    private void start() throws ExecutionException, InterruptedException {
        log.info("MasterServer started");
        AccountServer accountServer = new AccountServer(7000);
        Mechanics mechanics = new Mechanics();
        Ticker ticker = new Ticker(1);
        ticker.registerTickable(mechanics);
        ReplicationServer replicationServer = new ReplicationServer(8080);

        serverThreads.add(accountServer);
        serverThreads.add(mechanics);
        serverThreads.add(ticker);
        serverThreads.add(replicationServer);

        serverThreads.forEach(ServerThread::start);

        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        ticker.interrupt();

        for (ServerThread serverThread : serverThreads) {
            serverThread.join();
        }
    }

    public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
        MasterServer server = new MasterServer();
        server.start();
    }
}
