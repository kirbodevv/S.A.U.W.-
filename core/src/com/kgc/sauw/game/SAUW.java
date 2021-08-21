package com.kgc.sauw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.environment.Environment;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.elements.Elements;
import com.kgc.sauw.core.item.Items;
import com.kgc.sauw.core.particle.Particles;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.render.WorldRenderer;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.sound.Music;
import com.kgc.sauw.core.utils.GameCameraController;
import com.kgc.sauw.game.environment.Blockss;
import com.kgc.sauw.game.environment.Itemss;
import com.kgc.sauw.game.items.Torch;
import com.kgc.sauw.game.worlds.MysticalVoidWorld;
import com.kgc.sauw.mods.Mods;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.environment.Environment.setWorld;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.GAME_CAMERA;
import static com.kgc.sauw.game.gui.Interfaces.HUD;
import static com.kgc.sauw.game.gui.Interfaces.isAnyInterfaceOpen;

public class SAUW implements Screen {
    public static boolean isGameRunning;
    public static final Mods MODS;

    static {
        MODS = new Mods();
        isGameRunning = false;
    }

    Box2DDebugRenderer DR;

    public SAUW(String worldName) {
        DR = new Box2DDebugRenderer();

        GeneratedJson.init();
        Items.addItem(new Torch());
        new Blockss();

        Music.setVolume(Settings.musicVolume);

        setWorld(new MysticalVoidWorld());

        Environment.setSaveName(worldName);

        if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
            getWorld().createNewWorld();
            PLAYER.randomSpawn();
            Environment.save();
        } else {
            Environment.load();
        }
        MODS.load();

        new UpdateTick().start();
        isGameRunning = true;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Physic.update();
        Animator.update();

        Music.setVolume(Settings.musicVolume);

        Blocks.animationTick();
        HUD.update();

        GameCameraController.update();

        BATCH.begin();
        if (isAnyInterfaceOpen()) BATCH.setColor(0.5f, 0.5f, 0.5f, 1);
        WorldRenderer.render(getWorld());
        Particles.render();
        if (isAnyInterfaceOpen()) BATCH.setColor(1, 1, 1, 1);
        BATCH.end();
        if (Settings.debugRenderer) DR.render(Physic.getWorld(), GAME_CAMERA.CAMERA.combined);
        BATCH.begin();
        HUD.render(Settings.debugMode);
        getWorld().update();
        AchievementsChecker.update();

        BATCH.end();
    }

    @Override
    public void dispose() {
        BATCH.dispose();
        Resource.dispose();
        Mods.disposeResources();
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
            getWorld().map.update();
            Items.tick();
            try {
                sleep(50);
            } catch (Exception ignored) {
            }
            new UpdateTick().start();
        }
    }
}