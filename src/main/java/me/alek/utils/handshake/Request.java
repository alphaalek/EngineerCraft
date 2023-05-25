package me.alek.utils.handshake;

import me.alek.utils.handshake.Handshake;

public class Request {

    private final Runnable runnable;

    public Request(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run(Handshake handshake) {
        runnable.run();
    }
}
