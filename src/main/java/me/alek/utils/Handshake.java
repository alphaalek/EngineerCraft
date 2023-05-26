package me.alek.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Handshake {

    private List<Runnable> requests = new ArrayList<>();
    private HashMap<Integer, Runnable> assignedRequests = new HashMap<>();

    public void addRequest(Runnable request) {
        requests.add(request);
    }

    public void addRequest(int id, Runnable request) {
        assignedRequests.put(id, request);
    }


    public void onResponse() {
        if (requests.isEmpty()) return;
        for (Runnable request : requests) {
            request.run();
        }
    }

    public void removeRequest(int id) {
        assignedRequests.remove(id);
    }

    public void onResponse(int id) {
        if (assignedRequests.isEmpty()) return;
        if (!assignedRequests.containsKey(id)) return;

        assignedRequests.get(id).run();
    }
}
