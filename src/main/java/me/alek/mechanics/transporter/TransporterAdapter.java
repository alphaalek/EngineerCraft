package me.alek.mechanics.transporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransporterAdapter {

    public static List<TransferEndpoint> getInputLocations(Transporter transporter) {
        if (transporter instanceof SimpleTransporter) {
            return Collections.singletonList(((SimpleTransporter) transporter).getInputEndpoint());
        }
        else if (transporter instanceof MultipleOutputTransporter) {
            return Collections.singletonList(((MultipleOutputTransporter)transporter).getInputLocation());
        }
        else if (transporter instanceof MultipleInputTransporter) {
            return ((MultipleInputTransporter)transporter).getInputLocations();
        }
        else {
            return new ArrayList<>();
        }
    }

    public static List<TransferEndpoint> getOutputLocations(Transporter transporter) {
        if (transporter instanceof SimpleTransporter) {
            return Collections.singletonList(((SimpleTransporter) transporter).getOutputEndpoint());
        }
        else if (transporter instanceof MultipleOutputTransporter) {
            return ((MultipleOutputTransporter)transporter).getOutputLocations();
        }
        else if (transporter instanceof MultipleInputTransporter) {
            return Collections.singletonList(((MultipleInputTransporter)transporter).getOutputLocation());
        }
        else {
            return new ArrayList<>();
        }
    }
}
