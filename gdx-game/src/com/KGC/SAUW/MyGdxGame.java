package com.KGC.SAUW;

import com.KGC.SAUW.CallbackAPI.Callbacks;
import com.KGC.SAUW.commands.Cmd;
import com.KGC.SAUW.mobs.Mobs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.KGC.SAUW.mobs.ItemMob;
import com.KGC.SAUW.ModAPI.ModAPI;
import com.KGC.SAUW.commands.Cmd.Argument;
import java.util.Random;
import com.KGC.SAUW.screen.MenuScreen;
import box2dLight.RayHandler;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
public class MyGdxGame implements Screen {

	SpriteBatch batch;
	Textures Textures;
	World World;
	float WIDTH;
	float HEIGHT;
	GameInterface GI;
    //player pl;

	Items ITEMS;
	Blocks BLOCKS;
	Settings settings;

	Camera2D camera;
	//mobs mobs;
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
	RayHandler RayHandler;
	float TL = 720 / 0.6f;
	Box2DDebugRenderer DR;
	@Override
	public  MyGdxGame(MainGame game, Textures t, SpriteBatch batch, Music music, String worldName) {
		this.game = game;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT =  Gdx.graphics.getHeight();
        crafting = new Crafting();
		//crafting.addCraft(new crafting.craft(new int[]{9, 1, 0}, new int[][]{{7, 2, 0}}));
		//crafting.addCraft(new Crafting.craft(new int[]{10, 2, 0}, new int[][]{{8, 1, 0}}));
		crafting.addCraft(new Crafting.craft(new int[]{4, 1, 0}, new int[][]{{12, 4, 0}}));
        crafting.addCraft(new Crafting.craft(new int[]{14, 1, 0}, new int[][]{{7, 3, 0}, {12, 3, 0}, {17, 2, 0}}));
		crafting.addCraft(new Crafting.craft(new int[]{15, 1, 0}, new int[][]{{7, 3, 0}, {12, 3, 0}, {17, 2, 0}}));
		crafting.addCraft(new Crafting.craft(new int[]{22, 1, 0}, new int[][]{{7, 3, 0}, {12, 3, 0}, {17, 2, 0}}));
		crafting.addCraft(new Crafting.craft(new int[]{17, 1, 0}, new int[][]{{18, 3, 0}}));
		crafting.addCraft(new Crafting.craft(new int[]{16, 1, 0}, new int[][]{{12, 10, 0}, {13, 5, 0}}));
		settings = new Settings();
		langs = new Langs(settings);
		camera = new Camera2D((int)(Gdx.graphics.getWidth() * 1.5f));
	    this.batch = batch;
		this.Textures = t;
        achievements = new Achievements(langs);

		//achievements.addAchievement(new achievements.achievement("clown", "Тестовое достижение", "Ххх", Textures.clown, 10));
		ITEMS = new Items(Textures, langs);
		GI = new GameInterface(Textures, batch, ITEMS, settings, langs);
		BLOCKS = new Blocks(Textures, langs);
		World = new World(batch, Textures, ITEMS, camera, BLOCKS , GI, settings);
		if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
			World.createNewWorld();
			World.save(worldName);
		} else {
			World.load(worldName);
		}
		//mobs = new mobs(batch, World.maps, Textures);
		BLOCKS.setItems(ITEMS);
		//pl = new player(ITEMS, Textures, GI, mobs, World.maps, settings);
		///World.pl = pl;
		RayHandler = new RayHandler(World.world);
		RayHandler.setAmbientLight(1, 1, 1, 1);
		RayHandler.useDiffuseLight(true);
        DR = new Box2DDebugRenderer();

		World.rh = RayHandler;
		this.music = music;
		music.w = World;
		music.settings = settings;
		BLOCKS.initialize(Textures, camera, ITEMS, GI, batch, World, langs);
		ModAPI = new ModAPI(GI);
		mods = new Mods();
		GI.initilizate(crafting, ModAPI, game, langs, World);
		mods.load(World.pl, BLOCKS, ITEMS, ModAPI, crafting, settings, GI, Textures);
		//achievements.addAchievement(new achievements.achievement("SZD", "Не прячься", "Спрятаться за деревом", Textures.log, 10));
		World.setBlock(10, 7, 0, 9);
		World.setBlock(11, 7, 0, 10);

		Random r = new Random();
	    for (int i = 0; i < World.maps.map0.length; i++) {
			for (int j = 0; j < World.maps.map0[i].length; j++) {
				if (r.nextInt(75) == 0) {
					World.mobs.spawn(new ItemMob(j * (int)WIDTH / 16, i * (int)WIDTH / 16, 7, 1, 0, ITEMS));
				}
				if (r.nextInt(50) == 0) {
					World.mobs.spawn(new ItemMob(j * (int)WIDTH / 16, i * (int)WIDTH / 16, 12, 1, 0, ITEMS));
				}
			}
		}

		/*MS.SettingsScreen.dispose();
		 MS.dispose();*/
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
		
		;
		World.renderHighLayer();
		if (GI.isInterfaceOpen) batch.setColor(1, 1, 1, 1);
		batch.end();
		if (!GI.isInterfaceOpen) {
			float AL = 1.0f - (Maths.module(720 - World.WorldTime.getTime()) / TL);
			RayHandler.setAmbientLight(AL, AL, AL, 1);
			RayHandler.setCombinedMatrix(camera.CAMERA.combined);
			RayHandler.updateAndRender();
		}
		if (settings.debugMode) DR.render(World.world, camera.CAMERA.combined);
		batch.begin();
		GI.render(World.pl, (settings.debugMode) ? 
				  " FPS:" + Gdx.graphics.getFramesPerSecond() + 
				  "\n Time:" + World.WorldTime.getTimeString() +
				  "\n " + (Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1024 / 1024 + " Mb" :"");
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
