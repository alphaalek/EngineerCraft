package me.alek.mechanics.transporter;

public interface SimpleTransporter extends Transporter {

    TransferEndpoint getInputEndpoint();

    TransferEndpoint getOutputEndpoint();
}
