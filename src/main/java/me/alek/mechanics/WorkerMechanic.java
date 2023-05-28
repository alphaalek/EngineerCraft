package me.alek.mechanics;

import me.alek.mechanics.transporter.Transporter;

public interface WorkerMechanic extends Mechanic {

    void tick();

    boolean isWorking();

    void setWorking(boolean working);
}
