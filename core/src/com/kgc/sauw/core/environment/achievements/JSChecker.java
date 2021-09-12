package com.kgc.sauw.core.environment.achievements;

import com.kgc.sauw.core.utils.js.JSLoader;
import org.mozilla.javascript.Scriptable;

public class JSChecker implements AchievementChecker {
    Scriptable scriptable;

    private static final Object[] objects = new Object[]{};

    public JSChecker(String js, String achievementName) {
        try {
            this.scriptable = JSLoader.loadJs(js, achievementName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
        return (boolean) JSLoader.hookFunction("check", scriptable, objects);
    }
}
