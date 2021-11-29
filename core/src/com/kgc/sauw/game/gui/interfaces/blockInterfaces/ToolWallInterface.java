package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.world.Tile;

public class ToolWallInterface extends BlockInterface {

    public ToolWallInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/ToolWallInstruments.xml"), this);

        ((Slot) getElement("slot.HammerSlot")).setIcon(Resource.getTexture("Items/hammer.png"));
        ((Slot) getElement("slot.HandsawSlot")).setIcon(Resource.getTexture("Items/handsaw.png"));

        updateElementsList();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void tick(Tile tile) {

    }

    @Override
    public void onOpen(Tile tile) {

    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void preRender() {

    }

    @Override
    public void postRender() {

    }
}
