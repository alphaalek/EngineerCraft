package me.alek.mechanics;

public interface WorkerMechanic extends Mechanic, Transporter {

    void tick();

    boolean isWorking();

    void setWorking(boolean working);
}
