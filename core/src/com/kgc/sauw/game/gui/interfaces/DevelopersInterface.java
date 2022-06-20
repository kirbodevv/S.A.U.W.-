package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

        String[] contributorsList = Gdx.files.internal("contributors/contributors.list")
                .readString().split("\\r?\\n");

        Contributor[] contributors = new Contributor[contributorsList.length];

        for (int i = 0; i < contributors.length; i++) {
            contributors[i] = new Contributor(contributorsList[i]);
        }
        contributors = sort(contributors);

        StringBuilder devsText = new StringBuilder();

        for (Contributor c : contributors) {
            devsText.append("[LINK]@").append(c.name)
                    .append("[BLACK], contributions: ").append(c.contributions).append("\n");
        }

        devs.setText(devsText.toString());

        jvmfrogLink.setDefaultText();
        kirboGamesLink.setDefaultText();

        jvmfrogLink.addEventListener(() -> {
            Application.openURI("https://github.com/JVMFrog");
        });
        kirboGamesLink.addEventListener(() -> {
            Application.openURI("https://github.com/KirboGames");
        });
    }

    private static Contributor[] sort(Contributor[] contributors) {
        for (int i = 0; i < contributors.length; i++) {
            int max = contributors[i].contributions;
            int max_i = i;

            for (int j = i + 1; j < contributors.length; j++) {
                if (contributors[j].contributions > max) {
                    max = contributors[j].contributions;
                    max_i = j;
                }
            }

            if (i != max_i) {
                Contributor tmp = contributors[i];
                contributors[i] = contributors[max_i];
                contributors[max_i] = tmp;
            }
        }
        return contributors;
    }

    private static class Contributor {
        public Texture avatar;
        public String name;
        public int contributions;

        public Contributor(String data) {
            String[] keys = data.split(":");

            name = keys[0];
            contributions = Integer.parseInt(keys[1]);
            avatar = Resource.getTexture("contributors/" + name + ".jpg");

        }
    }
}
