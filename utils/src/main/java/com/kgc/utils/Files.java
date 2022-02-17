package com.kgc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Files {
    public static FileHandle tempDir = Gdx.files.external("S.A.U.W./temp");

    public static FileHandle tempFile(String path) {
        return tempDir.child(path);
    }

    public static void clearTempDir() {
        FileHandle[] files = tempDir.list();
        for (FileHandle file : files) file.delete();
    }
}
