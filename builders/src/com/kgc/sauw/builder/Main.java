package com.kgc.sauw.builder;

import com.kgc.sauw.jsonbuilder.JsonBuilder;
import com.kgc.sauw.versionbuilder.VersionBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileUtils.putVar("game", args[0]);
        FileUtils.putVar("json_builder", args[1]);
        FileUtils.putVar("version", args[2]);
        FileUtils.putVar("code_version", args[3]);
        FileUtils.putVar("mod_format", args[4]);
        FileUtils.putVar("module_format", args[5]);
        JsonBuilder.build();
        VersionBuilder.build();
    }
}
