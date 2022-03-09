package com.kgc.sauw.os;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 840;
        config.height = 480;
        config.title = "S.A.U.W. OS";

        new LwjglApplication(new TempApp(), config);
    }

    public static class TempApp extends Game {
        SAUWOS os;

        @Override
        public void create() {
            os = new SAUWOS();
            os.start();
        }

        @Override
        public void dispose() {
            os.shutdown();
            super.dispose();
        }
    }
}
