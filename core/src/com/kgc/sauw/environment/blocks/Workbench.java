package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.map.Tile;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Workbench extends Block {
    public Workbench() {
        super(ID.registeredId("block:workbench"), TEXTURES.table);

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(1, 2, 30, 7, 32);
    }

    /*
     * Доделать верстак (Это послание из прошлого чтобы ты вспонил что хотел сделать)
     * */
    @Override
    public void tick(Tile tile) {

    }
}
