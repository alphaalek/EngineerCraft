package me.alek.utils;

public class Tuple2<V, U> {

    private final V param1;
    private final U param2;

    public Tuple2(V param1, U param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public V getParam1() {
        return param1;
    }

    public U getParam2() {
        return param2;
    }
}
