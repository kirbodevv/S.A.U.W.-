package com.kgc.sauw;

import com.kgc.sauw.version.Version;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Launcher {
    public static Process process;
    public static InputStream inputStream;
    public static InputStream errorStream;
    public static OutputStream outputStream;

    public static void launch(String version) throws IOException {
        if (Version.currentVersion != null && process == null) {
            process = Runtime.getRuntime().exec(
                    String.format("java -jar %s/%s.jar",
                            Version.versionsDir.path(),
                            Version.currentVersion.name));
            inputStream = process.getInputStream();
            errorStream = process.getErrorStream();
            outputStream = process.getOutputStream();
        }
    }

    public static void kill() {
        process.destroy();
        process = null;
    }
}
