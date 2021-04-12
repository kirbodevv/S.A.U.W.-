package com.kgc.sauw.game;

import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.Modding.ModAPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.Modding.Mods;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.map.World;
import com.kgc.sauw.resource.Music;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.UI.Interfaces.Interfaces.isAnyInterfaceOpen;
import static com.kgc.sauw.environment.Environment.*;
import static com.kgc.sauw.graphic.Graphic.*;

public class SAUW implements Screen {

    com.kgc.sauw.map.World World;
    private float WIDTH;
    private float HEIGHT;

    private int camX, camY;
    Mods mods;
    public ModAPI ModAPI;
    MainGame game;
    Music music;

    Box2DDebugRenderer DR;

    public SAUW(MainGame game, Music music, String worldName) {
        this.game = game;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        World = new World();
        DR = new Box2DDebugRenderer();
        this.music = music;
        music.w = World;
        music.setMusicVolume(SETTINGS.musicVolume);
        BLOCKS.initialize(GAME_INTERFACE, World);
        ModAPI = new ModAPI(GAME_INTERFACE);
        mods = new Mods();
        GAME_INTERFACE.initialize(ModAPI, game, World);
        if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
            World.createNewWorld();
            World.save(worldName);
        } else {
            World.load(worldName);
        }
        mods.load(World.pl, BLOCKS, ITEMS, ModAPI, CRAFTING, SETTINGS, GAME_INTERFACE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GAME_CAMERA.update(BATCH);

        BLOCKS.interfacesUpdate(World.getMaps(), World.pl, INTERFACE_CAMERA);
        GAME_INTERFACE.update(World.pl);
        music.update(false);
        BATCH.begin();
        if (isAnyInterfaceOpen()) BATCH.setColor(0.5f, 0.5f, 0.5f, 1);
        World.renderLowLayer();
        World.renderHighLayer();
        World.renderEntitys();
        World.renderLights();
        if (isAnyInterfaceOpen()) BATCH.setColor(1, 1, 1, 1);
        BATCH.end();
        if (SETTINGS.debugRenderer) DR.render(World.world, GAME_CAMERA.CAMERA.combined);
        BATCH.begin();
        GAME_INTERFACE.render(World, World.pl, SETTINGS.debugMode);
        BLOCKS.interfacesRender(World.getMaps(), World.pl, INTERFACE_CAMERA);
        GAME_INTERFACE.update(World.pl);
        World.update(mods, ACHIEVEMENTS);


        camX = (int) ((World.pl.posX + (World.pl.plW / 2)) - (GAME_CAMERA.W / 2));
        camY = (int) (World.pl.posY + (World.pl.plH / 2) - (GAME_CAMERA.H / 2));
        if (camX < WIDTH / 16) camX = (int) WIDTH / 16;
        if (camY < WIDTH / 16) camY = (int) WIDTH / 16;
        if (camX + GAME_CAMERA.W > (World.getMaps().map0[0].length - 1) * (WIDTH / 16))
            camX = (int) ((World.getMaps().map0[0].length - 1) * (WIDTH / 16) - GAME_CAMERA.W);
        if (camY + GAME_CAMERA.H > (World.getMaps().map0.length - 1) * (WIDTH / 16))
            camY = (int) ((World.getMaps().map0.length - 1) * (WIDTH / 16) - GAME_CAMERA.H);

        GAME_CAMERA.lookAt(camX, camY, true);
        BATCH.end();
    }

    @Override
    public void dispose() {
        BATCH.dispose();
        TEXTURES.dispose();
        music.dispose();
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
