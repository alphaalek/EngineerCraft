package me.alek.utils.handshake;

public class OneTimeRequest extends Request {

    public OneTimeRequest(Runnable runnable) {
        super(runnable);
    }

    @Override
    public void run(Handshake handshake) {
        super.run(handshake);
        handshake.removeRequest(this);
    }
}
