package main;

import accountserver.AccountServer;
import network.ClientConnectionServer;
import mechanics.Mechanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Ticker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        AccountServer accountServer = new AccountServer(8080);
        Mechanics mechanics = new Mechanics();
        Ticker ticker = new Ticker(1);
        ticker.registerTickable(mechanics);
        ClientConnectionServer clientConnectionServer = new ClientConnectionServer(7000);

        serverThreads.add(accountServer);
        //serverThreads.add(mechanics);
        serverThreads.add(clientConnectionServer);
        //serverThreads.add(ticker);

        serverThreads.forEach(ServerThread::start);

        for (ServerThread serverThread : serverThreads) {
            serverThread.join();
        }
    }

    public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
        MasterServer server = new MasterServer();
        server.start();
    }
}
