package com.kgc.sauw.jsonbuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileUtils.putVar("game", args[0]);
        FileUtils.putVar("json_builder", args[1]);
        JsonBuilder.build();
    }
}
