package com.kgc.sauwlauncher.launcher;

import com.kgc.sauwlauncher.sauwupdater.Updater;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

public class Main extends Application {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        root.getStylesheets().add("bg.css");
        primaryStage.setTitle("S.A.U.W. Launcher");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("com/kgc/sauwlauncher/launcher/icon.png"));
        stage = primaryStage;
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Settings.load();
        Settings.save();
        try {
            Updater.update();
        } catch (ConnectException e) {
            for (File file : Files.versionDir.listFiles()) {
                if (file.isDirectory()) Updater.versionsList.add(file.getName());
            }
        }
        launch(args);

    }
}
