package com.kgc.sauw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.core.achievements.Achievements;
import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.entity.EntityManager;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.input.Input;
import com.kgc.sauw.core.item.Items;
import com.kgc.sauw.core.particle.Particles;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.sound.Music;
import com.kgc.sauw.core.utils.GameCameraController;
import com.kgc.sauw.core.world.WorldRenderer;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.GAME_CAMERA;
import static com.kgc.sauw.game.gui.Interfaces.HUD;
import static com.kgc.sauw.game.gui.Interfaces.isAnyInterfaceOpen;

public class SAUW implements Screen {
    private final Box2DDebugRenderer DR;

    public SAUW() {
        DR = new Box2DDebugRenderer();

        Music.setVolume(Settings.musicVolume);
        Particles.init();

        new UpdateTick().start();
        Input.init();

        EntityManager.spawn("entity:egor", 10, 10);
        PLAYER.inventory.addItem("sauw", "item:handsaw", 1);
        PLAYER.inventory.addItem("sauw", "item:hammer", 1);
        PLAYER.inventory.addItem("sauw", "item:iron_ingot", 1);
        PLAYER.inventory.addItem("sauw", "item:log", 1);
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

        BATCH.end();
    }

    @Override
    public void dispose() {
        DR.dispose();
        throw new RuntimeException("It works, but you shouldn't use it :moyai:");
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
            Achievements.checkAchievements(PLAYER.achievementsData);
            getWorld().getTime().updateTime();
            try {
                sleep(50);
            } catch (Exception ignored) {
            }
            if (Game.isGameRunning)
                new UpdateTick().start();
        }
    }
}