package com.kgc.sauw.versionbuilder;

import com.kgc.sauw.builder.FileUtils;

import java.io.File;
import java.io.IOException;

public class VersionBuilder {
    public static void build() throws IOException {
        File versionFile = FileUtils.getFile("%game%/src/com/kgc/sauw/Version.java");
        FileUtils.writeString(versionFile, buildVersion());
    }

    private static String buildVersion() throws IOException {
        String template = FileUtils.readFile("%json_builder%/templates/version.txt");
        StringBuilder code = new StringBuilder();
        code.append("\t").append("public static String VERSION = \"").append(FileUtils.variables.get("version")).append("\";");
        code.append("\n\t").append("public static int CODE_VERSION = ").append(FileUtils.variables.get("code_version")).append(";");
        code.append("\n\t").append("public static int MOD_FORMAT = ").append(FileUtils.variables.get("module_format")).append(";");
        code.append("\n\t").append("public static int MODULE_FORMAT = ").append(FileUtils.variables.get("mod_format")).append(";");
        return template.replace("$version$", code);
    }
}
