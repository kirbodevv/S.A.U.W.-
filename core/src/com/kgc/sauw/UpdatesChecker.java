package com.kgc.sauw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.core.resource.Files;

import java.util.HashMap;

public class UpdatesChecker {
    private static HashMap<String, String> versions = new HashMap<>();

    private static int lastVersionCode;
    private static String androidLink;
    private static String desktopLink;
    private static String changelog = "";
    private static String fullChangelogLink;
    private static Texture screenshot;

    private static final String VERSIONS_LIST_LINK = "https://raw.githubusercontent.com/KirboGames/S.A.U.W.-versions/main/versions";
    private static final String REPOSITIRY_BLOB_LINK = "https://github.com/KirboGames/S.A.U.W.-versions/blob/main/";
    private static final String REPOSITIRY_RAW_LINK = "https://github.com/KirboGames/S.A.U.W.-versions/raw/main/";

    private static void getRequest(String url, Net.HttpResponseListener httpResponse) {
        Net.HttpRequest httpRequest = new Net.HttpRequest("GET");
        httpRequest.setUrl(url);
        httpRequest.setContent(null);
        Gdx.net.sendHttpRequest(httpRequest, httpResponse);
    }

    public static void check(UpdatesCallback listener) {
        getRequest(VERSIONS_LIST_LINK, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String result = httpResponse.getResultAsString();
                versions = new HashMap<>();
                String[] lines = result.split("\\r?\\n");
                for (String line : lines) {
                    String[] keys = line.split("=");
                    versions.put(keys[0], keys[1]);
                }

                String version = versions.get(lastVersionCode + "");
                androidLink = REPOSITIRY_RAW_LINK + getLastVersionName() + "/android.apk";
                desktopLink = REPOSITIRY_RAW_LINK + getLastVersionName() + "/desktop.jar";

                fullChangelogLink = REPOSITIRY_BLOB_LINK + version + "/full_changelogs/" + Settings.lang + ".md";

                listener.updatesChecked();
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    public static void updateChangelog(ChangelogsCallback listener) {
        getRequest(REPOSITIRY_RAW_LINK + getLastVersionName() + "/short_changelogs/" + Settings.lang + ".txt",
                new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        UpdatesChecker.changelog = httpResponse.getResultAsString();
                        listener.changelogReceived();
                    }

                    @Override
                    public void failed(Throwable t) {
                    }

                    @Override
                    public void cancelled() {
                    }
                });
    }

    public static void updateScreenshot(ScreenshotCallback screenshotCallback) {
        getRequest(REPOSITIRY_RAW_LINK + getLastVersionName() + "/img.png", new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final FileHandle tmpFile = Files.tempFile(getLastVersionName() + ".screenshot.png");
                tmpFile.write(httpResponse.getResultAsStream(), false);
                Gdx.app.postRunnable(() -> {
                    screenshot = new Texture(tmpFile);
                    screenshotCallback.screenshotReceived();
                });
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    public static int getLastCodeVersion() {
        return lastVersionCode;
    }

    public static boolean newVersionAvailable(int currentVersion) {
        return lastVersionCode > currentVersion;
    }

    public static String getAndroidLink() {
        return androidLink;
    }

    public static String getDesktopLink() {
        return desktopLink;
    }

    public static String getLastVersionName() {
        return versions.get(versions.get("last"));
    }

    public static String getFullChangelogLink() {
        return fullChangelogLink;
    }

    public static String getChangelog() {
        return changelog;
    }

    public static Texture getScreenshot() {
        return screenshot;
    }

    public interface UpdatesCallback {
        void updatesChecked();
    }

    public interface ChangelogsCallback {
        void changelogReceived();
    }

    public interface ScreenshotCallback {
        void screenshotReceived();
    }
}
