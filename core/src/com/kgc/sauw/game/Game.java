package com.kgc.sauw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.UpdatesChecker;
import com.kgc.sauw.Version;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.environment.Environment;
import com.kgc.sauw.core.gui.Interfaces;
import com.kgc.sauw.core.resource.Files;
import com.kgc.sauw.game.environment.GameBlocks;
import com.kgc.sauw.game.generated.AchievementsGenerated;
import com.kgc.sauw.game.generated.ItemsGenerated;
import com.kgc.sauw.game.generated.RecipesGenerated;
import com.kgc.sauw.game.gui.screen.MenuScreen;
import com.kgc.sauw.game.worlds.ChristmasWorld;
import com.kgc.sauw.modding.Mods;
import org.json.JSONObject;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.environment.Environment.setWorld;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.input.Input.INPUT_MULTIPLEXER;
import static com.kgc.sauw.game.gui.GameInterfaces.*;

public class Game extends com.badlogic.gdx.Game {
    public static boolean isRunning;

    static {
        Game.isRunning = false;
    }

    private static Game game;
    private static MenuScreen menuScreen;
    private static SAUW sauw;
    private static JSONObject data;

    public static void loadInDevMode() {
        setWorld(new ChristmasWorld());
        getWorld().createNewWorld();
        PLAYER.randomSpawn();
        load();
    }

    public static void load(String worldName) {
        Environment.setSaveName(worldName);
        //if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
        setWorld(new ChristmasWorld());
        getWorld().createNewWorld();
        PLAYER.randomSpawn();
        /*    Environment.save();
        } else {
            Environment.load();
        }*/
        load();
    }

    private static void load() {
        sauw = new SAUW();
        getGame().setScreen(sauw);
        isRunning = true;
    }


    public static void closeGame() {
        getGame().setScreen(getMenuScreen());
        isRunning = false;
        sauw.dispose();
        sauw = null;
    }

    public static MenuScreen getMenuScreen() {
        if (menuScreen == null) menuScreen = new MenuScreen();
        return menuScreen;
    }

    public static Game getGame() {
        if (game == null) game = new Game();
        return game;
    }

    public static JSONObject getData() {
        if (data == null)
            try {
                FileHandle dataFile = Gdx.files.external("S.A.U.W./User/data.json");
                String result = dataFile.readString();
                data = new JSONObject(result);
            } catch (Exception e) {
                Gdx.app.log("error", e.toString());
            }
        return data;
    }

    @Override
    public void create() {
        Files.createFiles();
        Settings.loadSettings();
        Gdx.input.setInputProcessor(INPUT_MULTIPLEXER);

        //S.A.U.W. initialization
        ItemsGenerated.init();
        AchievementsGenerated.init();
        RecipesGenerated.init();
        new GameBlocks();

        Mods.loadMods();
        setScreen(getMenuScreen());
        UpdatesChecker.check(() -> {
            if (UpdatesChecker.newVersionAvailable(Version.CODE_VERSION)) {
                UPDATES_INTERFACE.setVersion(UpdatesChecker.getLastVersionName());
                UpdatesChecker.getChangelog(() -> UPDATES_INTERFACE.setChangelog(UpdatesChecker.getChangelog()));
                UPDATES_INTERFACE.open();
            }
        });
        Callback.executeInitializationCallbacks();
    }

    @Override
    public void render() {
        super.render();
        Interfaces.updateInterfaces();
        BATCH.begin();
        Interfaces.renderInterfaces();
        BATCH.end();
    }
}