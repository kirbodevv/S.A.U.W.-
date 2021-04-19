package com.kgc.sauw.UI.Interfaces.blockInterfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.resource.Textures;

import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class ChestInterface extends Interface {

    Texture bg;
    float size;

    public ChestInterface() {
        super(InterfaceSizes.FULL, "CHEST_INTERFACE");
        createInventory().setHeaderText(LANGUAGES.getString("chest")).isBlockInterface(true);

        size = (int) (BLOCK_SIZE * 7.5f - BLOCK_SIZE * 2) / 6f;

        int xCount = (int) (BLOCK_SIZE * 6f / size);
        int yCount = 24 / xCount;

        float padding = (BLOCK_SIZE * 6 - xCount * size) / 2f;

        int startX = (int) (BLOCK_SIZE * 9 + padding);
        int startY = (int) (previousTabInv.Y - BLOCK_SIZE / 2 - yCount * size);

        bg = Textures.generateTexture(5f, size / BLOCK_SIZE, true);

        int counter = 0;
        for (int i = 0; i < yCount; i++) {
            for (int j = 0; j < xCount; j++) {
                if (counter < 24) {
                    Slot slot = new Slot("chestSlot_" + (i * 8 + j), (int) (startX + size * j), (int) (startY + size * i), (int) (size), (int) (size));
                    slots.add(slot);
                    counter++;
                }
            }
        }
    }

    @Override
    public void postRender() {
        BATCH.draw(bg, BLOCK_SIZE * 9.5f, previousTabInv.Y, BLOCK_SIZE * 5, size);
        text.drawMultiLine(BATCH, LANGUAGES.getString("chest"), BLOCK_SIZE * 9.5f, previousTabInv.Y + previousTabInv.height - ((previousTabInv.height - text.getCapHeight()) / 2), BLOCK_SIZE * 5, BitmapFont.HAlignment.CENTER);
        //text.drawMultiLine(BLOCK_SIZE * 9.5f, setHeaderText(LANGUAGES.getString("chest"), previousTabInv.Y)
    }
}