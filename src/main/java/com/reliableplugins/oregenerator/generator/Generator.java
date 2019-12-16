package com.reliableplugins.oregenerator.generator;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private List<Generator> generators;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.generators = new ArrayList<>();
    }

    public String getPermission() {
        return "oregenerator.use." + name;
    }

}
