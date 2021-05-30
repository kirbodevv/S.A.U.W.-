package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.gui.Interface;
import com.kgc.sauw.gui.elements.Image;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class WorkbenchInterface extends Interface {
    public WorkbenchInterface() {
        super("WORKBENCH_TABLE_INTERFACE");

        createFromXml(Gdx.files.internal("xml/WorkbenchInterface.xml"));

        ((Image) getElement("CRAFT_HANDSAW_ARROW")).setImg(TEXTURES.icon_right);
        ((Image) getElement("CRAFT_HAMMER_ARROW")).setImg(TEXTURES.icon_right);
        ((Image) getElement("HANDSAW")).setImg(TEXTURES.handsaw);
        ((Image) getElement("HAMMER")).setImg(TEXTURES.hammer);
    }
}