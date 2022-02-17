package com.kgc.sauw.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kgc.sauw.core.utils.Application;
import com.kgc.utils.DesktopFileDownloader;
import com.kgc.sauw.game.Game;
import com.kgc.sauw.game.RunParameters;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 840;
        config.height = 480;
        config.title = "S.A.U.W.";
        config.addIcon("icon/icon_128.png", Files.FileType.Internal);
        RunParameters.set(args);
        Application.fileDownloader = new DesktopFileDownloader();
        new LwjglApplication(Game.getGame(), config);
    }
}
