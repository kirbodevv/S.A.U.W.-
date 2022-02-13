package com.kgc.sauw.modding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.js.JS;
import org.mozilla.javascript.Scriptable;

public class Module {
    private String script;
    private Scriptable scriptable;
    public String name;
    public String executable;
    public int codeVersion;
    private static final String API;

    static {
        API = Gdx.files.internal("module/api.js").readString().replaceAll("\\r?\\n", "");
    }

    public Module(FileHandle file) {
        script = API + "\n" + file.readString();
        scriptable = JS.loadJs(script, file.parent().name());
        Scriptable manifest = (Scriptable) scriptable.get("module_manifest", scriptable);
        name = (String) manifest.get("name", manifest);
        codeVersion = Integer.parseInt((String) manifest.get("code_version", manifest));
        executable = manifest.has("executable", manifest) ?
                (String) manifest.get("executable", manifest) : null;
    }

    public void execute() {
        if (executable != null) {
            JS.hookFunction(executable, scriptable, null);
        }
    }
}
