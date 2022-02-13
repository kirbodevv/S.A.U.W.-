package com.kgc.sauw;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.FileDownloader;

public class AndroidFileDownloader implements FileDownloader {
    private DownloadManager manager;
    private final Context context;

    public AndroidFileDownloader(Context context) {
        this.context = context;
    }

    @SuppressLint("Range")
    @Override
    public void download(String url, FileHandle downloadTo, ProgressListener progressListener) {
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(downloadTo.name());
        request.setDescription("Downloading...");
        request.setDestinationInExternalFilesDir(context, downloadTo.parent().path(), downloadTo.name());
        long reference = manager.enqueue(request);

        new DownloadProgressCounter(reference, progressListener).start();
    }

    class DownloadProgressCounter extends Thread {

        private final long downloadId;
        private final DownloadManager.Query query;
        private Cursor cursor;
        private int lastBytesDownloadedSoFar;
        private int totalBytes;
        private ProgressListener progressListener;

        public DownloadProgressCounter(long downloadId, ProgressListener progressListener) {
            this.progressListener = progressListener;
            this.downloadId = downloadId;
            this.query = new DownloadManager.Query();
            query.setFilterById(this.downloadId);
        }

        @SuppressLint("Range")
        @Override
        public void run() {

            while (downloadId > 0) {
                try {
                    Thread.sleep(300);

                    cursor = manager.query(query);
                    if (cursor.moveToFirst()) {

                        //get total bytes of the file
                        if (totalBytes <= 0) {
                            totalBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        }

                        final int bytesDownloadedSoFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        final int progress = ((int) (((double) bytesDownloadedSoFar / (double) totalBytes) * 100));

                        if (bytesDownloadedSoFar == totalBytes && totalBytes > 0) {
                            this.interrupt();
                            progressListener.done();
                        } else {
                            progressListener.update(progress);
                        }

                    }
                    cursor.close();
                } catch (Exception e) {
                    return;
                }
            }
        }

    }
}
