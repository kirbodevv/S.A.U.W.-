package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.gui.Interface;
import com.kgc.sauw.gui.elements.Slot;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class ToolWallInterface extends Interface {

    public ToolWallInterface() {
        super("TOOL_WALL_INTERFACE");
        createFromXml(Gdx.files.internal("xml/ToolWallInstruments.xml"));

        ((Slot) getElement("HammerSlot")).setIcon(TEXTURES.hammer);
        ((Slot) getElement("HandsawSlot")).setIcon(TEXTURES.handsaw);

        updateElementsList();
    }
}
