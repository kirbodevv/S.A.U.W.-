package com.kgc.sauw.game;

import com.kgc.sauw.Achievements;
import com.kgc.sauw.Modding.ModAPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.Modding.Mods;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.config.Settings;
import com.kgc.sauw.environment.Blocks;
import com.kgc.sauw.environment.Crafting;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.map.World;
import com.kgc.sauw.resource.Music;
import com.kgc.sauw.utils.Langs;

import static com.kgc.sauw.graphic.Graphic.*;

public class SAUW implements Screen {

    com.kgc.sauw.map.World World;
    float WIDTH;
    float HEIGHT;
    GameInterface GI;

    Items ITEMS;
    Blocks BLOCKS;
    Settings settings;

    int camX, camY;
    Achievements achievements;
    Mods mods;
    Crafting crafting;
    public ModAPI ModAPI;
    public Langs langs;
    MainGame game;
    Music music;

    Box2DDebugRenderer DR;

    public SAUW(MainGame game, Music music, String worldName) {
        this.game = game;
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        crafting = new Crafting();
        settings = new Settings();
        langs = new Langs(settings);
        achievements = new Achievements(langs);

        ITEMS = new Items(langs);
        GI = new GameInterface(ITEMS, settings, langs);
        BLOCKS = new Blocks(langs);
        World = new World(ITEMS, BLOCKS, GI, settings);
        BLOCKS.setItems(ITEMS);
        DR = new Box2DDebugRenderer();
        this.music = music;
        music.w = World;
        music.setMusicVolume(settings.musicVolume);
        BLOCKS.initialize(ITEMS, GI, World, langs);
        ModAPI = new ModAPI(GI);
        mods = new Mods();
        GI.initialize(crafting, ModAPI, game, langs, World);
        if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
            World.createNewWorld();
            World.save(worldName);
        } else {
            World.load(worldName);
        }
        mods.load(World.pl, BLOCKS, ITEMS, ModAPI, crafting, settings, GI);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GAME_CAMERA.update(BATCH);

        BLOCKS.interfacesUpdate(World.maps, World.pl, INTERFACE_CAMERA);
        GI.update(World.pl);
        music.update(false);
        BATCH.begin();
        if (GI.isInterfaceOpen) BATCH.setColor(0.5f, 0.5f, 0.5f, 1);
        World.renderLowLayer();
        World.renderHighLayer();
        World.renderEntitys();
        World.renderLights();
        if (GI.isInterfaceOpen) BATCH.setColor(1, 1, 1, 1);
        BATCH.end();
        if (settings.debugRenderer) DR.render(World.world, GAME_CAMERA.CAMERA.combined);
        BATCH.begin();
        GI.render(World, World.pl, settings.debugMode);
        BLOCKS.interfacesRender(World.maps, World.pl, INTERFACE_CAMERA);
        GI.update(World.pl);
        World.update(mods, achievements);


        camX = (int) ((World.pl.posX + (World.pl.plW / 2)) - (GAME_CAMERA.W / 2));
        camY = (int) (World.pl.posY + (World.pl.plH / 2) - (GAME_CAMERA.H / 2));
        if (camX < WIDTH / 16) camX = (int) WIDTH / 16;
        if (camY < WIDTH / 16) camY = (int) WIDTH / 16;
        if (camX + GAME_CAMERA.W > (World.maps.map0[0].length - 1) * (WIDTH / 16))
            camX = (int) ((World.maps.map0[0].length - 1) * (WIDTH / 16) - GAME_CAMERA.W);
        if (camY + GAME_CAMERA.H > (World.maps.map0.length - 1) * (WIDTH / 16))
            camY = (int) ((World.maps.map0.length - 1) * (WIDTH / 16) - GAME_CAMERA.H);

        GAME_CAMERA.lookAt(camX, camY);
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
