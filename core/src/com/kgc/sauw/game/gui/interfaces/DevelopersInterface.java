package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.gui.elements.MultilineTextView;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.Application;

@RegistryMetadata("sauw:developers_interface")
public class DevelopersInterface extends Interface {
    public DevelopersInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/DevelopersInterface.xml"), this);

        ((Image) getElement("image.jvmfrog")).setImg(Resource.getTexture("jvmfrog.png"));

        Button jvmfrogLink = (Button) getElement("button.jvmfrog_link");
        Button kirboGamesLink = (Button) getElement("button.kirbogames_link");
        MultilineTextView devs = (MultilineTextView) getElement("text.devs");

        devs.setText("[LINK]@KirboGames : \n\t\t- Programming, music, textures\n[LINK]@IbremMiner837 :\n\t\t- Textures");

        jvmfrogLink.setDefaultText();
        kirboGamesLink.setDefaultText();

        jvmfrogLink.addEventListener(() -> {
            Application.openURI("https://github.com/JVMFrog");
        });
        kirboGamesLink.addEventListener(() -> {
            Application.openURI("https://github.com/KirboGames");
        });
    }
}
