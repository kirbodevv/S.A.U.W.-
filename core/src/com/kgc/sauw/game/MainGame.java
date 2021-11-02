package com.kgc.sauw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.game.gui.screen.MenuScreen;
import org.json.JSONObject;

import java.io.IOException;

import static com.kgc.sauw.core.input.Input.INPUT_MULTIPLEXER;

public class MainGame extends Game {
    private static MainGame game;
    private static MenuScreen menuScreen;
    private static JSONObject data;

    public static MenuScreen getMenuScreen() {
        if (menuScreen == null) menuScreen = new MenuScreen();
        return menuScreen;
    }

    public static MainGame getGame() {
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
            createFiles();
            Settings.loadSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gdx.input.setInputProcessor(INPUT_MULTIPLEXER);
        setScreen(getMenuScreen());
        game = this;
    }

    public void createFiles() throws IOException {
        FileHandle mainFolder = Gdx.files.external("S.A.U.W.");

        FileHandle ModsFolder = Gdx.files.external("S.A.U.W./Mods");
        FileHandle ScreenshotsFolder = Gdx.files.external("S.A.U.W./Screenshots");
        FileHandle UserFolder = Gdx.files.external("S.A.U.W./User");
        FileHandle WorldsFolder = Gdx.files.external("S.A.U.W./Worlds");

        FileHandle userData = Gdx.files.external("S.A.U.W./User/data.json");
        FileHandle settings = Gdx.files.external("S.A.U.W./User/settings.json");
        FileHandle modsFile = Gdx.files.external("S.A.U.W./Mods/Mods.json");

        if (!mainFolder.exists())
            mainFolder.mkdirs();
        if (!ModsFolder.exists())
            ModsFolder.mkdirs();
        if (!ScreenshotsFolder.exists())
            ScreenshotsFolder.mkdirs();
        if (!UserFolder.exists())
            UserFolder.mkdirs();
        if (!WorldsFolder.exists())
            WorldsFolder.mkdirs();
        if (!settings.exists()) {
            settings.file().createNewFile();
            settings.writeString(Gdx.files.internal("json/settings.json").readString(), false);
        }
        if (!userData.exists()) {
            userData.file().createNewFile();
            userData.writeString("{\n\"SAUW_Coins\" : 0,\n\"lastWorld\":null}", false);
        }
        if (!modsFile.exists()) {
            modsFile.file().createNewFile();
            modsFile.writeString("[]", false);
        }

    }
}