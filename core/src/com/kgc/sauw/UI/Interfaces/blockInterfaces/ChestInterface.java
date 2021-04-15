package com.kgc.sauw.UI.Interfaces.blockInterfaces;

import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.environment.Environment;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class ChestInterface extends Interface {

    public ChestInterface() {
        super(InterfaceSizes.STANDART,"CHEST_INTERFACE");
        createInventory().setHeaderText(LANGUAGES.getString("chest")).isBlockInterface(true);
        float x = SCREEN_HEIGHT / (SCREEN_WIDTH / 16.0f) / 2 * (SCREEN_WIDTH / 16.0f);
        float y = SCREEN_WIDTH / 16.0f;
        float width = SCREEN_WIDTH / 16 * (SCREEN_HEIGHT / (SCREEN_WIDTH / 16.0f) - 2);
        float heigth = SCREEN_WIDTH / 16 * (SCREEN_HEIGHT / (SCREEN_WIDTH / 16.0f) - 2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                Slot slot = new Slot("chestSlot_" + (i * 8 + j), (int) (x + ((width - SCREEN_WIDTH / 24.0f * 8) / 2) + (SCREEN_WIDTH / 24 * j)), (int) (y + (SCREEN_WIDTH / 24 * 3 + SCREEN_WIDTH / 16 + SCREEN_WIDTH / 32) + SCREEN_WIDTH / 24 * i + SCREEN_WIDTH / 64), (int)SCREEN_WIDTH / 24, (int)SCREEN_WIDTH / 24, TEXTURES.selected_slot);
                slots.add(slot);
            }
        }
    }
}
