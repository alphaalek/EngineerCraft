package me.alek.mechanics.transporter;

import org.bukkit.Location;

import java.util.List;

public interface MultipleInputTransporter extends Transporter {

    List<TransferLocation> getInputLocations();

    TransferLocation getOutputLocation();
}
