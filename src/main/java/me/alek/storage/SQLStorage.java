package me.alek.storage;

import me.alek.hub.Hub;

import java.util.HashSet;
import java.util.Set;

public class SQLStorage implements StorageAdapter {

    @Override
    public void init() {

    }

    @Override
    public Set<Hub> loadHubs() {
        return new HashSet<>();
    }
}
