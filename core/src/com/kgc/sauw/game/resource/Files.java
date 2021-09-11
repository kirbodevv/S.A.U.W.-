package com.kgc.sauw.game.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;

public class Files {
    public static JSONObject playerData;
    public static JSONObject availableLanguages;

    static {
        availableLanguages = new JSONObject(Gdx.files.internal("json/availableLanguages.json").readString());
        playerData = new JSONObject(Gdx.files.external("S.A.U.W./User/data.json").readString());
    }

    public static void saveData() {
        FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
        try {
            data.writeString(playerData.toString(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
