package com.kgc.sauw.version;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class Version {
    public static final ArrayList<Version> versions = new ArrayList<>();
    public static final FileHandle versionsDir;
    public static Version currentVersion;

    public static Version get(int codeVersion) {
        for (Version v : versions) if (v.codeVersion == codeVersion) return v;
        return null;
    }

    public static Version get(String name) {
        for (Version v : versions) if (v.name.equals(name)) return v;
        return null;
    }

    static {
        versionsDir = Gdx.files.external("S.A.U.W./Versions");
        if (!versionsDir.exists()) versionsDir.mkdirs();
    }

    public String name;
    public int codeVersion;
    public boolean isDownloaded = false;

    public Version(String name, int codeVersion) {
        this.name = name;
        this.codeVersion = codeVersion;
        versions.add(this);
    }
}
