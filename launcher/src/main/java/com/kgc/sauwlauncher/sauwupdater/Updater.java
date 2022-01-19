package com.kgc.sauwlauncher.sauwupdater;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Updater {
    public static ArrayList<String> versionsList = new ArrayList<>();

    public static void update() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/KirboGames/S.A.U.W.-Releases/main/versions");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline.append(scanner.nextLine()).append("\n");
            }

            scanner.close();
            String[] versions = inline.toString().split("\n");
            for (String version : versions) {
                versionsList.add(version.split(":")[0]);
            }
        }
    }
}
