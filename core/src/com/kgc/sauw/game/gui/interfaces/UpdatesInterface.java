package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.UpdatesChecker;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Files;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.FileDownloader;
import com.kgc.sauw.core.utils.languages.Languages;

@RegistryMetadata(package_ = "sauw", id = "updates")
public class UpdatesInterface extends Interface {
    private final ProgressBar progressBar;
    private boolean isDownloading = false;

    public UpdatesInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/UpdatesInterface.xml"), this);
        Button downloadUpdateButton = (Button) getElement("button.download_update");
        downloadUpdateButton.setDefaultText();
        ((Button) getElement("button.later")).setDefaultText();
        ((Button) getElement("button.full_changelog")).setDefaultText();

        progressBar = new ProgressBar(true);
        progressBar.setSizeInBlocks(15f, 0.5f);
        progressBar.setTranslationY(-0.125f);
        progressBar.setMaxValue(100);

        mainLayout.addElements(progressBar);

        downloadUpdateButton.addEventListener(() -> {
            switch (Gdx.app.getType()) {
                case Desktop:
                    downloadUpdateButton.setText(Languages.getString("sauw.interface.update_interface.button.download_update.via_launcher"));

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            downloadUpdateButton.setDefaultText();
                        }
                    }.start();
                case Android:
                    if (!downloadUpdateButton.isLocked()) {
                        downloadUpdateButton.lock(true);
                        isDownloading = true;
                        FileDownloader.download(UpdatesChecker.getAndroidLink(),
                                Files.tempDir.child(UpdatesChecker.getLastVersionName() + ".apk"),
                                new FileDownloader.ProgressListener() {
                                    @Override
                                    public void update(int progress) {
                                        progressBar.setValue(progress);
                                    }

                                    @Override
                                    public void done() {
                                        downloadUpdateButton.lock(false);
                                        isDownloading = false;
                                    }
                                });
                    }
            }
        });
        getElement("button.later").addEventListener(this::close);
        getElement("button.full_changelog").addEventListener(() -> {
            try {
                Gdx.net.openURI(UpdatesChecker.getFullChangelogLink());
            } catch (Exception ignored) {
            }
        });
        setScreenshot(Resource.getTexture("SAUW_icon.png"));
    }

    public void setScreenshot(Texture screenshot) {
        ((Image) getElement("image.screenshot")).setImg(screenshot);
    }

    public void setVersion(String version) {
        ((TextView) getElement("text.version")).setText(version);
    }

    public void setChangelog(String changes) {
        ((MultilineTextView) getElement("text.changelog")).setText(changes);
    }

    @Override
    public void tick() {
        progressBar.hide(!isDownloading);
    }
}
