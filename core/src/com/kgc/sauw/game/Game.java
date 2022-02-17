package com.kgc.sauw.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kgc.sauw.Version;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.callbacks.Initialization;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.environment.Environment;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.Interfaces;
import com.kgc.sauw.core.resource.Files;
import com.kgc.sauw.game.environment.GameBlocks;
import com.kgc.sauw.game.generated.AchievementsGenerated;
import com.kgc.sauw.game.generated.ItemsGenerated;
import com.kgc.sauw.game.generated.RecipesGenerated;
import com.kgc.sauw.game.gui.screen.ErrorScreen;
import com.kgc.sauw.game.gui.screen.MenuScreen;
import com.kgc.sauw.game.worlds.MysticalVoidWorld;
import com.kgc.sauw.modding.Mods;
import com.kgc.sauw.modding.Modules;
import com.kgc.utils.UpdatesChecker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tools.ant.types.Commandline;
import org.json.JSONObject;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.environment.Environment.setWorld;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.INTERFACE_CAMERA;
import static com.kgc.sauw.core.input.Input.INPUT_MULTIPLEXER;
import static com.kgc.sauw.game.RunParameters.INSTANCE;
import static com.kgc.sauw.game.gui.GameInterfaces.UPDATES_INTERFACE;

public class Game extends com.badlogic.gdx.Game {
    public static boolean isRunning;

    static {
        Game.isRunning = false;
    }

    private static ErrorScreen errorScreen;
    private static Game game;
    private static MenuScreen menuScreen;
    private static SAUW sauw;
    private static JSONObject data;
    private static Box2DDebugRenderer DR;

    public static void loadInDevMode() {
        setWorld(new MysticalVoidWorld());
        getWorld().createNewWorld();
        PLAYER.randomSpawn();
        load();
    }

    public static void load(String worldName) {
        Environment.setSaveName(worldName);
        //if (!Gdx.files.external("S.A.U.W./Worlds/" + worldName).exists()) {
        setWorld(new MysticalVoidWorld());
        getWorld().createNewWorld();
        PLAYER.randomSpawn();
        /*    Environment.save();
        } else {
            Environment.load();
        }*/
        load();
    }

    private static void load() {
        Interfaces.closeInterface();
        sauw = new SAUW();
        getGame().setScreen(sauw);
        isRunning = true;
    }

    public static Box2DDebugRenderer getDebugRenderer() {
        if (DR == null) DR = new Box2DDebugRenderer();
        return DR;
    }


    public static void closeGame() {
        getGame().setScreen(getMenuScreen());
        isRunning = false;
        sauw.dispose();
        sauw = null;
        Interfaces.closeInterface();
    }

    public static void setErrorScreen(String errorMsg) {
        if (errorScreen == null) errorScreen = new ErrorScreen();
        errorScreen.setErrorMsg(errorMsg);
        getGame().setScreen(errorScreen);
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
        try {
            Files.createFiles();
            Files.clearTempDir();

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                String gameParams = Files.runParams.readString();
                if (!gameParams.isEmpty()) {
                    RunParameters.set(Commandline.translateCommandline(gameParams));
                }
            }

            Callback.addCallback((Initialization) () -> {
                if (INSTANCE.defaultWorld != null) Game.load(INSTANCE.defaultWorld);
                if (INSTANCE.devmode) Game.loadInDevMode();
            });

            Settings.loadSettings();
            Gdx.input.setInputProcessor(INPUT_MULTIPLEXER);

            //S.A.U.W. initialization
            ItemsGenerated.init();
            AchievementsGenerated.init();
            RecipesGenerated.init();
            new GameBlocks();

            Modules.load();
            Mods.loadMods();
            setScreen(getMenuScreen());
            UpdatesChecker.check(() -> {
                if (UpdatesChecker.newVersionAvailable(Version.CODE_VERSION)) {
                    UPDATES_INTERFACE.setVersion(UpdatesChecker.getLastVersionName());
                    UpdatesChecker.updateChangelog(() -> UPDATES_INTERFACE.setChangelog(UpdatesChecker.getChangelog()), Settings.lang);
                    UpdatesChecker.updateScreenshot(() -> UPDATES_INTERFACE.setScreenshot(UpdatesChecker.getScreenshot()));
                }
            }, Settings.lang);
            Callback.executeInitializationCallbacks();
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void resize(int width, int height) {
        Graphic.resize(width, height);
        super.resize(width, height);
    }

    @Override
    public void render() {
        try {
            super.render();
            Interfaces.updateInterfaces();
            BATCH.begin();
            INTERFACE_CAMERA.update(BATCH);
            Interfaces.renderInterfaces();
            BATCH.end();
        } catch (Exception e) {
            onError(e);
        }
    }

    private static void onError(Exception e) {
        Gdx.app.error("Error", ExceptionUtils.getStackTrace(e));
        if (!(getGame().screen instanceof ErrorScreen)) {
            if (BATCH.isDrawing()) BATCH.end();
            Game.isRunning = false;
            setErrorScreen(ExceptionUtils.getStackTrace(e));
        } else {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        if (DR != null) DR.dispose();
        if (sauw != null) sauw.dispose();
        UpdatesChecker.disposeScreenshot();
        super.dispose();
    }
}