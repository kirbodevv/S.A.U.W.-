package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.environment.Environment;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.game.Game;

@RegistryMetadata(package_ = "sauw", id = "pause")
public class PauseInterface extends Interface {
    Button saveWorldButton;
    Button resumeButton;
    Button settingsButton;
    Button exitButton;

    public PauseInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/PauseInterface.xml"), this);
        resumeButton = (Button) getElement("button.resume");
        saveWorldButton = (Button) getElement("button.save_world");
        settingsButton = (Button) getElement("button.settings");
        exitButton = (Button) getElement("button.exit");

        resumeButton.setDefaultText();
        saveWorldButton.setDefaultText();
        settingsButton.setDefaultText();
        exitButton.setDefaultText();

        resumeButton.addEventListener(this::close);
        saveWorldButton.addEventListener(Environment::save);
        exitButton.addEventListener(Game::closeGame);

        closeButtonHidden(true);
        updateElementsList();
    }

    @Override
    public void tick() {

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
