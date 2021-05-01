package com.kgc.sauw.ui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.ui.Interface;

public class ChestInterface extends Interface {

    public ChestInterface() {
        super("CHEST_INTERFACE");

        createFromXml(Gdx.files.internal("xml/ChestInterface.xml").readString());

        updateElementsList();
    }
}