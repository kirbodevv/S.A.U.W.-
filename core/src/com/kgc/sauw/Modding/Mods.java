package com.kgc.sauw.Modding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.UI.Interfaces.Interfaces;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.*;
import static com.kgc.sauw.game.SAUW.MOD_API;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.map.World.WORLD;

public class Mods {
    Context cx;
    mod mods[] = null;

    public void load() {
        cx = Context.enter();
        cx.setOptimizationLevel(-1);
        try {
            String[] names;
            FileHandle t = Gdx.files.external("S.A.U.W./Mods");
            names = t.file().list();
            if (names != null) {
                mods = new mod[names.length];
                for (int i = 0; i < names.length; i++) {
                    if (Gdx.files.external("S.A.U.W./Mods/" + names[i] + "/manifest.json").exists()) {
                        mods[i] = new mod("S.A.U.W./Mods/" + names[i]);
                        mods[i].sc = cx.initStandardObjects();

                        FileHandle MainJSFile = Gdx.files.external("S.A.U.W./Mods/" + names[i] + "/main.js");
                        String result = MainJSFile.readString();
                        ITEMS.createItems(mods[i].ItemsFolder, mods[i].resFolder);
                        CRAFTING.addCraftsFromDirectory(mods[i].CraftsFolder);
                        ScriptableObject.putProperty(mods[i].sc, "Player", PLAYER);
                        ScriptableObject.putProperty(mods[i].sc, "Blocks", BLOCKS);
                        ScriptableObject.putProperty(mods[i].sc, "Items", ITEMS);
                        ScriptableObject.putProperty(mods[i].sc, "ModAPI", MOD_API);
                        ScriptableObject.putProperty(mods[i].sc, "Settings", SETTINGS);
                        ScriptableObject.putProperty(mods[i].sc, "GI", GAME_INTERFACE);
                        ScriptableObject.putProperty(mods[i].sc, "World", WORLD);
                        ScriptableObject.putProperty(mods[i].sc, "Interfaces", Interfaces.class);
                        ScriptableObject.putProperty(mods[i].sc, "Textures", TEXTURES);

                        cx.evaluateString(mods[i].sc, result, names[i], 1, null);
                    }
                }
            }
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        } finally {
            Context.exit();
        }
        new ModTick().start();
    }

    public void HookFunction(String functionName, Object args[]) {
        cx = Context.enter();
        cx.setOptimizationLevel(-1);
        try {
            for (mod mod : mods) {
                Object fObj = mod.sc.get(functionName, mod.sc);
                if ((fObj instanceof Function)) {
                    Function f = (Function) fObj;
                    Object result = f.call(cx, mod.sc, mod.sc, args);
                }
            }
        } catch (Exception e) {
            Gdx.app.log("MOD_ERROR", e.toString());
        } finally {
            Context.exit();
        }
    }

    class ModTick extends Thread {
        Timer timer = new Timer();
        Object[] obj = new Object[]{};

        @Override
        public void run() {
            super.run();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    HookFunction("tick", obj);
                }
            }, 0, 50);

        }
    }

    public class mod {
        public Scriptable sc;
        public String modPath;
        public JSONObject manifest;
        public FileHandle CraftsFolder;
        public FileHandle ItemsFolder;
        public FileHandle resFolder;

        public mod(String ModPath) {
            try {
                String manifest = Gdx.files.external(ModPath + "/manifest.json").readString();
                this.manifest = new JSONObject(manifest);
                this.modPath = ModPath;
                CraftsFolder = Gdx.files.external(modPath + "/" + this.manifest.getString("crafts_path"));
                ItemsFolder = Gdx.files.external(modPath + "/" + this.manifest.getString("items_path"));
                resFolder = Gdx.files.external(modPath + "/" + this.manifest.getString("resources_path"));
            } catch (Exception e) {
                Gdx.app.log("ModAPI_CreateModError", e.toString());
            }

        }
    }
}
