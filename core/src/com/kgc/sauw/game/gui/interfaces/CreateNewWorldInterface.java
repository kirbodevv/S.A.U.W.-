package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.EditText;
import com.kgc.sauw.core.utils.languages.Languages;

public class CreateNewWorldInterface extends Interface {
    public EditText worldName;
    public Button create;

    public CreateNewWorldInterface() {
        super("CREATE_NEW_WORLD_INTERFACE");
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/CreateWorldInterface.xml"), this);

        worldName = (EditText) getElement("WorldNameText");
        create = (Button) getElement("CREATE_BUTTON");

        updateElementsList();
    }

    @Override
    public void tick() {

    }

    @Override
    public void onOpen() {
        worldName.input = Languages.getString("newWorld");
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
