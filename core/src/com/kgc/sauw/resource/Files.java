package com.kgc.sauw.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;

public class Files {
    public static JSONObject playerData;


    public static void loadPlayerData(){
        try {
            String result = "";
            FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
            result = data.readString();
            playerData = new JSONObject(result);
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
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
