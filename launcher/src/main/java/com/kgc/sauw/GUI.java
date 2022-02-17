package com.kgc.sauw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kgc.sauw.version.Version;
import com.kgc.utils.UpdatesChecker;

public class GUI implements ApplicationListener {
    private static final int width = 500, height = 300;

    private Stage stage;
    private Skin skin;
    private Texture background;
    private TextButton launch;
    private ProgressBar progressBar;
    private SelectBox<String> versionSelectBox;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("flat/skin/skin.json"));

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        UpdatesChecker.check(() -> {
            for (String key : UpdatesChecker.getVersions().keySet()) {
                if (!key.equals("last"))
                    new Version(UpdatesChecker.getVersions().get(key), Integer.parseInt(key));
            }
        }, "ru");
        launch = new TextButton("Start", skin);
        versionSelectBox = new SelectBox<>(skin);
        background = new Texture(Gdx.files.internal("background.png"));
        Image background = new Image(this.background);
        progressBar = new ProgressBar(0, 100, 1, false, skin);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        table.add(versionSelectBox).width(150).height(30).padRight(10);
        table.add(launch).width(320).height(30);
        table.row();
        table.add(progressBar).width(470).height(30).colspan(2).padTop(5).padBottom(5);

        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        background.dispose();
        UpdatesChecker.disposeScreenshot();
    }
}
