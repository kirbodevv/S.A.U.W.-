package com.kgc.sauw;

import com.kgc.sauw.screen.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;

public class MainGame extends Game {
    public int keyboardHeight = 0;

    @Override
    public void create() {
    	createFiles();
    	setScreen(new MenuScreen(this));
    }
	public void createFiles(){
    	FileHandle ModsFolder = Gdx.files.external("S.A.U.W./Mods");
    	FileHandle ScreenshotsFolder = Gdx.files.external("S.A.U.W./Screenshots");
    	FileHandle UserFolder = Gdx.files.external("S.A.U.W./User");
    	FileHandle WorldsFolder = Gdx.files.external("S.A.U.W./Worlds");

    	FileHandle userData = Gdx.files.external("S.A.U.W./User/data.json");
		FileHandle settings = Gdx.files.external("S.A.U.W./User/settings.json");
		if(!ModsFolder.exists())
			ModsFolder.mkdirs();
		if(!ScreenshotsFolder.exists())
			ScreenshotsFolder.mkdirs();
		if(!UserFolder.exists())
			UserFolder.mkdirs();
		if(!WorldsFolder.exists())
			WorldsFolder.mkdirs();
		if(!settings.exists())
			settings.writeString(Gdx.files.internal("json/settings.json").readString(),false);
		if(!userData.exists())
			userData.writeString("{\n\"SAUW_Coins\" : 0,\n\"lastWorld\":null}", false);
	}
	@Override
	public void render() {
		//try {
			super.render();
		/*} catch (Exception e) {
			writeLogToFile(e.toString());
			Gdx.app.exit();
		}*/
	}
}
