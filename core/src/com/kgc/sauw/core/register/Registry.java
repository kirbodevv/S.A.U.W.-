package com.kgc.sauw.core.register;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.GameContext;

import java.util.ArrayList;

public abstract class Registry<I extends RegistryObject> {
    protected ArrayList<I> objects = new ArrayList<>();

    public abstract String getIDGroup();

    public void register(I object, String package_, String id) {
        object.setId(GameContext.get(package_).registerId(getIDGroup() + ":" + id));
        object.setStringId(id);
        object.setPackage_(package_);
        object.init();
        objects.add(object);
        Gdx.app.log("Registry", "registered " + getIDGroup() + ", with id \"" + object.getStringId() + "\", integer id " + object.id);
    }

    public I get(int id) {
        return objects.get(id);
    }

    public ArrayList<I> getAllObjectsFromPackage(String package_) {
        ArrayList<I> arrayList = new ArrayList<>();
        for (I object : objects) {
            if (object.package_.equals(package_)) arrayList.add(object);
        }
        return arrayList;
    }
}
