package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;

public class ChestInterface extends Interface {

    public ChestInterface() {
        super("CHEST_INTERFACE");

        createFromXml(Gdx.files.internal("xml/ChestInterface.xml"));

        updateElementsList();
    }
}