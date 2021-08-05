package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.resource.Resource;

public class ToolWallInterface extends Interface {

    public ToolWallInterface() {
        super("TOOL_WALL_INTERFACE");
        createFromXml(Gdx.files.internal("xml/ToolWallInstruments.xml"));

        ((Slot) getElement("HammerSlot")).setIcon(Resource.getTexture("Items/hammer.png"));
        ((Slot) getElement("HandsawSlot")).setIcon(Resource.getTexture("Items/handsaw.png"));

        updateElementsList();
    }
}
