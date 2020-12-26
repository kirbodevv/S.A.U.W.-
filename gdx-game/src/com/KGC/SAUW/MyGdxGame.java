package com.KGC.SAUW;

import box2dLight.RayHandler;
import com.KGC.SAUW.CallbackAPI.Callbacks;
import com.KGC.SAUW.ModAPI.ModAPI;
import com.KGC.SAUW.mobs.ItemMob;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import java.util.Random;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
public class MyGdxGame implements Screen {

	SpriteBatch batch;
	Textures Textures;
	World World;
	float WIDTH;
	float HEIGHT;
	GameInterface GI;

	Items ITEMS;
	Blocks BLOCKS;
	Settings settings;

	Camera2D camera;
	int camX, camY;
    Achievements achievements;
    Callbacks Callbacks;
    BitmapFont bf = new BitmapFont();
	Mods mods;
	Crafting crafting;
	public ModAPI ModAPI;
	public Langs langs;
	MainGame game;
	Music music;

	Box2DDebugRenderer DR;
	@Override
	public  MyGdxGame(MainGame game, Textures t, SpriteBatch batch, Music music, String worldName) {
		this.game = game;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT =  Gdx.graphics.getHeight();
        crafting = new Crafting();
		settings = new Settings();
		langs = new Langs(settings);
		camera = new Camera2D((int)(Gdx.graphics.getWidth() * 1.25f));
	    this.batch = batch;
		this.Textures = t;
        achievements = new Achievements(langs);

		ITEMS = new Items(Textures, langs);
		GI = new GameInterface(Textures, batch, ITEMS, settings, langs);
		BLOCKS = new Blocks(Textures, langs);
		World = new World(batch, Textures, ITEMS, camera, BLOCKS , GI, settings);
		BLOCKS.setItems(ITEMS);
        DR = new Box2DDebugRenderer();
		this.music = music;
		music.w = World;
		music.settings = settings;
		BLOCKS.initialize(Textures, camera, ITEMS, GI, batch, World, langs);
		ModAPI = new ModAPI(GI);
		mods = new Mods();
		GI.initilizate(crafting, ModAPI, game, langs, World);
		mods.load(World.pl, BLOCKS, ITEMS, ModAPI, crafting, settings, GI, Textures);
		if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
			World.createNewWorld();
			World.save(worldName);
		} else {
			World.load(worldName);
		}
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		camera.update(batch);

		BLOCKS.interfacesUpdate(World.maps, World.pl, GI.interfaceCamera);
		GI.update(World.pl);
		music.update(false);
		batch.begin();
		if (GI.isInterfaceOpen) batch.setColor(0.5f, 0.5f, 0.5f, 1);
		World.renderLowLayer();

		World.renderHighLayer();
		if (GI.isInterfaceOpen) batch.setColor(1, 1, 1, 1);
		batch.end();
		if (settings.debugRenderer) DR.render(World.world, camera.CAMERA.combined);
		batch.begin();
		GI.render(World.pl, (settings.debugMode) ? 
				  " FPS:" + Gdx.graphics.getFramesPerSecond() + 
				  "\n Time:" + World.WorldTime.getTimeString() +
				  "\n " + (Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1024 / 1024 + " Mb" : "");
		BLOCKS.interfacesRender(World.maps, World.pl, GI.interfaceCamera);
	    GI.update(World.pl);
		World.update(mods, achievements);


		camX = ((World.pl.posX + (World.pl.plW / 2)) - (camera.W / 2));
		camY = World.pl.posY + (World.pl.plH / 2) - (camera.H / 2);
		if (camX < WIDTH / 16) camX = (int)WIDTH / 16;
		if (camY < WIDTH / 16) camY = (int)WIDTH / 16;
		if (camX + camera.W > (World.maps.map0[0].length - 1) * (WIDTH / 16)) camX = (int)((World.maps.map0[0].length - 1) * (WIDTH / 16) - camera.W);
		if (camY + camera.H > (World.maps.map0.length - 1) * (WIDTH / 16)) camY = (int)((World.maps.map0.length - 1) * (WIDTH / 16) - camera.H);

		camera.lookAt(camX, camY);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		Textures.dispose();
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
