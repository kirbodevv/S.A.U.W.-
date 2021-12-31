package com.kgc.sauw.modding;

import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ModTest {
    static String manifest = "{\n" +
            "  \"package\": \"com.kgc.example_mod\",\n" +
            "  \"mod_format\": 1,\n" +
            "  \"authors\": [\n" +
            "    \"Kirbo\"\n" +
            "  ],\n" +
            "  \"header\": {\n" +
            "    \"name\": \"Mod name\",\n" +
            "    \"description\": \"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam\"\n" +
            "  },\n" +
            "  \"dependencies\": []" +
            "}";
    static String config = "{\"some_setting\":123}";

    @Test
    void test() {
        printManifest(new Manifest(new JSONObject(manifest)));
        printConfig(new Config(new JSONObject(config)));
    }

    private static void printManifest(Manifest manifest) {
        System.out.println("ModManifest {");
        System.out.println("\tPackage: " + manifest.package_);
        System.out.println("\tMod format: " + manifest.modFormat);
        System.out.println("\tName: " + manifest.name);
        System.out.println("\tDescription: " + manifest.description);
        System.out.println("\tAuthors: " + Arrays.toString(manifest.authors));
        System.out.println("\tDependencies: " + Arrays.toString(manifest.dependencies));
        System.out.println("}");
    }

    private static void printConfig(Config config) {
        Map<String, Object> map = config.toMap();
        Set<String> keySet = map.keySet();
        System.out.println("ModConfig {");
        for (String s : keySet) {
            System.out.println("\t" + s + ": " + map.get(s));
        }
        System.out.println("}");
    }
}
