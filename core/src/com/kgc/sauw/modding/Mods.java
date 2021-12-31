package com.kgc.sauw.modding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

import static com.kgc.sauw.core.resource.Files.modsDir;

public class Mods {
    public static ArrayList<Mod> mods = new ArrayList<>();

    public static void loadMods() {
        FileHandle[] modsDirs = modsDir.list();
        for (FileHandle file : modsDirs) {
            if (file.isDirectory() && file.child("manifest.json").exists()) { //Проверка является ли директория модом
                loadMod(file);
            }
        }
    }

    private static void loadMod(FileHandle modDir) {
        Mod mod = new Mod(modDir);
        mods.add(mod);
        Gdx.app.log("Mod loader", "Mod loaded with package \"" + mod.getManifest().package_ + "\"");
    }
}
