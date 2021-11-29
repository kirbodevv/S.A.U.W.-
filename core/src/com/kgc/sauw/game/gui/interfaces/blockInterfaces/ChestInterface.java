package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.TextView;

public class ChestInterface extends BlockInterface {

    public ChestInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/ChestInterface.xml"), this);

        ((TextView) getElement("text.chest")).setDefaultText();

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