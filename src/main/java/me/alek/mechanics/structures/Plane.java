package me.alek.mechanics.structures;

import me.alek.utils.Tuple2;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Plane {

    private Set<Tuple2<Integer, Integer>> offsets = new HashSet<>();
    private HashMap<Integer, TreeSet<Integer>> direct = new HashMap<>();

    public void addPoint(int x, int y) {
        if (!direct.containsKey(x)) {
            direct.put(x, new TreeSet<>());
        } else {
            if (direct.get(x).contains(y)) {
                return;
            }
        }
        offsets.add(new Tuple2<>(x, y));
        direct.get(x).add(y);
    }

    public void addPoint(@NotNull Tuple2<Integer, Integer> tuple) {
        addPoint(tuple.getParam1(), tuple.getParam2());
    }

    public void addAll(List<Tuple2<Integer, Integer>> tuples) {
        offsets.addAll(tuples);
        for (Tuple2<Integer, Integer> offset : tuples) {
            addPoint(offset.getParam1(), offset.getParam2());
        }
    }

    public boolean intersect(@NotNull Plane plane) {
        for (Map.Entry<Integer, TreeSet<Integer>> entryDirect : plane.getDirect().entrySet()) {
            if (!this.direct.containsKey(entryDirect.getKey())) {
                continue;
            }
            for (int yOffset : this.direct.get(entryDirect.getKey())) {
                if (entryDirect.getValue().contains(yOffset)) {
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<Integer, TreeSet<Integer>> getDirect() {
        return direct;
    }
}
