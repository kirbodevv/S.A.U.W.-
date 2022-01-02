package com.kgc.sauw.core.achievements;

import com.kgc.sauw.core.utils.js.JS;
import org.mozilla.javascript.Scriptable;

public class JSChecker implements AchievementChecker {
    Scriptable scriptable;

    private static final Object[] objects = new Object[]{};

    public JSChecker(String js, String achievementName) {
        try {
            this.scriptable = JS.loadJs(js, achievementName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
        return (boolean) JS.hookFunction("check", scriptable, objects);
    }
}
