package com.kgc.sauw.game;

import com.kgc.sauw.Modding.ModAPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.Modding.Mods;
import com.kgc.sauw.UI.Elements.Elements;
import com.kgc.sauw.resource.Music;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.UI.Interfaces.Interfaces.isAnyInterfaceOpen;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.*;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.WORLD;

public class SAUW implements Screen {
    private float WIDTH;
    private float HEIGHT;

    private int camX, camY;
    Mods mods;
    public static ModAPI MOD_API;
    MainGame game;
    Music music;

    Box2DDebugRenderer DR;

    public SAUW(MainGame game, Music music, String worldName) {
        this.game = game;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        DR = new Box2DDebugRenderer();
        this.music = music;
        music.setMusicVolume(SETTINGS.musicVolume);
        BLOCKS.initialize();
        MOD_API = new ModAPI();
        mods = new Mods();
        if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
            WORLD.createNewWorld();
            WORLD.save(worldName);
        } else {
            WORLD.load(worldName);
        }
        mods.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GAME_CAMERA.update(BATCH);

        BLOCKS.interfacesUpdate(WORLD.getMaps(), PLAYER, INTERFACE_CAMERA);
        GAME_INTERFACE.update(PLAYER);
        music.update(false);
        BATCH.begin();
        if (isAnyInterfaceOpen()) BATCH.setColor(0.5f, 0.5f, 0.5f, 1);
        WORLD.renderLowLayer();
        WORLD.renderHighLayer();
        WORLD.renderEntitys();
        WORLD.renderLights();
        if (isAnyInterfaceOpen()) BATCH.setColor(1, 1, 1, 1);
        BATCH.end();
        if (SETTINGS.debugRenderer) DR.render(WORLD.world, GAME_CAMERA.CAMERA.combined);
        BATCH.begin();
        GAME_INTERFACE.render(WORLD, PLAYER, SETTINGS.debugMode);
        BLOCKS.interfacesRender(WORLD.getMaps(), PLAYER, INTERFACE_CAMERA);
        GAME_INTERFACE.update(PLAYER);
        WORLD.update(mods, ACHIEVEMENTS);


        camX = (int) ((PLAYER.posX + (PLAYER.plW / 2)) - (GAME_CAMERA.W / 2));
        camY = (int) (PLAYER.posY + (PLAYER.plH / 2) - (GAME_CAMERA.H / 2));
        if (camX < WIDTH / 16) camX = (int) WIDTH / 16;
        if (camY < WIDTH / 16) camY = (int) WIDTH / 16;
        if (camX + GAME_CAMERA.W > (WORLD.getMaps().map0[0].length - 1) * (WIDTH / 16))
            camX = (int) ((WORLD.getMaps().map0[0].length - 1) * (WIDTH / 16) - GAME_CAMERA.W);
        if (camY + GAME_CAMERA.H > (WORLD.getMaps().map0.length - 1) * (WIDTH / 16))
            camY = (int) ((WORLD.getMaps().map0.length - 1) * (WIDTH / 16) - GAME_CAMERA.H);

        GAME_CAMERA.lookAt(camX, camY, true);
        BATCH.end();
    }

    @Override
    public void dispose() {
        BATCH.dispose();
        TEXTURES.dispose();
        music.dispose();
        Elements.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
