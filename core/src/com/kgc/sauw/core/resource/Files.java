package com.kgc.sauw.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Files {
    public static FileHandle sauwDir = Gdx.files.external("S.A.U.W.");
    public static FileHandle userDir = sauwDir.child("User");
    public static FileHandle worldsDir = sauwDir.child("Worlds");
    public static FileHandle screenshotsDir = sauwDir.child("Screenshots");
    public static FileHandle modsDir = sauwDir.child("Mods");
    public static FileHandle settingFile = userDir.child("settings.json");
    public static FileHandle userData = userDir.child("data.json");
    public static FileHandle runParams = sauwDir.child("run.params");

    public static void createFiles() {
        if (!sauwDir.exists()) {
            sauwDir.mkdirs();
            userDir.mkdirs();
            worldsDir.mkdirs();
            screenshotsDir.mkdirs();
            modsDir.mkdirs();
            settingFile.writeString(Gdx.files.internal("json/settings.json").readString(), false);
            runParams.writeString("", false);
            //this will be removed
            userData.writeString("{\n\"SAUW_Coins\" : 0,\n\"lastWorld\":null}", false);
        }
    }
}
