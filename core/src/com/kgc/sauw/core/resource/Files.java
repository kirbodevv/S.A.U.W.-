package com.kgc.sauw.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Files {
    public static FileHandle sauwDir = Gdx.files.external("S.A.U.W.");
    public static FileHandle userDir = sauwDir.child("User");
    public static FileHandle worldsDir = sauwDir.child("Worlds");
    public static FileHandle screenshotsDir = sauwDir.child("Screenshots");
    public static FileHandle modsDir = sauwDir.child("Mods");
}
