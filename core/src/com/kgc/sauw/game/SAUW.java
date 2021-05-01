package com.kgc.sauw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.AchievementsChecker;
import com.kgc.sauw.WorldLoader;
import com.kgc.sauw.graphic.Animator;
import com.kgc.sauw.modding.ModAPI;
import com.kgc.sauw.modding.Mods;
import com.kgc.sauw.physic.Physic;
import com.kgc.sauw.ui.elements.Elements;
import com.kgc.sauw.resource.Music;

import static com.kgc.sauw.resource.Files.loadPlayerData;
import static com.kgc.sauw.ui.interfaces.Interfaces.*;
import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.WORLD;

public class SAUW implements Screen {
    private int camX, camY;

    public static boolean isGameRunning;
    public static final Mods MODS;
    public static final ModAPI MOD_API;

    static {
        MOD_API = new ModAPI();
        MODS = new Mods();
        isGameRunning = false;
    }

    private MainGame game;
    Music music;

    Box2DDebugRenderer DR;

    public SAUW(MainGame game, Music music, String worldName) {
        this.game = game;
        this.music = music;

        DR = new Box2DDebugRenderer();

        music.setMusicVolume(SETTINGS.musicVolume);

        loadPlayerData();

        if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
            WORLD.createNewWorld();
            WorldLoader.save(worldName);
        } else {
            WorldLoader.load(worldName);
        }
        MODS.load();
        isGameRunning = true;
        setCameraPosition();

        GAME_CAMERA.lookAt(camX, camY, false);
    }

    public void setCameraPosition() {
        camX = (int) ((PLAYER.getPosition().x + (PLAYER.getSize().x / 2)) - (GAME_CAMERA.W / 2));
        camY = (int) (PLAYER.getPosition().y + (PLAYER.getSize().y  / 2) - (GAME_CAMERA.H / 2));
        if (camX < BLOCK_SIZE) camX = BLOCK_SIZE;
        if (camY < BLOCK_SIZE) camY = BLOCK_SIZE;
        if (camX + GAME_CAMERA.W > (WORLD.getMaps().map0[0].length - 1) * (SCREEN_WIDTH / 16))
            camX = (int) ((WORLD.getMaps().map0[0].length - 1) * (SCREEN_WIDTH / 16) - GAME_CAMERA.W);
        if (camY + GAME_CAMERA.H > (WORLD.getMaps().map0.length - 1) * (SCREEN_WIDTH / 16))
            camY = (int) ((WORLD.getMaps().map0.length - 1) * (SCREEN_WIDTH / 16) - GAME_CAMERA.H);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Physic.update();
        Animator.update();

        music.setMusicVolume(SETTINGS.musicVolume);
        GAME_CAMERA.update(BATCH);

        BLOCKS.animationTick();
        GAME_INTERFACE.update(PLAYER);
        music.setMusicVolume(SETTINGS.musicVolume);
        music.update(false);
        BATCH.begin();
        if (isAnyInterfaceOpen()) BATCH.setColor(0.5f, 0.5f, 0.5f, 1);
        WORLD.renderLowLayer();
        WORLD.renderHighLayer();
        WORLD.renderEntities();
        WORLD.renderLights();
        if (isAnyInterfaceOpen()) BATCH.setColor(1, 1, 1, 1);
        BATCH.end();
        if (SETTINGS.debugRenderer) DR.render(Physic.getWorld(), GAME_CAMERA.CAMERA.combined);
        BATCH.begin();
        GAME_INTERFACE.render(WORLD, PLAYER, SETTINGS.debugMode);
        WORLD.update(MODS);
        //AchievementsChecker.update();

        setCameraPosition();
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