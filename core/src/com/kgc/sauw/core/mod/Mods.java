package com.kgc.sauw.core.mod;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.utils.js.JSLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mozilla.javascript.Context;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.kgc.sauw.Version.MOD_FORMAT;

public class Mods {
    public static Context context;
    public static Mod[] mods;


    public void load() {
        context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            ArrayList<String> names = new ArrayList<>();
            JSONArray modsArray = new JSONArray(Gdx.files.external("S.A.U.W./Mods/Mods.json").readString());

            for (int i = 0; i < modsArray.length(); i++) {
                if (modsArray.getJSONObject(i).getBoolean("isOn")) {
                    JSONObject manifest = new JSONObject(Gdx.files.external("S.A.U.W./Mods").child(modsArray.getJSONObject(i).getString("Mod")).child("manifest.json").readString());
                    if (manifest.getInt("mod_format") == MOD_FORMAT)
                        names.add(modsArray.getJSONObject(i).getString("Mod"));
                    else
                        Gdx.app.log("Mods loader", "mod \"" + modsArray.getJSONObject(i).getString("Mod") + "\" is not supported");
                }
            }

            Gdx.app.log("Mods loader", "Mods count " + names.size());

            mods = new Mod[names.size()];
            for (int i = 0; i < names.size(); i++) {
                mods[i] = new Mod(Gdx.files.external("S.A.U.W./Mods").child(names.get(i)), names.get(i));
            }

        } catch (Exception e) {
            Gdx.app.log("Mods loader error", e.toString());
        } finally {
            Context.exit();
        }

        new ModTick().start();
    }

    public static void disposeResources() {
        for (Mod mod : mods) mod.modResources.dispose();
    }

    public static void hookFunction(String functionName, Object[] args) {
        for (Mod mod : mods) {
            JSLoader.hookFunction(functionName, mod.scriptable, args);
        }
    }


    public static class ModTick extends Thread {
        Timer timer = new Timer();
        Object[] obj = new Object[]{};

        @Override
        public void run() {
            super.run();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    hookFunction("tick", obj);
                }
            }, 0, 50);

        }
    }
}
