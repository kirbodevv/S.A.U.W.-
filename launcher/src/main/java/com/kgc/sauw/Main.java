package com.kgc.sauw;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 500;
        config.height = 300;
        config.title = "S.A.U.W. Launcher";
        config.resizable = false;
        config.addIcon("icon/icon_16.png", Files.FileType.Internal);
        config.addIcon("icon/icon_32.png", Files.FileType.Internal);
        config.addIcon("icon/icon_64.png", Files.FileType.Internal);
        config.addIcon("icon/icon_128.png", Files.FileType.Internal);
        new LwjglApplication(new GUI(), config);
    }
}
