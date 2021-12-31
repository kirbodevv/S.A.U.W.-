package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.languages.Languages;

public class ModLocalization {
    public ModLocalization(FileHandle modLocalizationDir) {
        FileHandle[] files = modLocalizationDir.list();
        for (FileHandle file : files) {
            if (file.name().endsWith(".language"))
                Languages.loadLanguage(file, file.nameWithoutExtension());
        }
    }
}
