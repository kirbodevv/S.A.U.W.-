package com.kgc.sauw.ui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.ui.Interface;
import com.kgc.sauw.ui.elements.*;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.CRAFTING;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class CraftingInterface extends Interface {
    Button craft;
    Text craftName;
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
    Image itemIcon;

    public CraftingInterface() {
        super("CRAFTING_INTERFACE");

        MainLayout.setOrientation(Layout.Orientation.HORIZONTAL);
        MainLayout.setGravity(Layout.Gravity.LEFT);

        createFromXml(Gdx.files.internal("xml/CraftingInterface.xml").readString());
        getElement("craftsLayout").setSize(BLOCK_SIZE * 7.5f, SCREEN_HEIGHT - BLOCK_SIZE * 2f);
        ((Layout) getElement("craftsLayout")).generateBackground(false);
        getElement("craftInfoLayout").setSize(BLOCK_SIZE * 6f, SCREEN_HEIGHT - BLOCK_SIZE * 2);
        ((Layout) getElement("craftInfoLayout")).generateBackground(false);

        getElement("craftIconLayout").setSize(BLOCK_SIZE * 2, BLOCK_SIZE * 2);
        ((Layout) getElement("craftIconLayout")).generateBackground(true);

        ((Layout) getElement("craftIconLayout_0")).setSize(0, BLOCK_SIZE * 2);

        itemIcon = (Image) getElement("craftIcon");
        craftName = (Text) getElement("craftName");
        craft = (Button) getElement("craftButton");
        craft.setText(LANGUAGES.getString("craft"));
        craft.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                if (currentCraft != -1) {
                    for (int i = 0; i < CRAFTING.crafts.get(currentCraft).ingr.length; i++) {
                        int IC = PLAYER.Inventory.getCountOfItems(CRAFTING.crafts.get(currentCraft).ingr[i][0]);
                        if (IC < CRAFTING.crafts.get(currentCraft).ingr[i][1]) {
                            return;
                        }
                    }
                    for (int i = 0; i < CRAFTING.crafts.get(currentCraft).ingr.length; i++) {
                        PLAYER.Inventory.deleteItems(CRAFTING.crafts.get(currentCraft).ingr[i][0], CRAFTING.crafts.get(currentCraft).ingr[i][1]);
                    }
                    PLAYER.Inventory.addItem(CRAFTING.crafts.get(currentCraft).result[0], CRAFTING.crafts.get(currentCraft).result[1]);
                }
            }
        });

        for (int i = 0; i < 6; i++) {
            ((Slot) getElement("craftItemSlot_" + i)).setSF(new Slot.SlotFunctions() {
                @Override
                public boolean isValid(int id, int count, int data, String FromSlotWithId) {
                    return false;
                }

                @Override
                public void onClick() {

                }

                @Override
                public boolean possibleToDrag() {
                    return false;
                }
            });
        }

        Layout CraftsListLayout = (Layout) getElement("CraftsListLayout");
        for (int y = 0; y < 5; y++) {
            Layout l = new Layout(Layout.Orientation.HORIZONTAL);
            l.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
            l.setGravity(Layout.Gravity.LEFT);
            l.setID("CraftsListLayout_" + y);
            for (int x = 0; x < 6; x++) {
                final int num = y * 6 + x;
                String id = "Craft_" + num;
                Button button = new Button(id, 0, 0, BLOCK_SIZE, BLOCK_SIZE);
                button.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        currentCraft = currentTab * 30 + num;
                        craftName.setText(ITEMS.getNameById(CRAFTING.crafts.get(currentCraft).result[0]));
                        itemIcon.setImg(ITEMS.getTextureById(CRAFTING.crafts.get(currentCraft).result[0]));
                    }
                });
                l.addElements(button);
            }
            CraftsListLayout.addElements(l);
        }
        updateElementsList();
    }

    @Override
    public void tick() {
        int temp = 0;
        for (int i = currentTab * 30; i < currentTab + 30; i++) {
            if (getElement("Craft_" + temp) != null) {
                if (i < CRAFTING.crafts.size()) {
                    getElement("Craft_" + temp).hide(false);
                } else {
                    getElement("Craft_" + temp).hide(true);
                }
            }
            temp += 1;
        }
    }

    @Override
    public void postRender() {
        for (int i = currentTab * 30; i < CRAFTING.crafts.size(); i++) {
            float x = getElement("Craft_" + i).X;
            float y = getElement("Craft_" + i).Y;
            float w = getElement("Craft_" + i).width;
            BATCH.draw(ITEMS.getTextureById(CRAFTING.crafts.get(i).result[0]), x + w / 8, y + w / 8, w - w / 4, w - w / 4);
        }
    }
}
