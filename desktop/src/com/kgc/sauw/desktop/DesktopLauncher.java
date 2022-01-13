package com.kgc.sauw.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.callbacks.Initialization;
import com.kgc.sauw.game.Game;

import java.util.ArrayList;
import java.util.List;

public class DesktopLauncher {
    public static DesktopLauncher INSTANCE;
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"--defaultworld", "-dw"}, description = "Default world")
    private String defaultWorld = null;

    @Parameter(names = {"--devmode", "-dm"}, description = "Developer mode")
    private boolean devmode = false;

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 840;
        config.height = 480;
        config.title = "S.A.U.W.";
        config.addIcon("icon/icon_16.png", Files.FileType.Internal);
        config.addIcon("icon/icon_32.png", Files.FileType.Internal);
        config.addIcon("icon/icon_64.png", Files.FileType.Internal);
        config.addIcon("icon/icon_128.png", Files.FileType.Internal);

        INSTANCE = new DesktopLauncher();

        JCommander.newBuilder()
                .addObject(INSTANCE)
                .build()
                .parse(args);

        INSTANCE.run(Game.getGame(), config);
    }

    public void run(Game game, LwjglApplicationConfiguration config) {
        new LwjglApplication(game, config);
        Callback.addCallback((Initialization) () -> {
            if (defaultWorld != null) Game.load(defaultWorld);
            if (devmode) Game.loadInDevMode();
        });
    }
}
