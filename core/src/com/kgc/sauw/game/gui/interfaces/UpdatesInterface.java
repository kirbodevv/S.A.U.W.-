package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.UpdatesChecker;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.gui.elements.TextView;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;

@RegistryMetadata(package_ = "sauw", id = "updates")
public class UpdatesInterface extends Interface {
    public UpdatesInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/UpdatesInterface.xml"), this);

        ((Button) getElement("button.download_update")).setDefaultText();
        ((Button) getElement("button.later")).setDefaultText();
        ((Button) getElement("button.full_changelog")).setDefaultText();

        getElement("button.download_update").addEventListener(() -> System.out.println("Это не работает"));
        getElement("button.later").addEventListener(this::close);
        getElement("button.full_changelog").addEventListener(() -> {
            try {
                Gdx.net.openURI(UpdatesChecker.getFullChangelogLink());
            } catch (Exception ignored) {
            }
        });
        setScreenshot(Resource.getTexture("test_screenshot.png"));
    }

    public void setScreenshot(Texture screenshot) {
        ((Image) getElement("image.screenshot")).setImg(screenshot);
    }

    public void setVersion(String version) {
        ((TextView) getElement("text.version")).setText(version);
    }

    public void setChangelog(String[] changes) {
        ((TextView) getElement("text.changelog")).setText(changes[0]);
        ((TextView) getElement("text.changelog1")).setText(changes[1]);
        ((TextView) getElement("text.changelog2")).setText(changes[2]);
        ((TextView) getElement("text.changelog3")).setText(changes[3]);
        ((TextView) getElement("text.changelog4")).setText(changes[4]);
    }
}
