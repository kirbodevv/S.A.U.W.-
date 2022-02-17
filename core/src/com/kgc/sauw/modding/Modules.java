package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.Application;
import com.kgc.utils.FileDownloader;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.kgc.sauw.core.resource.Files.modulesDir;
import static com.kgc.sauw.core.resource.Files.modulesList;

public class Modules {
    public static final HashMap<String, Module> libs = new HashMap<>();
    public static final HashMap<String, Module> executable = new HashMap<>();

    public static ArrayList<String> modulesToDownload = new ArrayList<>();

    private static final String GITHUB_URL = "https://github.com/";

    public static void load() {
        String[] modules = modulesList.readString().split("\\r?\\n");
        for (String module : modules) {
            if (!module.isEmpty() && !module.startsWith("//")) {
                if (!isLocal(module)) {
                    if (!exist(module)) {
                        modulesToDownload.add(module);
                    } else {
                        loadModule(module);
                    }
                }
            }
        }
        startDownloading();
    }

    public static void loadModule(String module) {
        FileHandle file = getModulePath(module);
        Module module_ = new Module(file);
        if (module_.executable == null) libs.put(module, module_);
        else {
            module_.execute();
            executable.put(module, module_);
        }

    }

    public static void startDownloading() {
        for (String module : modulesToDownload) {
            Application.fileDownloader.download(
                    getModuleURL(module), getModulePath(module), new FileDownloader.ProgressListener() {
                        @Override
                        public void update(int progress) {
                        }

                        @Override
                        public void done() {
                            loadModule(module);
                        }

                        @Override
                        public void failed(Throwable throwable) {
                        }
                    }
            );
        }
    }

    private static boolean isLocal(String module) {
        return module.split(":").length > 3;
    }

    private static boolean exist(String module) {
        return getModulePath(module).exists();
    }

    private static String getModuleURL(String module) {
        String[] keys = module.split(":");
        return MessageFormat.format("{0}{1}/{2}/releases/download/{3}/{3}.js",
                GITHUB_URL, keys[0], keys[1], keys[2]);
    }

    private static FileHandle getModulePath(String module) {
        String[] keys = module.split(":");
        return modulesDir.child(keys[0]).child(keys[1]).child(keys[2] + ".js");
    }
}
