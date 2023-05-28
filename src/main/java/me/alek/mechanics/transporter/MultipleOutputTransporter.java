package me.alek.mechanics.transporter;

import org.bukkit.Location;

import java.util.List;

public interface MultipleOutputTransporter extends Transporter {

    TransferLocation getInputLocation();

    List<TransferLocation> getOutputLocations();
}
