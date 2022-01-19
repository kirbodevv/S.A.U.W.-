package com.kgc.sauwlauncher.launcher;

import com.kgc.sauwlauncher.sauwupdater.Downloader;
import com.kgc.sauwlauncher.sauwupdater.Updater;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private boolean isDownloading = false;

    private static Controller controller;

    public static Controller getController() {
        return controller;
    }

    public ComboBox<String> versionSelector;
    public Button start;
    public HBox group;
    public ProgressBar progressBar;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private Process process;
    private ObservableList<String> dbTypeList = FXCollections.observableArrayList(Updater.versionsList);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        versionSelector.setVisibleRowCount(3);
        versionSelector.setItems(dbTypeList);
        if (!Settings.getString("LastVersion").equals("null")) {
            versionSelector.setValue(Settings.getString("LastVersion"));
        }
        versionSelector.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Settings.set("LastVersion", newValue);
                try {
                    Settings.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.setStyle("-fx-base : #269696;");


        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    String version = versionSelector.getSelectionModel().getSelectedItem();
                    if (version != null) {
                        if (!new File(Files.versionDir, version).exists()) {
                            Downloader.download(versionSelector.getSelectionModel().getSelectedItem());
                            isDownloading = true;
                        } else {
                            String cmd = "java -jar " + new File(Files.versionDir, version + "/" + version + ".jar");
                            System.out.println(cmd);
                            if (process != null) {
                                process.destroy();
                                process = null;
                            }
                            process = Runtime.getRuntime().exec(cmd);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Timeline tick = new Timeline(
                new KeyFrame(
                        new Duration(10),
                        t -> {
                            if (isDownloading) {
                                progressBar.setProgress(Downloader.getProgress());
                                System.out.println(Downloader.getProgress());
                                if (Downloader.getProgress() == 1) {
                                    isDownloading = false;
                                    start.setText("Запустить");
                                }
                            } else {
                                if (new File(Files.versionDir, versionSelector.getSelectionModel().getSelectedItem()).exists()) {
                                    start.setText("Запустить");
                                } else {
                                    start.setText("Установить");
                                }
                            }
                            if (process != null) {
                                if (process.isAlive()) {
                                    if (Main.getStage().isShowing()) Main.getStage().hide();
                                } else {
                                    if (!Main.getStage().isShowing()) Main.getStage().show();
                                    InputStream error = process.getErrorStream();
                                    StringBuilder result = new StringBuilder();
                                    try (Reader reader = new BufferedReader(new InputStreamReader
                                            (error, Charset.forName(StandardCharsets.UTF_8.name())))) {
                                        int c = 0;
                                        while ((c = reader.read()) != -1) {
                                            result.append((char) c);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(result);
                                    process = null;
                                            /*alert.setTitle("S.A.U.W. Error");
                                            alert.setHeaderText("Look, an Information Dialog");
                                            alert.setContentText("I have a great message for you!");
                                            alert.showAndWait();*/
                                }
                            }

                        }
                )
        );
        tick.setCycleCount(Animation.INDEFINITE);
        tick.play();
    }
}
