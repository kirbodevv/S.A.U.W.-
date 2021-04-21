package com.kgc.sauw.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;

import java.util.*;

import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Interface;

import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.game.SAUW;
import com.kgc.sauw.resource.Music;
import org.json.JSONObject;

import com.badlogic.gdx.files.FileHandle;

import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class MenuScreen implements Screen {
    boolean gameStart = false;
    MainGame game;
    Random random = new Random();
    int xC, yC;
    int w, h;
    int WIDTH;
    Button startButton;
    Button settingsButton;
    Button modsButton;
    Button exitButton;

    Button closeButton;
    float tmr;
    int camX, camY;
    boolean StartGameMenu = false;
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
    private Music music;
    public SettingsScreen SettingsScreen;
    public ModsScreen ModsScreen;
    BitmapFont bf = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    private int worldSelIndex = 0;
    String[] worldNames;

    public MenuScreen(final MainGame game) {
        WIDTH = Gdx.graphics.getWidth();
        FileHandle settings = Gdx.files.external("S.A.U.W./User/settings.json");
        if (!settings.exists()) {
            try {
                settings.mkdirs();
                settings.writeString(Gdx.files.internal("json/settings.json").readString(), false);
            } catch (Exception e) {

            }
        }
        try {
            FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
            result = data.readString();
            this.data = new JSONObject(result);

            SAUW_coins = this.data.getInt("SAUW_Coins");
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
        this.game = game;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        SettingsScreen = new SettingsScreen(game, TEXTURES, this);
        ModsScreen = new ModsScreen(game, TEXTURES, this);
        startButton = new Button("MENU_SCREEN_START_BUTTON", w / 16 * 5, h - w / 16 * 5 + w / 128, w / 16 * 6, w / 16);
        startButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                StartGameMenu = true;
            }
        });
        settingsButton = new Button("MENU_SCREEN_SETTINGS_BUTTON", w / 16 * 5, h - w / 16 * 6, w / 16 * 6, w / 16);
        settingsButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                game.setScreen(SettingsScreen);
            }
        });
        modsButton = new Button("MENU_SCREEN_MODS_BUTTON", w / 16 * 5, h - w / 16 * 7 - w / 128, w / 16 * 6, w / 16);
        modsButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                game.setScreen(ModsScreen);
            }
        });
        exitButton = new Button("MENU_SCREEN_EXIT_BUTTON", w / 16 * 5, h - w / 16 * 8 - w / 128 * 2, w / 16 * 6, w / 16);
        exitButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });

        final FileHandle worldsFolder = Gdx.files.external("S.A.U.W./Worlds/");
        FileHandle[] files = worldsFolder.list();
        int i = 0;
        for (FileHandle file : files) {
            if (file.isDirectory()) i++;
        }

        worldNames = new String[i];
        int ii = 0;
        for (int j = 0; j < files.length; j++) {
            if (files[j].isDirectory()) worldNames[ii] = files[j].name();
            ii++;
        }

        sel_0 = new Button("MENU_SCREEN_WORLD_SELECTOR_1", w / 16 * 5, h - w / 16 * 5 + w / 128, w / 16 * 6, w / 16);
        sel_0.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(worldNames[worldSelIndex]);
            }
        });
        sel_1 = new Button("MENU_SCREEN_WORLD_SELECTOR_2", w / 16 * 5, h - w / 16 * 6, w / 16 * 6, w / 16);
        sel_1.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(worldNames[worldSelIndex + 1]);
            }
        });
        sel_2 = new Button("MENU_SCREEN_WORLD_SELECTOR_3", w / 16 * 5, h - w / 16 * 7 - w / 128, w / 16 * 6, w / 16);
        sel_2.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(worldNames[worldSelIndex + 2]);
            }
        });
        closeButton = new Button("MENU_SCREEN_CLOSE_WORLD_SELECTOR", 0, (h - w / 16), w / 16, w / 16, TEXTURES.button_left_0, TEXTURES.button_left_1);
        closeButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                StartGameMenu = false;
            }
        });
        createWorldInterface = new Interface(Interface.InterfaceSizes.FULL, "CREATE_NEW_WORLD_INTERFACE");
        createWorldInterface.setHeaderText(LANGUAGES.getString("createNewWorld"));
        /*createWorldInterface.setInterfaceEvents(new InterfaceEvents() {
            EditText worldName;
            Button create;
            BitmapFont bf = new BitmapFont(Gdx.files.internal("ttf.fnt"));

            @Override
            public void initialize() {
                worldName = new EditText((int) (Interface.x + WIDTH / 16), (int) (Interface.y + Interface.heigth - WIDTH / 16 * 3), WIDTH / 16 * 9, WIDTH / 16, INPUT_MULTIPLEXER);
                bf.setScale(WIDTH / 16 / 2 / bf.getCapHeight());
                bf.setColor(Color.BLACK);
                create = new Button("CREATE_NEW_WORLD_INTERFACE_CREATE_BUTTON", WIDTH / 32, WIDTH / 32, WIDTH / 16 * 3, WIDTH / 16);
                create.setText(LANGUAGES.getString("create"));
                create.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        for (int i = 0; i < worldNames.length; i++) {
                            if (worldName.input.equals(worldNames[i])) return;
                        }
                        loadGame(worldName.input);
                    }
                });
                Interface.buttons.add(create);
            }

            @Override
            public void tick() {
                worldName.update(MENU_CAMERA);
            }

            @Override
            public void onOpen() {
                worldName.input = LANGUAGES.getString("newWorld");
                worldName.hide(false);
            }

            @Override
            public void onClose() {
                worldName.hide(true);
            }

            @Override
            public void renderBefore() {
            }

            @Override
            public void render() {
                bf.draw(BATCH, LANGUAGES.getString("WorldName"), worldName.X + MENU_CAMERA.X, worldName.Y + worldName.height + WIDTH / 16 + MENU_CAMERA.Y);
                worldName.render(BATCH, MENU_CAMERA);
            }
        });*/
        createNewWorld = new Button("CREATE_NEW_WORLD_BUTTON", WIDTH / 32, WIDTH / 32, WIDTH / 16 * 6, WIDTH / 16);
        createNewWorld.setText(LANGUAGES.getString("createNewWorld"));
        createNewWorld.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                createWorldInterface.open();
            }
        });
        HideButtonsIfNeed();
        setSelectButtonsText();
        up = new Button("MENU_SCREEN_WORLD_SELECTOR_UP_BUTTON", w / 32 * 23, sel_0.Y, w / 16, w / 16, TEXTURES.button_up_0, TEXTURES.button_up_1);
        up.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                worldSelIndex--;
                if (worldSelIndex < 0) worldSelIndex = 0;
                HideButtonsIfNeed();
                setSelectButtonsText();
            }
        });
        down = new Button("MENU_SCREEN_WORLD_SELECTOR_DOWN_BUTTON", w / 32 * 23, sel_2.Y, w / 16, w / 16, TEXTURES.button_down_0, TEXTURES.button_down_1);
        down.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                worldSelIndex++;
                if (worldSelIndex >= worldNames.length) worldSelIndex = worldNames.length - 1;
                HideButtonsIfNeed();
                setSelectButtonsText();
            }
        });
        startButton.setText(LANGUAGES.getString("startGame"));
        settingsButton.setText(LANGUAGES.getString("settings"));
        modsButton.setText(LANGUAGES.getString("mods"));
        exitButton.setText(LANGUAGES.getString("exit"));

        music = new Music();
    }

    public void loadGame(String worldName) {
        SettingsScreen.dispose();
        dispose();
        game.setScreen(new SAUW(game, music, worldName));
    }

    public void setSelectButtonsText() {
        if (!sel_0.isHidden() && worldSelIndex < worldNames.length) sel_0.setText(worldNames[worldSelIndex]);
        if (!sel_1.isHidden() && worldSelIndex + 1 < worldNames.length) sel_1.setText(worldNames[worldSelIndex + 1]);
        if (!sel_2.isHidden() && worldSelIndex + 2 < worldNames.length) sel_2.setText(worldNames[worldSelIndex + 2]);
    }

    public void HideButtonsIfNeed() {
        if (worldSelIndex >= worldNames.length) sel_0.hide(true);
        else sel_0.hide(false);
        if (worldSelIndex + 1 >= worldNames.length) sel_1.hide(true);
        else sel_1.hide(false);
        if (worldSelIndex + 2 >= worldNames.length) sel_2.hide(true);
        else sel_2.hide(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        music.setMusicVolume(SETTINGS.musicVolume);
        music.update(true);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BATCH.begin();
        BATCH.setColor(0.6f, 0.6f, 0.6f, 1);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < SCREEN_HEIGHT / BLOCK_SIZE; y++) {
                BATCH.draw(TEXTURES.grass0, BLOCK_SIZE * x, BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        BATCH.setColor(1, 1, 1, 1);
        BATCH.draw(TEXTURES.logo, MENU_CAMERA.X + w / 16 * 5, MENU_CAMERA.Y + h - w / 16 * 4, w / 16 * 6, w / 16 * 3);
        if (!StartGameMenu) {
            startButton.update(MENU_CAMERA);
            settingsButton.update(MENU_CAMERA);
            modsButton.update(MENU_CAMERA);
            exitButton.update(MENU_CAMERA);
            startButton.render(BATCH, MENU_CAMERA);
            settingsButton.render(BATCH, MENU_CAMERA);
            modsButton.render(BATCH, MENU_CAMERA);
            exitButton.render(BATCH, MENU_CAMERA);
            BATCH.draw(TEXTURES.SAUWCoin, MENU_CAMERA.X + w / 32, MENU_CAMERA.Y + h - w / 16, w / 32, w / 32);
            bf.setScale(w / 768);
            bf.draw(BATCH, SAUW_coins + "", MENU_CAMERA.X + w / 16 + w / 64, MENU_CAMERA.Y + h - w / 32);
        } else {
            if (!createWorldInterface.isOpen) {
                sel_0.update(MENU_CAMERA);
                sel_1.update(MENU_CAMERA);
                sel_2.update(MENU_CAMERA);
                closeButton.update(MENU_CAMERA);
                createNewWorld.update(MENU_CAMERA);
                up.update(MENU_CAMERA);
                down.update(MENU_CAMERA);
            }
            createWorldInterface.update(false);

            if (!createWorldInterface.isOpen) {
                sel_0.render(BATCH, MENU_CAMERA);
                sel_1.render(BATCH, MENU_CAMERA);
                sel_2.render(BATCH, MENU_CAMERA);
                closeButton.render(BATCH, MENU_CAMERA);
                createNewWorld.render(BATCH, MENU_CAMERA);
                up.render(BATCH, MENU_CAMERA);
                down.render(BATCH, MENU_CAMERA);
            }
            createWorldInterface.render();
        }
        BATCH.end();
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
