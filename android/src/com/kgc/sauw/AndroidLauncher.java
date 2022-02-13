package com.kgc.sauw;

import android.os.Bundle;
import android.os.StrictMode;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kgc.sauw.core.utils.Application;
import com.kgc.sauw.game.Game;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectNetwork()
                .build());
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        Application.fileDownloader = new AndroidFileDownloader(this);
        Application.apkOpener = new APKOpener(this);
        initialize(Game.getGame(), config);
    }
}
