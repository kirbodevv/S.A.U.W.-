package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.game.blocks.Workbench;

public class WorkbenchInterface extends BlockInterface {
    public final Image craftHandsawNotAvailableImage;
    public final Image craftHammerNotAvailableImage;

    public WorkbenchInterface() {
        super("WORKBENCH_TABLE_INTERFACE");

        createFromXml(Gdx.files.internal("xml/WorkbenchInterface.xml"));

        ((Image) getElement("CRAFT_HANDSAW_ARROW")).setImg(Resource.getTexture("Interface/button_right_0.png"));
        ((Image) getElement("CRAFT_HAMMER_ARROW")).setImg(Resource.getTexture("Interface/button_right_0.png"));
        ((Image) getElement("HANDSAW")).setImg(Resource.getTexture("Items/handsaw.png"));
        ((Image) getElement("HAMMER")).setImg(Resource.getTexture("Items/hammer.png"));

        craftHandsawNotAvailableImage = (Image) getElement("CRAFT_HANDSAW_NOT_AVAILABLE_ICON");
        craftHammerNotAvailableImage = (Image) getElement("CRAFT_HAMMER_NOT_AVAILABLE_ICON");

        craftHandsawNotAvailableImage.setImg(Resource.getTexture("Interface/closeButton.png"));
        craftHammerNotAvailableImage.setImg(Resource.getTexture("Interface/closeButton.png"));
    }

    @Override
    public void tick(Tile tile) {
        craftHandsawNotAvailableImage.hide(Workbench.findToolWall(tile).getContainer("HandsawSlot").id == com.kgc.sauw.core.utils.ID.get("item:handsaw"));
        craftHammerNotAvailableImage.hide(Workbench.findToolWall(tile).getContainer("HammerSlot").id == com.kgc.sauw.core.utils.ID.get("item:hammer"));
    }

    @Override
    public void onOpen(Tile tile) {

    }

    @Override
    public void tick() {
        super.tick();
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