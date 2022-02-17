package com.kgc.utils;

import com.badlogic.gdx.files.FileHandle;

public interface FileDownloader {
    interface ProgressListener {
        void update(int progress);

        void done();

        void failed(Throwable throwable);
    }

    void download(String url, FileHandle downloadTo, ProgressListener progressListener);
}
