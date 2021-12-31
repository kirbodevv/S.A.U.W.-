package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.GameContext;
import org.json.JSONObject;

public class Mod {
    private GameContext gameContext;
    private final Manifest manifest;
    private final Config config;
    private ModItems modItems;
    private ModResources modResources;
    private ModLocalization modLocalization;
    public boolean enabled = true;

    public Mod(FileHandle modDir) {
        manifest = new Manifest(new JSONObject(modDir.child("manifest.json").readString()));
        config = new Config(new JSONObject(modDir.child("config.json").readString()));
        modDir.child("config.json").writeString(config.toString(), false);
        if ((boolean) config.get("enabled")) {
            modResources = new ModResources(modDir.child("resources"));
            modLocalization = new ModLocalization(modDir.child("localizations"));
            gameContext = GameContext.registeredContext(manifest.package_);
            modItems = new ModItems(modDir.child("items"), gameContext);
        }
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Config getConfig() {
        return config;
    }
}
