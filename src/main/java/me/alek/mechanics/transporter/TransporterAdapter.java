package me.alek.mechanics.transporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransporterAdapter {

    public static List<TransferLocation> getInputLocations(Transporter transporter) {
        if (transporter instanceof SimpleTransporter) {
            return Collections.singletonList(((SimpleTransporter) transporter).getInputLocation());
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

    public static List<TransferLocation> getOutputLocations(Transporter transporter) {
        if (transporter instanceof SimpleTransporter) {
            return Collections.singletonList(((SimpleTransporter) transporter).getOutputLocation());
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
