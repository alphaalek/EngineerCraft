package me.alek.storage;

import me.alek.hub.Hub;

import java.util.List;
import java.util.Set;

public interface StorageAdapter {

    void init();

    Set<Hub> loadHubs();
}
