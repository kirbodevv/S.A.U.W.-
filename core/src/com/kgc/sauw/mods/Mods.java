package com.kgc.sauw.mods;

import com.badlogic.gdx.Gdx;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.kgc.sauw.core.utils.Version.MOD_FORMAT;

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
                        Gdx.app.log("Mods_Loader", "mod \"" + modsArray.getJSONObject(i).getString("Mod") + "\" is not supported");
                }
            }

            Gdx.app.log("Mods_Loader", "Mods count " + names.size());

            mods = new Mod[names.size()];
            for (int i = 0; i < names.size(); i++) {
                mods[i] = new Mod(Gdx.files.external("S.A.U.W./Mods").child(names.get(i)), names.get(i));
            }

        } catch (Exception e) {
            Gdx.app.log("Mods_Loader_error", e.toString());
        } finally {
            Context.exit();
        }

        new ModTick().start();
    }

    public static void disposeResources() {
        for (Mod mod : mods) mod.modResources.dispose();
    }

    public void hookFunction(String functionName, Object[] args) {
        context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            for (Mod mod : mods) {
                Object fObj = mod.scriptable.get(functionName, mod.scriptable);
                if ((fObj instanceof Function)) {
                    Function f = (Function) fObj;
                    Object result = f.call(context, mod.scriptable, mod.scriptable, args);
                }
            }
        } catch (Exception e) {
            Gdx.app.log("MOD_ERROR", e.toString());
        } finally {
            Context.exit();
        }
    }

    public class ModTick extends Thread {
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
