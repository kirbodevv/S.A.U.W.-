package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.gui.Interface;

public class ToolWallInterface extends Interface {

    public ToolWallInterface() {
        super("TOOL_WALL_INTERFACE");
        createFromXml(Gdx.files.internal("xml/ToolWallInstruments.xml").readString());

        updateElementsList();
    }
}
