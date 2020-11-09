package com.KGC.SAUW.screen;

import com.badlogic.gdx.Screen;
import com.KGC.SAUW.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import java.util.*;
import com.KGC.SAUW.InterfaceAPI.Button;
import com.KGC.SAUW.InterfaceAPI.Interface;
import java.io.File;
import java.io.FileReader;
import android.os.Environment;
import org.json.JSONObject;
import java.io.FileWriter;
import com.KGC.SAUW.InterfaceAPI.InterfaceEvents;
public class MenuScreen implements Screen {
	boolean gameStart = false;
	MainGame game;
	Random random = new Random();
	int xC, yC;
	int w, h;
	SpriteBatch b;
	Camera2D camera;
	int WIDTH = Gdx.graphics.getWidth();
	Button startButton;
	Button settingsButton;
	Button exitButton;
	World world;
	Blocks blocks;
	Textures t;
	Timer timer = new Timer();
	float tmr;
	int camX, camY;
	boolean StartGameMenu = false;
    //button testWorld;
	//button createWorld;
	int SAUW_coins = 0;
	Button sel_0;
	Button sel_1;
	Button sel_2;
	Button createNewWorld;
	Button up;
	Button down;
	private String result = "";
	Interface createWorldInterface;
	JSONObject data;
	Settings settings;
	Langs langs;
	private Music music;
	public SettingsScreen SettingsScreen;
	BitmapFont bf = new BitmapFont(Gdx.files.internal("ttf.fnt"));
	public MenuScreen(final MainGame game) {
		File mainDir = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W.");
		File user = new File(mainDir.toString() + "/User");
		File settings = new File(user.toString() + "/settings.json");
		if (!settings.exists()) {
            try {
				settings.createNewFile();
				FileWriter s = new FileWriter(settings.toString());
				s.write(Gdx.files.internal("json/settings.json").readString());
				s.close();
			} catch (Exception e) {

			}
		}
		this.settings = new Settings();
		langs = new Langs(this.settings);
		try {
			File data = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W./User/data.json");
			FileReader s = new FileReader(data.toString());
			Scanner scanner = new Scanner(s);
			while (scanner.hasNextLine()) {
				result += scanner.nextLine();
			}
			s.close();
			this.data = new JSONObject(result);


			SAUW_coins = this.data.getInt("SAUW_Coins");
		} catch (Exception e) {
			Gdx.app.log("error", e.toString());
		}
		this.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		t = new Textures();
		Items items = new Items(t, langs);
		t.load();
		timer.schedule(new TimerTask(){
				@Override
				public void run() {
					xC = random.nextInt(11) - 5;
					yC = random.nextInt(11) - 5;
				}
			}, 0, 5000);
		camera = new Camera2D();
		b = new SpriteBatch();
		SettingsScreen = new SettingsScreen(game, t, this);
		startButton = new Button("", w / 16 * 5, h - w / 16 * 5 + w / 138, w / 16 * 6, w / 16);
		startButton.setTextColor(Color.BLACK);
		startButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					StartGameMenu = true;
				}
			});
		settingsButton = new Button("", w / 16 * 5, h - w / 16 * 6, w / 16 * 6, w / 16);
		settingsButton.setTextColor(Color.BLACK);
		settingsButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					game.setScreen(SettingsScreen);
				}
			});
		exitButton = new Button("", w / 16 * 5, h - w / 16 * 7 - w / 128, w / 16 * 6, w / 16);
		exitButton.setTextColor(Color.BLACK);
		exitButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					Gdx.app.exit();
				}
			});
		/*testWorld = new button(1, w / 16 * 5, h - w / 16 * 5, w / 16 * 6, w / 16, t.BButton_0, t.BButton_1);
		 testWorld.setTextColor(new Color(Color.BLACK));
		 testWorld.setText("Test World");*/
		sel_0 = new Button("", w / 16 * 5, h - w / 16 * 5 + w / 128, w / 16 * 6, w / 16);
		sel_0.setTextColor(Color.BLACK);
	    sel_0.setText("World 1");
		sel_0.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
			        SettingsScreen.dispose();
					dispose();
					game.setScreen(new MyGdxGame(game, t, b, music, "TestWorld"));
				}
			});
		sel_1 = new Button("", w / 16 * 5, h - w / 16 * 6, w / 16 * 6, w / 16);
		sel_1.setTextColor(Color.BLACK);
	    sel_1.setText("World 2");

		sel_2 = new Button("", w / 16 * 5, h - w / 16 * 7 - w / 128, w / 16 * 6, w / 16);
		sel_2.setTextColor(Color.BLACK);
	    sel_2.setText("World 3");

		createWorldInterface = new Interface(Interface.InterfaceSizes.STANDART, t, b, camera, items, null);
		createWorldInterface.setInterfaceEvents(new InterfaceEvents(){

				@Override
				public void initialize() {
				}

				@Override
				public void tick() {
				}

				@Override
				public void onOpen() {
				}

				@Override
				public void renderBefore() {
				}

				@Override
				public void render() {
				}
			});
		createNewWorld = new Button("", WIDTH / 16 * 5, 0, WIDTH / 16 * 6, WIDTH / 16);
		createNewWorld.setTextColor(Color.BLACK);
		createNewWorld.setText(langs.getString("createNewWorld"));
        createNewWorld.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					createWorldInterface.open();
				}
		});
		
		up = new Button("", w / 32 * 23, sel_0.Y, w / 16, w / 16, t.button_up_0, t.button_up_1);
		down = new Button("", w / 32 * 23, sel_2.Y, w / 16, w / 16, t.button_down_0, t.button_down_1);
		blocks = new Blocks(t, langs);
		world = new World(b, t, items, camera, blocks);
		String lastWorld = null;
		try {
			JSONObject data = new JSONObject(Gdx.files.external("S.A.U.W./User/data.json").readString());
			lastWorld = data.getString("lastWorld");
		} catch (Exception e) {

		}
		if (lastWorld != null) {
			if (Gdx.files.external("S.A.U.W./Worlds/" + lastWorld).exists())
				world.load(lastWorld);
			else world.createNewWorld();
		} else {
			world.createNewWorld();
		}
		startButton.setText(langs.getString("startGame"));
		settingsButton.setText(langs.getString("settings"));
		exitButton.setText(langs.getString("exit"));
		music = new Music(this.settings, null);
	}
	@Override
    public void show() {

    }
    @Override
    public void render(float delta) {
		music.update(true);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camX += xC;
		camY += yC;
		if (camX < WIDTH / 16) camX = (int)WIDTH / 16;
		if (camY < WIDTH / 16) camY = (int)WIDTH / 16;
		if (camX + camera.W > (world.maps.map0[0].length - 1) * (WIDTH / 16)) camX = (int)((world.maps.map0[0].length - 1) * (WIDTH / 16) - camera.W);
		if (camY + camera.H > (world.maps.map0.length - 1) * (WIDTH / 16)) camY = (int)((world.maps.map0.length - 1) * (WIDTH / 16) - camera.H);
		camera.lookAt(camX, camY);
		camera.update(b);
		//startButton.setText("" + cam.X + " " + cam.Y);
		b.begin();
		world.renderLowLayer();
		world.renderHighLayer();
		b.draw(t.logo, camera.X + w / 16 * 5, camera.Y + h - w / 16 * 4, w / 16 * 6, w / 16 * 3);
		if (!StartGameMenu) {
			startButton.update(camera);
			settingsButton.update(camera);
			exitButton.update(camera);
			startButton.render(b, camera);
			settingsButton.render(b, camera);
			exitButton.render(b, camera);
			b.draw(t.SAUWCoin, camera.X + w / 32, camera.Y + h - w / 16, w / 32, w / 32);
			bf.setScale(w / 768);
			bf.draw(b, SAUW_coins + "", camera.X + w / 16 + w / 64, camera.Y + h - w / 32);
		} else {
			//testWorld.update(cam);
			//testWorld.render(b, cam);
			sel_0.update(camera);
			sel_1.update(camera);
			sel_2.update(camera);
			createNewWorld.update(camera);
			createWorldInterface.update(null, camera);
			up.update(camera);
			down.update(camera);

			sel_0.render(b, camera);
			sel_1.render(b, camera);
			sel_2.render(b, camera);
			createNewWorld.render(b, camera);
			up.render(b, camera);
			down.render(b, camera);
			createWorldInterface.render(null, camera);
		}
		b.end();
    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {
    }

}
