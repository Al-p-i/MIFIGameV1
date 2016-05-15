package mechanics;

import main.ServerThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ticker.Tickable;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends ServerThread implements Tickable {
    private final static Logger log = LogManager.getLogger(Mechanics.class);

    public Mechanics() {
        super("mechanics");
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void tick(long elapsedNanos) {
        log.info("Mechanics tick() started");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error(e);
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        log.info("Mechanics tick() finished");
    }
}
