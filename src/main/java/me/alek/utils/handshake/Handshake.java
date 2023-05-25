package me.alek.utils.handshake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Handshake {

    private List<Request> requests = new ArrayList<>();
    private HashMap<Integer, Request> assignedRequests = new HashMap<>();

    public void addRequest(Runnable request) {
        requests.add(new Request(request));
    }

    public void addRequest(int id, Runnable request) {
        assignedRequests.put(id, new Request(request));
    }

    public void addOneTimeRequest(Runnable request) {
        requests.add(new OneTimeRequest(request));
    }

    public void removeRequest(Request request) {
        requests.remove(request);
    }

    public void onResponse() {
        if (requests.isEmpty()) return;
        for (Request request : requests) {
            request.run(this);
        }
    }

    public void removeRequest(int id) {
        assignedRequests.remove(id);
    }

    public void onResponse(int id) {
        if (assignedRequests.isEmpty()) return;
        if (!assignedRequests.containsKey(id)) return;

        assignedRequests.get(id).run(this);
    }
}
