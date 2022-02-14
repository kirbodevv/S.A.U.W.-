package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import java.net.URI;

public class Application {
    public static FileDownloader fileDownloader;
    public static APKFileOpener apkOpener;

    public static void openURI(String uri) {
        String baseCommand = null;
        if (SharedLibraryLoader.isWindows) {
            baseCommand = "start";
        } else if (SharedLibraryLoader.isLinux) {
            baseCommand = "xdg-open";
        } else if (SharedLibraryLoader.isMac) {
            baseCommand = "open";
        } else if (SharedLibraryLoader.isAndroid) {
            Gdx.net.openURI(uri);
        }
        try {
            if (baseCommand != null)
                new ProcessBuilder(baseCommand, new URI(uri).toString()).start();
        } catch (Exception e) {
            Gdx.app.error("Error", "Error", e);
        }
    }
}
