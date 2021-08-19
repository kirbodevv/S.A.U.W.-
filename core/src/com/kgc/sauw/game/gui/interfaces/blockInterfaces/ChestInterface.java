package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.world.Tile;

public class ChestInterface extends BlockInterface {

    public ChestInterface() {
        super("CHEST_INTERFACE");

        createFromXml(Gdx.files.internal("xml/ChestInterface.xml"));

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