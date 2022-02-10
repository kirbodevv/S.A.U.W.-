package com.kgc.sauw.game;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class RunParameters {
    public static RunParameters INSTANCE = new RunParameters();
    @Parameter
    public List<String> parameters = new ArrayList<>();

    @Parameter(names = {"--defaultworld", "-dw"}, description = "Default world")
    public String defaultWorld = null;

    @Parameter(names = {"--devmode", "-dm"}, description = "Developer mode")
    public boolean devmode = false;

    public static void set(String[] args) {
        JCommander.newBuilder()
                .addObject(INSTANCE)
                .build()
                .parse(args);
    }
}
