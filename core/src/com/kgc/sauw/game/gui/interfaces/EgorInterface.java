package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.utils.Resource;

public class EgorInterface extends Interface {
    public EgorInterface() {
        super("EGOR_INTERFACE");
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/EgorInterface.xml"), this);

        ((Image) getElement("mem")).setImg(Resource.getTexture("egor_memes/mem1.jpg"));
    }
}
