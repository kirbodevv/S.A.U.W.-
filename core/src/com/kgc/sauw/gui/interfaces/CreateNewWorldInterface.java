package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.gui.elements.Button;
import com.kgc.sauw.gui.elements.EditText;
import com.kgc.sauw.gui.Interface;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

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
        worldName.input = LANGUAGES.getString("newWorld");
    }
}
