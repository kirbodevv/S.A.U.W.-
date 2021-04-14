package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.graphic.Graphic;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.*;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;

public class CraftingInterface extends Interface {
    Button craft;
    BitmapFont craftName;
    BitmapFont BF;
    Slot c0;
    Slot c1;
    Slot c2;
    Slot c3;
    Slot c4;
    Slot c5;
    float txtX;
    float txtY;
    Button previos;
    Button next;
    int currentCraft = -1;
    int currentTab = 0;
    Texture background1, background2, background3, background4;
    BitmapFont.TextBounds TB;
    public CraftingInterface() {
        super(InterfaceSizes.FULL, "CRAFTING_INTERFACE");
        setHeaderText(LANGUAGES.getString("crafting")).isBlockInterface(false);
        background1 = TEXTURES.generateTexture(7.5f, (Graphic.SCREEN_HEIGHT - Graphic.SCREEN_WIDTH / 16 * 2) / (Graphic.SCREEN_WIDTH / 16), false);
        background2 = TEXTURES.generateTexture(6f, (Graphic.SCREEN_HEIGHT - Graphic.SCREEN_WIDTH / 16 * 2) / (Graphic.SCREEN_WIDTH / 16), false);
        BF = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        BF.setColor(Color.BLACK);
        BF.setScale(INTERFACE_CAMERA.W / 32 / BF.getCapHeight());
        craftName = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        craftName.setColor(Color.BLACK);
        craftName.setScale(Graphic.SCREEN_WIDTH / 32 / 2 / craftName.getCapHeight());
        TB = BF.getBounds(LANGUAGES.getString("craftList"));
        craft = new Button("CRAFTING_INTERFACE_CRAFT_BUTTON", (int) (Graphic.SCREEN_WIDTH / 16 * 9 + Graphic.SCREEN_WIDTH / 32), (int) Graphic.SCREEN_WIDTH / 16, (int) Graphic.SCREEN_WIDTH / 16 * 5, (int) Graphic.SCREEN_WIDTH / 16);
        craft.setText(LANGUAGES.getString("craft"));
        craft.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                if (currentCraft != -1) {
                    for (int i = 0; i < CRAFTING.crafts.get(currentCraft).ingr.length; i++) {
                        int IC = PLAYER.getCountOfItems(CRAFTING.crafts.get(currentCraft).ingr[i][0]);
                        if (IC < CRAFTING.crafts.get(currentCraft).ingr[i][1]) {
                            return;
                        }
                    }
                    for (int i = 0; i < CRAFTING.crafts.get(currentCraft).ingr.length; i++) {
                        PLAYER.deleteItems(CRAFTING.crafts.get(currentCraft).ingr[i][0], CRAFTING.crafts.get(currentCraft).ingr[i][1]);
                    }
                    PLAYER.addItem(CRAFTING.crafts.get(currentCraft).result[0], CRAFTING.crafts.get(currentCraft).result[1], CRAFTING.crafts.get(currentCraft).result[2]);
                }
            }
        });
        buttons.add(craft);
        int xx = (int) Graphic.SCREEN_WIDTH / 16 * 9 + (int) Graphic.SCREEN_WIDTH / 32 + (int) Graphic.SCREEN_WIDTH / 16;
        int yy = (int) Graphic.SCREEN_WIDTH / 32 * 5;
        int ww = (int) Graphic.SCREEN_WIDTH / 16;
        c2 = new Slot("c2", xx + ww * 2, yy + ww, ww, ww, TEXTURES.selected_slot);
        c1 = new Slot("c1", xx + ww, yy + ww, ww, ww, TEXTURES.selected_slot);
        c0 = new Slot("c0", xx, yy + ww, ww, ww, TEXTURES.selected_slot);
        c5 = new Slot("c5", xx + ww * 2, yy, ww, ww, TEXTURES.selected_slot);
        c4 = new Slot("c4", xx + ww, yy, ww, ww, TEXTURES.selected_slot);
        c3 = new Slot("c3", xx, yy, ww, ww, TEXTURES.selected_slot);
        slots.add(c0);
        slots.add(c1);
        slots.add(c2);
        slots.add(c3);
        slots.add(c4);
        slots.add(c5);
        float xxx = Graphic.SCREEN_WIDTH / 16;
        float yyy = Graphic.SCREEN_WIDTH / 32;
        float www = Graphic.SCREEN_WIDTH / 16 * 8 - Graphic.SCREEN_WIDTH / 32;
        float hhh = Graphic.SCREEN_HEIGHT - Graphic.SCREEN_WIDTH / 16 * 2;
        previos = new Button("PREVIOUS_CRAFT_LIST_BUTTON", (int) (xxx + Graphic.SCREEN_WIDTH / 32), (int) (yyy + hhh - hhh / 7 - Graphic.SCREEN_WIDTH / 64), (int) hhh / 7, (int) hhh / 7, TEXTURES.button_left_0, TEXTURES.button_left_1);
        next = new Button("NEXT_CRAFT_LIST_BUTTON", (int) (xxx + www - Graphic.SCREEN_WIDTH / 32 - hhh / 7), (int) (yyy + hhh - hhh / 7 - Graphic.SCREEN_WIDTH / 64), (int) hhh / 7, (int) hhh / 7, TEXTURES.button_right_0, TEXTURES.button_right_1);
        int dist = next.X - (previos.X + previos.width);
        background3 = TEXTURES.generateTexture(dist / (Graphic.SCREEN_WIDTH / 16), (hhh / 7) / (Graphic.SCREEN_WIDTH / 16), true);
        background4 = TEXTURES.generateTexture(2, 2, true);
        buttons.add(previos);
        buttons.add(next);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 6; x++) {
                final int num = y * 6 + x;
                String id = "CRAFTING_INTERFACE_CRAFT_" + num;
                Button b = new Button(id, (int) (xxx + Graphic.SCREEN_WIDTH / 32 + hhh / 7 * x + Graphic.SCREEN_WIDTH / 64), (int) (yyy + Graphic.SCREEN_WIDTH / 32 + hhh / 7 * (4 - y)), (int) (hhh / 7), (int) (hhh / 7));
                b.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        currentCraft = currentTab * 30 + num;
                    }
                });
                buttons.add(b);

            }
        }
    }

    @Override
    public void tick() {
        int temp = 0;
        for (int i = currentTab * 30; i < currentTab + 1 * 30; i++) {
            if (getButton("CRAFTING_INTERFACE_CRAFT_" + temp) != null) {
                if (i < CRAFTING.crafts.size()) {
                    getButton("CRAFTING_INTERFACE_CRAFT_" + temp).hide(false);
                } else {
                    getButton("CRAFTING_INTERFACE_CRAFT_" + temp).hide(true);
                }
            }
            temp += 1;
        }
    }

    @Override
    public void preRender() {
        BATCH.draw(background2, SCREEN_WIDTH / 16 * 9, SCREEN_WIDTH / 32, SCREEN_WIDTH / 16 * 6, SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2);
        BATCH.draw(background1, SCREEN_WIDTH / 16, SCREEN_WIDTH / 32, SCREEN_WIDTH / 16 * 8 - SCREEN_WIDTH / 32, SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2);
        BATCH.draw(background3, previos.X + previos.width, previos.Y, next.X - (previos.X + previos.width), previos.height);
        BATCH.draw(background4, SCREEN_WIDTH / 32 * 22, SCREEN_WIDTH / 32 * 10, SCREEN_WIDTH / 16 * 2, SCREEN_WIDTH / 16 * 2);
    }
    @Override
    public void render() {
        for (int i = currentTab * 30; i < CRAFTING.crafts.size(); i++) {
            int x = getButton("CRAFTING_INTERFACE_CRAFT_" + i).X;
            int y = getButton("CRAFTING_INTERFACE_CRAFT_" + i).Y;
            int w = getButton("CRAFTING_INTERFACE_CRAFT_" + i).width;
            int h = getButton("CRAFTING_INTERFACE_CRAFT_" + i).height;
            BATCH.draw(ITEMS.getTextureById(CRAFTING.crafts.get(i).result[0]), x + w / 8, y + w / 8, w - w / 4, w - w / 4);
            if (currentCraft == i) {
                for (int j = 0; j < CRAFTING.crafts.get(i).ingr.length; j++) {
                    int xx = getSlot("c" + j).X;
                    int yy = getSlot("c" + j).Y;
                    int ww = getSlot("c" + j).width;
                    BATCH.draw(ITEMS.getTextureById(CRAFTING.crafts.get(i).ingr[j][0]), xx + ww / 8, yy + ww / 8, ww - ww / 4, ww - ww / 4);
                    if (j == 0)
                        c0.IC.drawMultiLine(BATCH, PLAYER.getCountOfItems(CRAFTING.crafts.get(i).ingr[j][0]) + "/" + CRAFTING.crafts.get(i).ingr[j][1], c0.X, c0.Y + c0.IC.getCapHeight(), c0.width, BitmapFont.HAlignment.RIGHT);
                    if (j == 1)
                        c1.IC.drawMultiLine(BATCH, PLAYER.getCountOfItems(CRAFTING.crafts.get(i).ingr[j][0]) + "/" + CRAFTING.crafts.get(i).ingr[j][1], c1.X, c1.Y + c1.IC.getCapHeight(), c1.width, BitmapFont.HAlignment.RIGHT);
                    if (j == 2)
                        c2.IC.drawMultiLine(BATCH, PLAYER.getCountOfItems(CRAFTING.crafts.get(i).ingr[j][0]) + "/" + CRAFTING.crafts.get(i).ingr[j][1], c2.X, c2.Y + c2.IC.getCapHeight(), c2.width, BitmapFont.HAlignment.RIGHT);
                    if (j == 3)
                        c3.IC.drawMultiLine(BATCH, PLAYER.getCountOfItems(CRAFTING.crafts.get(i).ingr[j][0]) + "/" + CRAFTING.crafts.get(i).ingr[j][1], c3.X, c3.Y + c3.IC.getCapHeight(), c3.width, BitmapFont.HAlignment.RIGHT);
                    if (j == 4)
                        c4.IC.drawMultiLine(BATCH, PLAYER.getCountOfItems(CRAFTING.crafts.get(i).ingr[j][0]) + "/" + CRAFTING.crafts.get(i).ingr[j][1], c4.X, c4.Y + c4.IC.getCapHeight(), c4.width, BitmapFont.HAlignment.RIGHT);
                    if (j == 5)
                        c5.IC.drawMultiLine(BATCH, PLAYER.getCountOfItems(CRAFTING.crafts.get(i).ingr[j][0]) + "/" + CRAFTING.crafts.get(i).ingr[j][1], c5.X, c5.Y + c5.IC.getCapHeight(), c5.width, BitmapFont.HAlignment.RIGHT);
                }
                BATCH.draw(ITEMS.getTextureById(CRAFTING.crafts.get(i).result[0]), SCREEN_WIDTH / 32 * 23, SCREEN_WIDTH / 32 * 11, SCREEN_WIDTH / 16, SCREEN_WIDTH / 16);
                craftName.drawMultiLine(BATCH, ITEMS.getNameById(CRAFTING.crafts.get(i).result[0]), SCREEN_WIDTH / 32 * 22, SCREEN_WIDTH / 16 * 5 - SCREEN_WIDTH / 128, SCREEN_WIDTH / 16 * 2, BitmapFont.HAlignment.CENTER);
            }
        }
        BF.draw(BATCH, LANGUAGES.getString("craftList"), previos.X + previos.width + (next.X - (previos.X + previos.width) - TB.width) / 2, previos.Y + previos.height - (previos.height - TB.height) / 2);
    }
}
