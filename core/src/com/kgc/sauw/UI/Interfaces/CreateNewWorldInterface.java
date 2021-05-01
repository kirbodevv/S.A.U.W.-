package com.kgc.sauw.ui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.ui.elements.Button;
import com.kgc.sauw.ui.elements.EditText;
import com.kgc.sauw.ui.Interface;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class CreateNewWorldInterface extends Interface {
    public String[] worldNames;
    public EditText worldName;
    public Button create;
    BitmapFont bf = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    public CreateNewWorldInterface() {
        super("CREATE_NEW_WORLD_INTERFACE");
        createFromXml(Gdx.files.internal("xml/CreateWorldInterface.xml").readString());

        FileHandle worldsFolder = Gdx.files.external("S.A.U.W./Worlds/");
        FileHandle[] files = worldsFolder.list();
        int i = 0;
        for (FileHandle file : files) {
            if (file.isDirectory()) i++;
        }

        worldNames = new String[i];
        int ii = 0;
        for (FileHandle file : files) {
            if (file.isDirectory()) worldNames[ii] = file.name();
            ii++;
        }

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
