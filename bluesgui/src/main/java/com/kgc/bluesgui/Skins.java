package com.kgc.bluesgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Skins {
    private static final HashMap<String, SkinPackage> skin_packages = new HashMap<>();

    public static final ElementSkin transparent;
    public static final int DEFAULT_OUTLINE_SIZE = 7;
    private static SkinPackage current_skin_package;
    private final static SkinPackage default_skin_package;

    static {
        transparent = new ElementSkin();
        addSkinPackage(Gdx.files.internal("default_skin_package/default_skin_package.skin"));
        default_skin_package = skin_packages.get("default_skin_package");
        default_skin_package.skins.put("transparent", transparent);
        useSkinPackage("default_skin_package");
    }

    public static void useSkinPackage(String packageID) {
        current_skin_package = skin_packages.get(packageID);
    }

    public static ElementSkin getSkin(String skinName) {
        return current_skin_package.skins.getOrDefault(skinName, current_skin_package.skins.get(skinName));
    }

    public static void addSkinPackage(FileHandle packageFile) {
        Gdx.app.log("Skin package", "loading skin package \"" + packageFile.nameWithoutExtension() + "\"");
        skin_packages.put(packageFile.nameWithoutExtension(), new SkinPackage(packageFile));
    }

    public static void dispose() {
        Collection<SkinPackage> packages = skin_packages.values();
        for (SkinPackage package_ : packages) {
            package_.dispose();
        }
    }

    public static class SkinPackage {
        protected final HashMap<String, ElementSkin> skins = new HashMap<>();

        public SkinPackage(FileHandle skinPackageFile) {
            String[] lines = skinPackageFile.readString().split("\\r?\\n");
            for (String line : lines) {
                String[] data = line.split(":");
                String id = data[0];
                String texture = data[1];
                int outlineSize = data[2].equals("default") ? DEFAULT_OUTLINE_SIZE : Integer.parseInt(data[2]);
                int color = Integer.decode(data[3]);

                ElementSkin skin = new ElementSkin(new Texture(skinPackageFile.parent().child(texture)), outlineSize);
                skin.setColor(color);

                skins.put(id, skin);

                Gdx.app.log("Skin package", "loaded skin \"" + id + "\" outline size " + outlineSize + " color " + "\"" + data[3] + "\"");
            }
        }

        public void dispose() {
            Set<String> skins = this.skins.keySet();
            for (String skin : skins) {
                Gdx.app.log("Skin package", "skin disposed \"" + skin + "\"");
                Texture t = this.skins.get(skin).texture;
                if (t != null) t.dispose();
            }
        }
    }
}
