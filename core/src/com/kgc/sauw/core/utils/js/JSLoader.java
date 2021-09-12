package com.kgc.sauw.core.utils.js;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.game.api.mod.Mods;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class JSLoader {
    public static final String imports = "" +
            "var ID = com.kgc.sauw.core.utils.ID;" +
            "var Player = com.kgc.sauw.core.entity.EntityManager.PLAYER;" +
            "var Environment = com.kgc.sauw.core.environment.Environment;";

    public static Object hookFunction(String functionName, Scriptable scriptable, Object[] args) {
        Object result = null;
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            Object fObj = scriptable.get(functionName, scriptable);
            if ((fObj instanceof Function)) {
                Function f = (Function) fObj;
                result = f.call(Mods.context, scriptable, scriptable, args);
            }
        } catch (Exception e) {
            Gdx.app.log("MOD_ERROR", e.toString());
        } finally {
            Context.exit();
        }
        return result;
    }

    public static Scriptable loadJs(String js, String sourceName) throws Exception{
        Context context = Context.enter();
        Scriptable scriptable = context.initStandardObjects();

        context.evaluateString(scriptable, imports + "\n" + js, sourceName, 1, null);

        Context.exit();
        return scriptable;
    }
}
