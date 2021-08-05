package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.EditText;
import com.kgc.sauw.core.utils.languages.Languages;

public class CreateNewWorldInterface extends Interface {
    public EditText worldName;
    public Button create;

    public CreateNewWorldInterface() {
        super("CREATE_NEW_WORLD_INTERFACE");
        createFromXml(Gdx.files.internal("xml/CreateWorldInterface.xml"));

        worldName = (EditText) getElement("WorldNameText");
        create = (Button) getElement("CREATE_BUTTON");

        updateElementsList();
    }

    @Override
    public void onOpen() {
        super.onOpen();
        worldName.input = Languages.getString("newWorld");
    }
}
