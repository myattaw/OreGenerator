package com.reliableplugins.oregenerator.util.pair;

public class PairValue<L, R> extends Pair<L, R> {

    private L key;
    private R value;

    public PairValue(L key, R value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public L getKey() {
        return key;
    }

    @Override
    public R getValue() {
        return value;
    }

}