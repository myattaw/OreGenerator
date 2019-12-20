package com.reliableplugins.oregenerator.util.pair;

public abstract class Pair<L, R> {

    public static <L, R> Pair<L, R> of(L var0, R var1) {
        return new PairValue<>(var0, var1);
    }

    public abstract L getKey();

    public abstract R getValue();

    public String toString() {
        return "" + '(' + this.getKey() + ',' + this.getValue() + ')';
    }

}