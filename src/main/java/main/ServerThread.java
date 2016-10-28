package main;

import org.jetbrains.annotations.NotNull;

/**
 * Created by apomosov on 15.05.16.
 */
public abstract class ServerThread extends Thread {
    public ServerThread(@NotNull String name) {
        super(name);
    }
}
