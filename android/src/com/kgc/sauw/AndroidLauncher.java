package com.kgc.sauw;

import android.os.Bundle;
import android.os.StrictMode;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jvmfrog.curve.CurveGDX;
import com.jvmfrog.curvegdx.backends.AndroidBackend;
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

        CurveGDX.init(new AndroidBackend(this));
        initialize(Game.getGame(), config);
    }
}
