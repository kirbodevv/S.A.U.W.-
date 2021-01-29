package com.KGC.SAUW;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        File mainDir = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W.");
		File mods = new File(mainDir.toString() + "/Mods");
		File Worlds = new File(mainDir.toString() + "/Worlds");
		File user = new File(mainDir.toString() + "/User");
		File Screenshots = new File(mainDir.toString() + "/Screenshots");
		File data = new File(user.toString() + "/data.json");
		if (!mainDir.exists()) {
			mainDir.mkdir();
		}
		if (!Worlds.exists()) {
			Worlds.mkdir();
		}
		if (!mods.exists()) {
		    mods.mkdir();
		}
		if (!user.exists()) {
			user.mkdir();
		}
		if(!Screenshots.exists()){
			Screenshots.mkdir();
		}

		if (!data.exists()) {
            try {
				data.createNewFile();
				FileWriter s = new FileWriter(data.toString());
				s.write("{\n\"SAUW_Coins\" : 0,\n\"lastWorld\":null}");
				s.close();
			} catch (Exception e) {

			}
		}
		final MainGame game = new MainGame();
		final View root = getWindow().getDecorView().findViewById(android.R.id.content);
		root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
				public void onGlobalLayout() {
					Rect visibleDisplayFrame = new Rect();
					getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleDisplayFrame);
					game.keyboardHeight = getWindow().getDecorView().getHeight() - visibleDisplayFrame.height();
				}
			});
		cfg.r = 8;
        cfg.g = 8;
        cfg.b = 8;
        cfg.a = 8;
        initialize(game, cfg);
    }
}
