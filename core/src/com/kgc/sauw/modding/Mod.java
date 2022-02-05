package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.GameContext;
import org.json.JSONObject;

public class Mod {
    private final Manifest manifest;
    private final Config config;
    private GameContext gameContext;
    private ModScripts scripts;
    public boolean enabled = true;

    public Mod(FileHandle modDir) {
        manifest = new Manifest(new JSONObject(modDir.child("manifest.json").readString()));
        config = new Config(new JSONObject(modDir.child("config.json").readString()));
        modDir.child("config.json").writeString(config.toString(), false);
        if ((boolean) config.get("enabled")) {
            ModLoaderKt.loadResources(modDir.child("resources"));
            ModLoaderKt.loadLocalizations(modDir.child("localizations"));
            gameContext = GameContext.registeredContext(manifest.package_);
            scripts = new ModScripts(modDir.child("scripts"));
            ModLoaderKt.loadCreativeTabs(modDir.child("creative_tabs"), gameContext);
            ModLoaderKt.loadItems(modDir.child("items"), gameContext);
            ModLoaderKt.loadAchievements(modDir.child("achievements"), scripts, gameContext);
        }
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Config getConfig() {
        return config;
    }
}
