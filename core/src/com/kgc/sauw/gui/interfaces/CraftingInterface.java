package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.gui.Interface;
import com.kgc.sauw.gui.elements.*;

import static com.kgc.sauw.entity.EntityManager.PLAYER;
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

        mainLayout.setOrientation(Layout.Orientation.HORIZONTAL);
        mainLayout.setGravity(Layout.Gravity.LEFT);

        createFromXml(Gdx.files.internal("xml/CraftingInterface.xml"));
        getElement("craftsLayout").setSizeInBlocks(7.5f, 7f);
        ((Layout) getElement("craftsLayout")).generateBackground(false);
        getElement("craftInfoLayout").setSizeInBlocks(6f, 7f);
        ((Layout) getElement("craftInfoLayout")).generateBackground(false);

        getElement("craftIconLayout").setSizeInBlocks(2f, 2f);
        ((Layout) getElement("craftIconLayout")).generateBackground(true);

        ((Layout) getElement("craftIconLayout_0")).setSizeInBlocks(0, 2);

        itemIcon = (Image) getElement("craftIcon");
        craftName = (Text) getElement("craftName");
        craft = (Button) getElement("craftButton");
        craft.setText(LANGUAGES.getString("craft"));
        craft.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                if (currentCraft != -1) {
                    for (int i = 0; i < CRAFTING.crafts.get(currentCraft).ingr.length; i++) {
                        int IC = PLAYER.inventory.getCountOfItems(CRAFTING.crafts.get(currentCraft).ingr[i][0]);
                        if (IC < CRAFTING.crafts.get(currentCraft).ingr[i][1]) {
                            return;
                        }
                    }
                    for (int i = 0; i < CRAFTING.crafts.get(currentCraft).ingr.length; i++) {
                        PLAYER.inventory.deleteItems(CRAFTING.crafts.get(currentCraft).ingr[i][0], CRAFTING.crafts.get(currentCraft).ingr[i][1]);
                    }
                    PLAYER.inventory.addItem(CRAFTING.crafts.get(currentCraft).result[0], CRAFTING.crafts.get(currentCraft).result[1]);
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
                Button button = new Button(id, 0, 0, 0, 0);
                button.setSizeInBlocks(1, 1);
                button.addEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        currentCraft = currentTab * 30 + num;
                        craftName.setText(ITEMS.getItemById(CRAFTING.crafts.get(currentCraft).result[0]).getDefaultName());
                        itemIcon.setImg(ITEMS.getItemById(CRAFTING.crafts.get(currentCraft).result[0]).getDefaultTexture());
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
            float x = getElement("Craft_" + i).x;
            float y = getElement("Craft_" + i).y;
            float w = getElement("Craft_" + i).width;
            BATCH.draw(ITEMS.getItemById(CRAFTING.crafts.get(i).result[0]).getDefaultTexture(), x + w / 8, y + w / 8, w - w / 4, w - w / 4);
        }
    }
}
