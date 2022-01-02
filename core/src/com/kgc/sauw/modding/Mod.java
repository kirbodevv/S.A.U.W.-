package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.GameContext;
import org.json.JSONObject;

public class Mod {
    private final Manifest manifest;
    private final Config config;
    private GameContext gameContext;
    private ModScripts scripts;
    private ModAchievements achievements;
    private ModItems items;
    private ModResources resources;
    private ModLocalization localization;
    public boolean enabled = true;

    public Mod(FileHandle modDir) {
        manifest = new Manifest(new JSONObject(modDir.child("manifest.json").readString()));
        config = new Config(new JSONObject(modDir.child("config.json").readString()));
        modDir.child("config.json").writeString(config.toString(), false);
        if ((boolean) config.get("enabled")) {
            resources = new ModResources(modDir.child("resources"));
            localization = new ModLocalization(modDir.child("localizations"));
            gameContext = GameContext.registeredContext(manifest.package_);
            scripts = new ModScripts(modDir.child("scripts"));
            items = new ModItems(modDir.child("items"), gameContext);
            achievements = new ModAchievements(modDir.child("achievements"), scripts, gameContext);
        }
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Config getConfig() {
        return config;
    }
}
