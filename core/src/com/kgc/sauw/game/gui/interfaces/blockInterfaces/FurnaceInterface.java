package com.kgc.sauw.game.gui.interfaces.blockInterfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.gui.elements.Slot;
import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.Tile;

@RegistryMetadata("sauw:furnace")
public class FurnaceInterface extends BlockInterface {


    public FurnaceInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/FurnaceInterface.xml"), this);

        Slot resultSlot = (Slot) getElement("slot.ResultSlot");
        resultSlot.setSF(new Slot.SlotFunctions() {
            @Override
            public boolean isValid(Container container, String fromSlotWithId) {
                return false;
            }

            @Override
            public void onClick() {

            }

            @Override
            public boolean possibleToDrag() {
                return true;
            }

            @Override
            public void onItemSwapping(Container fromContainer) {

            }
        });

        ((Image) getElement("image.ArrowIcon")).setImg(Resource.getTexture("interface/button_right_0.png"));

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