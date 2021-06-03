package com.kgc.sauw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.AchievementsChecker;
import com.kgc.sauw.core.WorldLoader;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.elements.Elements;
import com.kgc.sauw.core.map.World;
import com.kgc.sauw.mods.ModAPI;
import com.kgc.sauw.mods.Mods;
import com.kgc.sauw.core.particle.Particles;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.resource.Music;
import com.kgc.sauw.core.utils.GameCameraController;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.BLOCKS;
import static com.kgc.sauw.core.graphic.Graphic.*;
import static com.kgc.sauw.gui.Interfaces.HUD;
import static com.kgc.sauw.gui.Interfaces.isAnyInterfaceOpen;
import static com.kgc.sauw.core.map.World.WORLD;
import static com.kgc.sauw.resource.Files.loadPlayerData;

public class SAUW implements Screen {
    public static boolean isGameRunning;
    public static final Mods MODS;
    public static final ModAPI MOD_API;

    static {
        MOD_API = new ModAPI();
        MODS = new Mods();
        isGameRunning = false;
    }

    Music music;

    Box2DDebugRenderer DR;

    public SAUW(String worldName) {
        this.music = Music.getMusic();
        DR = new Box2DDebugRenderer();

        music.setMusicVolume(SETTINGS.musicVolume);

        loadPlayerData();

        if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
            WORLD.createNewWorld();
            PLAYER.randomSpawn();
            WorldLoader.save(worldName);
        } else {
            WorldLoader.load(worldName);
        }
        MODS.load();
        new UpdateTick().start();

        isGameRunning = true;

        WORLD.setBlock(5, 5, 0, ID.get("block:water"));
        WORLD.setBlock(5, 6, 0, ID.get("block:water"));
        WORLD.setBlock(6, 5, 0, ID.get("block:water"));

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Physic.update();
        Animator.update();

        music.setMusicVolume(SETTINGS.musicVolume);
        music.update(false);

        BLOCKS.animationTick();
        HUD.update();

        GameCameraController.update();

        BATCH.begin();
        if (isAnyInterfaceOpen()) BATCH.setColor(0.5f, 0.5f, 0.5f, 1);
        WORLD.renderLowLayer();
        WORLD.renderHighLayer();
        WORLD.renderEntities();
        WORLD.renderLights();
        Particles.render();
        if (isAnyInterfaceOpen()) BATCH.setColor(1, 1, 1, 1);
        BATCH.end();
        if (SETTINGS.debugRenderer) DR.render(Physic.getWorld(), GAME_CAMERA.CAMERA.combined);
        BATCH.begin();
        HUD.render(SETTINGS.debugMode);
        WORLD.update();
        AchievementsChecker.update();

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
        Graphic.resize(width, height);
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

    public static class UpdateTick extends Thread {
        @Override
        public void run() {
            super.run();
            World.MAPS.update();
            try {
                sleep(50);
            } catch (Exception e) {
            }
            new UpdateTick().start();
        }
    }
}