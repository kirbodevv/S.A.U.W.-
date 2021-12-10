package com.kgc.sauw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.kgc.sauw.core.config.Settings;

import java.text.MessageFormat;
import java.util.HashMap;

public class UpdatesChecker {
    private static HashMap<String, String> versions = new HashMap<>();

    private static int lastVersionCode;
    private static String androidLink;
    private static String desktopLink;
    private static String[] changelog = new String[]{"", "", "", "", ""};
    private static String fullChangelogLink;

    private static void getRequest(String url, Net.HttpResponseListener httpResponse) {
        Net.HttpRequest httpRequest = new Net.HttpRequest("GET");
        httpRequest.setUrl(url);
        httpRequest.setContent(null);
        Gdx.net.sendHttpRequest(httpRequest, httpResponse);
    }

    public static void check(UpdatesListener listener) {
        getRequest(
                "https://raw.githubusercontent.com/KirboGames/S.A.U.W.-versions/main/versions", new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        String result = httpResponse.getResultAsString();
                        versions = new HashMap<>();
                        String[] lines = result.split("\\r?\\n");
                        for (String line : lines) {
                            String[] keys = line.split("=");
                            versions.put(keys[0], keys[1]);
                        }
                        lastVersionCode = Integer.parseInt(versions.get("last"));

                        androidLink = MessageFormat.format("https://github.com/KirboGames/S.A.U.W.-versions/raw/main/{0}/android.apk", versions.get(lastVersionCode + ""));
                        desktopLink = MessageFormat.format("https://github.com/KirboGames/S.A.U.W.-versions/raw/main/{0}/desktop.jar", versions.get(lastVersionCode + ""));
                        fullChangelogLink = MessageFormat.format("https://github.com/KirboGames/S.A.U.W.-versions/blob/main/{0}/full_changelogs/{1}.md",
                                versions.get(lastVersionCode + ""),
                                Settings.lang);
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

    public static void getChangelog(ChangelogsListener listener) {
        final String[] changelog = new String[5];
        getRequest(MessageFormat.format("https://raw.githubusercontent.com/KirboGames/S.A.U.W.-versions/main/{0}/short_changelogs/{1}.txt", getLastVersionName(), Settings.lang),
                new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        String result = httpResponse.getResultAsString();
                        String[] lines = result.split("\\r?\\n");
                        System.arraycopy(lines, 0, changelog, 0, changelog.length);
                        listener.changelogReceived();
                    }

                    @Override
                    public void failed(Throwable t) {

                    }

                    @Override
                    public void cancelled() {

                    }
                });
        UpdatesChecker.changelog = changelog;
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

    public static String[] getChangelog() {
        return changelog;
    }

    public interface UpdatesListener {
        void updatesChecked();
    }

    public interface ChangelogsListener {
        void changelogReceived();
    }
}
