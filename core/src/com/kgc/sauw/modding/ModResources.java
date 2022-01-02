package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.Variables;
import org.json.JSONObject;

public class ModResources {
    public ModResources(FileHandle modResourcesDir) {
        JSONObject resourceVariables = new JSONObject(modResourcesDir.child("resources.json").readString());
        for (String key : resourceVariables.keySet()) {
            FileHandle resource = modResourcesDir.child(resourceVariables.getString(key));
            Variables.putVariable(key, resource.path());
        }
    }
}
