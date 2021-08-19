package com.kgc.sauw.mods;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.item.Items;
import com.kgc.sauw.game.gui.Interfaces;
import org.json.JSONObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.game.environment.Environment.*;
import static com.kgc.sauw.game.gui.Interfaces.HUD;
import static com.kgc.sauw.mods.Mods.context;

public class Mod {
    private String ID;

    public Scriptable scriptable;
    public JSONObject manifest;
    public FileHandle modFolder;
    public FileHandle craftsFolder;
    public FileHandle itemsFolder;
    public FileHandle resFolder;
    public FileHandle libsFolder;
    public FileHandle srcFolder;
    public FileHandle mainJsFile;

    public ModResources modResources;

    public Mod(FileHandle modFolder, String sourceName) {
        try {
            this.modFolder = modFolder;
            loadManifest(modFolder);
            loadResources();
            loadItems();
            loadCrafts();
            buildSources();
            loadJs(sourceName);

            Gdx.app.log("ModAPI", "loaded mod, with id " + ID);
        } catch (Exception e) {
            Gdx.app.log("ModAPI_CreateModError", e.toString());
        }
    }

    private void loadManifest(FileHandle modFolder) {
        this.manifest = new JSONObject(modFolder.child("manifest.json").readString());
        ID = this.manifest.getString("id");

        craftsFolder = modFolder.child(this.manifest.getString("crafts_path"));
        itemsFolder = modFolder.child(this.manifest.getString("items_path"));
        resFolder = modFolder.child(this.manifest.getString("resources_path"));
        libsFolder = modFolder.child(this.manifest.getString("libs_path"));
        srcFolder = modFolder.child(this.manifest.getString("src_path"));

        mainJsFile = modFolder.child("out").child("main.js");
    }

    private void loadCrafts() {
        CRAFTING.addCraftsFromDirectory(craftsFolder);
    }

    private void loadItems() {
        Items.addItemsFromMod(itemsFolder, modResources);
    }

    private void buildSources() {
        if (!modFolder.child("out").exists()) modFolder.child("out").mkdirs();
        FileHandle mainJS = modFolder.child("out").child("main.js");
        FileHandle includesFile = srcFolder.child("src.includes");
        String[] fileNames = includesFile.readString().split("\\r\\n");
        StringBuilder srcString = new StringBuilder();
        for (String fileName : fileNames) {
            srcString.append("/*").append(fileName).append("*/\n\n");
            srcString.append(srcFolder.child(fileName).readString());
            srcString.append("\n\n");
        }
        mainJS.writeString(srcString.toString(), false);
    }

    private void loadJs(String sourceName) {
        scriptable = context.initStandardObjects();
        addProperties();
        context.evaluateString(scriptable, mainJsFile.readString(), sourceName, 1, null);
    }

    private void loadResources() {
        modResources = new ModResources();
        modResources.loadResources(resFolder);
    }

    private void addProperties() {
        ScriptableObject.putProperty(scriptable, "Player", PLAYER);
        ScriptableObject.putProperty(scriptable, "Resources", modResources);
        ScriptableObject.putProperty(scriptable, "ModAPI", ModAPI.class);
        ScriptableObject.putProperty(scriptable, "Settings", Settings.class);
        ScriptableObject.putProperty(scriptable, "GI", HUD);
        ScriptableObject.putProperty(scriptable, "World", getWorld());
        ScriptableObject.putProperty(scriptable, "Interfaces", Interfaces.class);
    }
}
