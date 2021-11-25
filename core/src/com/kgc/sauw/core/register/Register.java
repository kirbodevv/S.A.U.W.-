package com.kgc.sauw.core.register;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.GameContext;

import java.util.ArrayList;

public abstract class Register<I extends RegistryObject> {
    protected ArrayList<I> objects = new ArrayList<>();

    public abstract String getIDGroup();

    public void register(I object, String package_, String id) {
        object.setId(GameContext.get(package_).registerId(getIDGroup() + ":" + id));
        object.setStringId(id);
        object.setPackage_(package_);
        objects.add(object);
        Gdx.app.log("Register", "registered " + getIDGroup() + ", with id \"" + object.getStringId() + "\", integer id " + object.id);
    }

    public I get(int id) {
        return objects.get(id);
    }
}
