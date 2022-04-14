package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.EditText;
import com.kgc.sauw.core.gui.elements.TextView;
import com.jvmfrog.curve.registry.RegistryMetadata;

import static com.kgc.sauw.game.gui.GameInterfaces.SELECT_WORLD_INTERFACE;

@RegistryMetadata("sauw:create_new_world")
public class CreateNewWorldInterface extends Interface {
    public EditText worldName;
    public Button create;

    public CreateNewWorldInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/CreateWorldInterface.xml"), this);

        worldName = (EditText) getElement("edittext.world_name_text");
        create = (Button) getElement("button.create");
        create.setDefaultText();
        ((TextView) getElement("text.world_name")).setDefaultText();

        updateElementsList();
    }

    @Override
    public void tick() {

    }

    @Override
    public void onOpen() {
        worldName.setDefaultText();
    }

    @Override
    public void onClose() {
        SELECT_WORLD_INTERFACE.open();
    }

    @Override
    public void preRender() {

    }

    @Override
    public void postRender() {

    }
}
