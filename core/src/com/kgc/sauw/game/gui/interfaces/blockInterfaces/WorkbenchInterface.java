package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.environment.world.Tile;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.game.blocks.Workbench;

public class WorkbenchInterface extends BlockInterface {
    public final Image craftHandsawNotAvailableImage;
    public final Image craftHammerNotAvailableImage;

    public WorkbenchInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/WorkbenchInterface.xml"), this);

        ((Image) getElement("image.craft_handsaw_arrow")).setImg(Resource.getTexture("Interface/button_right_0.png"));
        ((Image) getElement("image.craft_hammer_arrow")).setImg(Resource.getTexture("Interface/button_right_0.png"));
        ((Image) getElement("image.handsaw")).setImg(Resource.getTexture("Items/handsaw.png"));
        ((Image) getElement("image.hammer")).setImg(Resource.getTexture("Items/hammer.png"));

        craftHandsawNotAvailableImage = (Image) getElement("image.craft_handsaw_not_available_icon");
        craftHammerNotAvailableImage = (Image) getElement("image.craft_hammer_not_available_icon");

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