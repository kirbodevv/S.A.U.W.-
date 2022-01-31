package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.registry.Object;
import com.kgc.sauw.core.world.generator.WorldGeneratorUtils;
import com.kgc.utils.OpenSimplexNoise;

@Object(package_ = "sauw", id = "test")
public class TestInterface extends Interface {
    OpenSimplexNoise openSimplexNoise = new OpenSimplexNoise();

    public TestInterface() {

        actionBar.setText("TOP SECRET");

        Image image = new Image();
        image.setSizeInBlocks(8, 8);

        Pixmap p = new Pixmap(300, 300, Pixmap.Format.RGBA8888);
        float scale = .05f;
        Color water = new Color(0x0000FFFF);
        Color sand = new Color(0xFFFF00FF);
        Color plains = new Color(0x00FF00FF);
        Color forest = new Color(0x00AA00FF);
        for (int x = 0; x < p.getWidth(); x++) {
            for (int y = 0; y < p.getHeight(); y++) {
                float val = (float) WorldGeneratorUtils.sumOctave(openSimplexNoise, 16, x, y, .1, scale, 0, 1);
                if (val < 0.4f) p.setColor(water);
                else if (val < 0.55f) p.setColor(sand);
                else if (val < 0.7f) p.setColor(plains);
                else p.setColor(forest);
                p.drawPixel(x, y);
            }
        }
        Texture t = new Texture(p);
        p.dispose();
        image.setImg(t);
        mainLayout.addElements(image);
    }

    @Override
    public void tick() {

    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void preRender() {

    }

    @Override
    public void postRender() {
    }
}
