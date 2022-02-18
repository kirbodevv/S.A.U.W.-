package com.kgc.sauw.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Files {
    public static FileHandle sauwDir = Gdx.files.external("S.A.U.W.");
    public static FileHandle userDir = sauwDir.child("User");
    public static FileHandle worldsDir = sauwDir.child("Worlds");
    public static FileHandle screenshotsDir = sauwDir.child("Screenshots");
    public static FileHandle modsDir = sauwDir.child("Mods");
    public static FileHandle tempDir = sauwDir.child("temp");
    public static FileHandle modulesDir = sauwDir.child("Modules");
    public static FileHandle modulesList = modulesDir.child("modules.list");
    public static FileHandle settingFile = userDir.child("settings.json");
    public static FileHandle userData = userDir.child("data.json");
    public static FileHandle runParams = sauwDir.child("run.params");

    public static void createFiles() {
        sauwDir.mkdirs();
        userDir.mkdirs();
        worldsDir.mkdirs();
        screenshotsDir.mkdirs();
        modsDir.mkdirs();
        tempDir.mkdirs();
        modulesDir.mkdirs();
        if (!settingFile.exists())
            settingFile.writeString(Gdx.files.internal("json/settings.json").readString(), false);
        if (!runParams.exists())
            runParams.writeString("", false);
        if (!modulesList.exists())
            modulesList.writeString("", false);
        //this will be removed
        if (!userData.exists())
            userData.writeString("{\n\"SAUW_Coins\" : 0,\n\"lastWorld\":null}", false);
    }

    public static FileHandle tempFile(String path) {
        return tempDir.child(path);
    }

    public static void clearTempDir() {
        FileHandle[] files = tempDir.list();
        for (FileHandle file : files) file.delete();
    }
}
