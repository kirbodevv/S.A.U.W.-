package com.kgc.sauwlauncher.sauwupdater;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class Downloader {
    private static float progress;

    public static float getProgress() {
        return progress;
    }

    public static void download(String version) throws IOException {
        progress = 0;
        File downloadDir = new File(com.kgc.sauwlauncher.launcher.Files.versionDir, version);
        if (!downloadDir.exists()) downloadDir.mkdirs();

        URL url = new URL("https://github.com/KirboGames/S.A.U.W.-Releases/raw/main/" + version + "/desktop.jar");
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());

        new DownloadThread(url, version).start();
        new ProgressThread(httpConnection.getContentLength(), new File(com.kgc.sauwlauncher.launcher.Files.versionDir, version + "/" + version + ".jar")).start();
    }

    private static class DownloadThread extends Thread {
        private final URL url;
        private final String version;

        public DownloadThread(URL url, String version) {
            this.url = url;
            this.version = version;
        }

        @Override
        public void run() {
            super.run();
            try {
                FileUtils.copyURLToFile(url, new File(com.kgc.sauwlauncher.launcher.Files.versionDir, version + "/" + version + ".jar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ProgressThread extends Thread {
        private final int s;
        private final File file;

        public ProgressThread(int s, File file) {
            this.s = s;
            this.file = file;
        }

        @Override
        public void run() {
            super.run();
            int size = 0;
            try {
                size = (int) Files.size(file.toPath());
                progress = (float) size / s;
                System.out.println("Downloaded " + size + " bytes from " + s);
                System.out.println("Progress " + (progress * 100) + "%");
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s > 0 && size < s) new ProgressThread(s, file).start();
        }
    }
}
