package me.alek.mechanics.transporter;

import java.util.List;

public interface MultipleOutputTransporter extends Transporter {

    TransferEndpoint getInputLocation();

    List<TransferEndpoint> getOutputLocations();
}
