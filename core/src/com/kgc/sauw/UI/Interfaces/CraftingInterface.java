package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.graphic.Graphic;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.CRAFTING;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

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
        super("CRAFTING_INTERFACE");
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
        Elements.add(craft);
        int xx = (int) Graphic.SCREEN_WIDTH / 16 * 9 + (int) Graphic.SCREEN_WIDTH / 32 + (int) Graphic.SCREEN_WIDTH / 16;
        int yy = (int) Graphic.SCREEN_WIDTH / 32 * 5;
        int ww = (int) Graphic.SCREEN_WIDTH / 16;
        c2 = new Slot("c2", this, xx + ww * 2, yy + ww, ww, ww);
        c1 = new Slot("c1", this, xx + ww, yy + ww, ww, ww);
        c0 = new Slot("c0", this, xx, yy + ww, ww, ww);
        c5 = new Slot("c5", this, xx + ww * 2, yy, ww, ww);
        c4 = new Slot("c4", this, xx + ww, yy, ww, ww);
        c3 = new Slot("c3", this, xx, yy, ww, ww);
        Elements.add(c0);
        Elements.add(c1);
        Elements.add(c2);
        Elements.add(c3);
        Elements.add(c4);
        Elements.add(c5);
        float xxx = Graphic.SCREEN_WIDTH / 16;
        float yyy = Graphic.SCREEN_WIDTH / 32;
        float www = Graphic.SCREEN_WIDTH / 16 * 8 - Graphic.SCREEN_WIDTH / 32;
        float hhh = Graphic.SCREEN_HEIGHT - Graphic.SCREEN_WIDTH / 16 * 2;
        previos = new Button("PREVIOUS_CRAFT_LIST_BUTTON", (int) (xxx + Graphic.SCREEN_WIDTH / 32), (int) (yyy + hhh - hhh / 7 - Graphic.SCREEN_WIDTH / 64), (int) hhh / 7, (int) hhh / 7, TEXTURES.button_left_0, TEXTURES.button_left_1);
        next = new Button("NEXT_CRAFT_LIST_BUTTON", (int) (xxx + www - Graphic.SCREEN_WIDTH / 32 - hhh / 7), (int) (yyy + hhh - hhh / 7 - Graphic.SCREEN_WIDTH / 64), (int) hhh / 7, (int) hhh / 7, TEXTURES.button_right_0, TEXTURES.button_right_1);
        float dist = next.X - (previos.X + previos.width);
        background3 = TEXTURES.generateTexture(dist / (Graphic.SCREEN_WIDTH / 16), (hhh / 7) / (Graphic.SCREEN_WIDTH / 16), true);
        background4 = TEXTURES.generateTexture(2, 2, true);
        Elements.add(previos);
        Elements.add(next);
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
                Elements.add(b);

            }
        }
        updateElementsList();
    }

    @Override
    public void tick() {
        int temp = 0;
        for (int i = currentTab * 30; i < currentTab + 1 * 30; i++) {
            if (getElement("CRAFTING_INTERFACE_CRAFT_" + temp) != null) {
                if (i < CRAFTING.crafts.size()) {
                    getElement("CRAFTING_INTERFACE_CRAFT_" + temp).hide(false);
                } else {
                    getElement("CRAFTING_INTERFACE_CRAFT_" + temp).hide(true);
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
    public void postRender() {
        for (int i = currentTab * 30; i < CRAFTING.crafts.size(); i++) {
            float x = getElement("CRAFTING_INTERFACE_CRAFT_" + i).X;
            float y = getElement("CRAFTING_INTERFACE_CRAFT_" + i).Y;
            float w = getElement("CRAFTING_INTERFACE_CRAFT_" + i).width;
            float h = getElement("CRAFTING_INTERFACE_CRAFT_" + i).height;
            BATCH.draw(ITEMS.getTextureById(CRAFTING.crafts.get(i).result[0]), x + w / 8, y + w / 8, w - w / 4, w - w / 4);
            if (currentCraft == i) {
                for (int j = 0; j < CRAFTING.crafts.get(i).ingr.length; j++) {
                    float xx = getSlot("c" + j).X;
                    float yy = getSlot("c" + j).Y;
                    float ww = getSlot("c" + j).width;
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
