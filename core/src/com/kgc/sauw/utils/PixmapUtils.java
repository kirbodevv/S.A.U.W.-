package com.kgc.sauw.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PixmapUtils {
    public static Pixmap extractPixmapFromTextureRegion(TextureRegion textureRegion) {
        TextureData textureData = textureRegion.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        textureData.disposePixmap();
        Pixmap pixmap = new Pixmap(
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                textureData.getFormat()
        );
        pixmap.drawPixmap(
                textureData.consumePixmap(),
                0,
                0,
                textureRegion.getRegionX(),
                textureRegion.getRegionY(),
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight()
        );
        return pixmap;
    }
}
