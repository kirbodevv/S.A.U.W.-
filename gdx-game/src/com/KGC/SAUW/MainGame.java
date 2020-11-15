package com.KGC.SAUW;

import com.KGC.SAUW.screen.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;

public class MainGame extends Game {
    public int keyboardHeight = 0;

    @Override
    public void create() {
		//try {
			setScreen(new MenuScreen(this));
		/*} catch (Exception e) {
			writeLogToFile(e.toString());
			Gdx.app.exit();
		}*/
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
	public void writeLogToFile(String log) {
		FileHandle logFile = Gdx.files.external("S.A.U.W./log.txt");
		File f = logFile.file();
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (Exception e) {

			}
		}
		logFile.writeString(log, false);
	}

}
