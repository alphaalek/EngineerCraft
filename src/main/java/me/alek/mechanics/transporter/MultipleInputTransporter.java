package me.alek.mechanics.transporter;

import java.util.List;

public interface MultipleInputTransporter extends Transporter {

    List<TransferEndpoint> getInputLocations();

    TransferEndpoint getOutputLocation();
}
