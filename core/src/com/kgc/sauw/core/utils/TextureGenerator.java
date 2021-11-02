package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureGenerator {
    public static Texture generateProgressBarFTexture(float w, float h, boolean useGlassTexture) {
        Pixmap pixmap = generatePixmap(w, h, true);
        Color pixColor = new Color();
        pixColor.set(0, 0, 0, 0);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(pixColor);
        pixmap.fillRectangle(4, 4, pixmap.getWidth() - 8, pixmap.getHeight() - 8);
        if (useGlassTexture) {
            Pixmap glassTexture = new Pixmap(Gdx.files.internal("Interface/glass.png"));

            int blockSize = glassTexture.getWidth();
            int width = (int) (blockSize * w);
            int height = (int) (blockSize * h);
            Pixmap glassPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

            for (int x = 0; x < (int) Math.ceil(w); x++) {
                for (int y = 0; y < (int) Math.ceil(h); y++) {
                    pixColor.set(0, 0, 0, 0);
                    glassPixmap.drawPixmap(glassTexture, x * blockSize, y * blockSize);
                }
            }
            pixmap.drawPixmap(glassPixmap,
                    4, 4, glassPixmap.getWidth() - 8, glassPixmap.getHeight() - 8,
                    4, 4, pixmap.getWidth() - 8, pixmap.getHeight() - 8);
            glassTexture.dispose();
            glassPixmap.dispose();
        }
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    public static Texture generateProgressBarPTexture(float w, float h) {
        int blockSize = 32;
        int width = (int) (blockSize * w);
        int height = (int) (blockSize * h);

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Color pixColor = new Color();

        pixColor.set(0.6f, 0.6f, 0.6f, 1f);
        pixmap.setColor(pixColor);
        pixmap.fill();

        if (w > h) {
            pixColor.set(0.4f, 0.4f, 0.4f, 1);
            pixmap.setColor(pixColor);
            for (int x = 1; x < width; x += 2) {
                pixmap.drawLine(x, 0, x, height);
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    public static Texture generateTexture(float w, float h, Color background, Color c1, Color c2, Color c3) {
        Pixmap texture = new Pixmap(Gdx.files.internal("Blocks/wood.png"));
        int blockSize = texture.getWidth();
        texture.dispose();
        int width = (int) (blockSize * w);
        int height = (int) (blockSize * h);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(background);
        pixmap.fill();
        pixmap.setColor(c1);
        pixmap.drawPixel(0, height - 1);
        pixmap.drawPixel(width - 1, 0);

        pixmap.setColor(c2);
        pixmap.drawLine(0, height - 2, 0, 0);
        pixmap.drawLine(0, 0, width - 2, 0);

        pixmap.setColor(c3);
        pixmap.drawLine(1, height - 1, width - 1, height - 1);
        pixmap.drawLine(width - 1, height - 1, width - 1, 1);
        Texture t = new Texture(pixmap);
        pixmap.dispose();
        return t;
    }

    private static Pixmap generatePixmap(float w, float h, boolean c) {
        Pixmap texture = new Pixmap(Gdx.files.internal("Blocks/stone.png"));
        Color pixCol = new Color();
        int blockSize = texture.getWidth();
        int width = (int) (blockSize * w);
        int height = (int) (blockSize * h);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        int x = (int) (Math.floor(w) + ((w - Math.floor(w) > 0) ? 1 : 0));
        int y = (int) (Math.floor(h) + ((h - Math.floor(h) > 0) ? 1 : 0));
        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                pixmap.drawPixmap(texture, 0, 0, texture.getWidth(), texture.getHeight(), xx * blockSize, yy * blockSize, blockSize, blockSize);
            }
        }
        for (int xx = 0; xx < pixmap.getWidth(); xx++) {
            for (int yy = 0; yy < pixmap.getHeight(); yy++) {
                pixCol.set(pixmap.getPixel(xx, yy));
                if ((xx == 0 && yy == pixmap.getHeight() - 1) || (xx == pixmap.getWidth() - 1 && yy == 0)) {
                    pixmap.setColor(pixCol.r * 0.7f, pixCol.g * 0.7f, pixCol.b * 0.7f, 1);
                } else if ((xx == 0) || (yy == 0)) {
                    if (c)
                        pixmap.setColor(pixCol.r, pixCol.g, pixCol.b, 1);
                    else
                        pixmap.setColor(pixCol.r * 0.4f, pixCol.g * 0.4f, pixCol.b * 0.4f, 1);
                } else if ((xx == pixmap.getWidth() - 1) || (yy == pixmap.getHeight() - 1)) {
                    if (c)
                        pixmap.setColor(pixCol.r * 0.4f, pixCol.g * 0.4f, pixCol.b * 0.4f, 1);
                    else
                        pixmap.setColor(pixCol.r, pixCol.g, pixCol.b, 1);
                } else {
                    if (c)
                        pixmap.setColor(pixCol.r * 0.8f, pixCol.g * 0.8f, pixCol.b * 0.8f, 1);
                    else
                        pixmap.setColor(pixCol.r * 0.6f, pixCol.g * 0.6f, pixCol.b * 0.6f, 1);
                }
                pixmap.drawPixel(xx, yy);
            }
        }
        texture.dispose();
        return pixmap;
    }

    public static Texture generateTexture(float w, float h, boolean c) {
        Pixmap p = generatePixmap(w, h, c);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    public static Texture generateBackground(float w, float h) {
        Pixmap texture = new Pixmap(Gdx.files.internal("Blocks/wood.png"));
        int blockSize = texture.getWidth();
        int width = (int) (blockSize * w);
        int height = (int) (blockSize * h);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);

        int x = (int) (Math.floor(w) + ((w - Math.floor(w) > 0) ? 1 : 0));
        int y = (int) (Math.floor(h) + ((h - Math.floor(h) > 0) ? 1 : 0));
        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                pixmap.drawPixmap(texture, 0, 0, texture.getWidth(), texture.getHeight(), xx * blockSize, yy * blockSize, blockSize, blockSize);
            }
        }
        pixmap.setColor(0, 0, 0, 1);
        pixmap.drawRectangle(0, 0, width, height);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fillRectangle(0, 0, 3, 3);
        pixmap.fillRectangle(0, height - 3, 3, 3);
        pixmap.fillRectangle(width - 3, 0, 3, 3);
        pixmap.fillRectangle(width - 3, height - 3, 3, 3);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.drawPixel(1, 2);
        pixmap.drawPixel(2, 1);
        pixmap.drawPixel(width - 2, 2);
        pixmap.drawPixel(width - 3, 1);
        pixmap.drawPixel(1, height - 3);
        pixmap.drawPixel(2, height - 2);
        pixmap.drawPixel(width - 3, height - 2);
        pixmap.drawPixel(width - 2, height - 3);

        Texture t = new Texture(pixmap);
        pixmap.dispose();
        texture.dispose();
        return t;

    }
}
