package me.alek.mechanics.transporter;

import org.bukkit.Location;

public interface SimpleTransporter extends Transporter {

    TransferLocation getInputLocation();

    TransferLocation getOutputLocation();
}
