package me.alek.utils;

public class Tuple2<V, U> {

    private V param1;
    private U param2;

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

    public void setParam1(V param1) {
        this.param1 = param1;
    }

    public void setParam2(U param2) {
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "param1: " + param1 + ", param2: " + param2;
    }
}
