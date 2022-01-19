package com.kgc.sauwlauncher.launcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Files {
    public static final File versionDir = new File(System.getProperty("user.home"), "S.A.U.W./Versions/");
    public static final File launcherSettings = new File(System.getProperty("user.home"), "S.A.U.W./launcher.settings");
    public static final File launcherLog = new File(System.getProperty("user.home"), "S.A.U.W./launcherErrorLog.txt");

    static {
        if (!launcherSettings.exists()) {
            try {
                launcherSettings.createNewFile();
                FileWriter fw = new FileWriter(launcherSettings);
                fw.write("LastVersion:null");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!launcherLog.exists()) {
            try {
                launcherLog.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
