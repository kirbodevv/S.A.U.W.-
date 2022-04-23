package com.kgc.sauw.core.gui;

import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.gui.elements.*;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.utils.Camera2D;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

public class InventoryFragment extends Fragment {
    public Button previousTabInv;
    public Button nextTabInv;
    public int currentItemInv = -1;
    public int currentTabInv = 0;


    private final Slot[] slots = new Slot[30];

    public InventoryFragment() {
        super(Orientation.HORIZONTAL);
        setGravity(Layout.Gravity.LEFT);
        setId("InventoryFragment");
    }

    @Override
    public void hide(boolean b) {
        super.hide(b);
        if (b) currentItemInv = -1;
    }

    @Override
    public void tick(Camera2D cam) {
        for (int j = 0; j < PLAYER.inventory.containers.size(); j++) {
            if (j >= currentTabInv * 30 && j < currentTabInv * 30 + 30) {
                Slot slot = slots[j - currentTabInv * 30];
                slot.inventorySlot = j;
                slot.setContainer(PLAYER.inventory.containers.get(j));
            }
        }
        super.tick(cam);
    }

    public void createInventory(Interface interface_) {
        interface_.mainLayout.setOrientation(Orientation.HORIZONTAL);
        interface_.mainLayout.setGravity(Layout.Gravity.LEFT);
        Layout inventoryLayout = new Layout(Layout.Orientation.VERTICAL);
        Layout switchTabLayout = new Layout(Layout.Orientation.HORIZONTAL);
        Layout slotsLayout = new Layout(Layout.Orientation.VERTICAL);
        Layout optionalLayout = new Layout(Layout.Orientation.VERTICAL);

        inventoryLayout.setGravity(Layout.Gravity.TOP);
        inventoryLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        inventoryLayout.setSizeInBlocks(7.5f, 7f);
        inventoryLayout.setStandardBackground(false);
        inventoryLayout.setTranslationX(1);
        inventoryLayout.setId("inventoryLayout");

        switchTabLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        switchTabLayout.setGravity(Layout.Gravity.LEFT);
        switchTabLayout.setTranslationY(-0.25f);
        switchTabLayout.setId("switchTabLayout");

        slotsLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        slotsLayout.setGravity(Layout.Gravity.TOP);
        slotsLayout.setTranslationY(-0.25f);
        slotsLayout.setId("slotsLayout");

        optionalLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        optionalLayout.setSizeInBlocks(6f, 7f);
        optionalLayout.setGravity(Layout.Gravity.TOP);
        optionalLayout.setStandardBackground(false);
        optionalLayout.setTranslationX(0.5f);
        optionalLayout.setId("optionalLayout");

        previousTabInv = new Button("PREVIOUS_INVENTORY_TAB_BUTTON", 0, 0, 0, 0);
        previousTabInv.setIcon(Resource.getTexture("interface/button_left_0.png"));
        nextTabInv = new Button("NEXT_INVENTORY_TAB_BUTTON", 0, 0, 0, 0);
        nextTabInv.setIcon(Resource.getTexture("interface/button_right_0.png"));
        previousTabInv.setSizeInBlocks(1, 1);
        nextTabInv.setSizeInBlocks(1, 1);
        TextView backpackTextView = new TextView();
        backpackTextView.setSizeInBlocks(5, 1);
        backpackTextView.setText(Languages.getString("sauw.inventory_fragment.backpack"));
        backpackTextView.setId("BackpackText");

        switchTabLayout.addElements(previousTabInv, backpackTextView, nextTabInv);
        inventoryLayout.addElements(switchTabLayout, slotsLayout);

        for (int y = 0; y < 5; y++) {
            Layout l = new Layout(Layout.Orientation.HORIZONTAL);
            l.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
            l.setGravity(Layout.Gravity.LEFT);
            l.setId("InventoryLayout_" + y);
            for (int x = 0; x < 6; x++) {
                final int num = y * 6 + x;
                String id = "InventorySlot_" + num;
                Slot s = new Slot(id, interface_);
                slots[num] = s;
                s.setSizeInBlocks(1, 1);
                s.isInventorySlot = true;
                s.setSF(new Slot.SlotFunctions() {

                    @Override
                    public boolean isValid(Container container, String fromSlotWithId) {
                        return false;
                    }

                    @Override
                    public void onClick() {
                        currentItemInv = currentTabInv * 30 + num;
                    }

                    @Override
                    public boolean possibleToDrag() {
                        return true;
                    }

                    @Override
                    public void onItemSwapping(Container fromContainer) {
                        if (!PLAYER.inventory.isInventoryContainer(fromContainer)) {
                            PLAYER.inventory.addItem(fromContainer);
                            fromContainer.clear();
                        }
                    }
                });
                l.addElements(s);
            }
            slotsLayout.addElements(l);
        }
        addElements(inventoryLayout, optionalLayout);
        nextTabInv.addEventListener(() -> {
            if (PLAYER.inventory.containers.size() > (currentTabInv + 1) * 30) {
                currentTabInv++;
            }
        });
        previousTabInv.addEventListener(() -> {
            if (currentTabInv - 1 >= 0) {
                currentTabInv--;
            }
        });
    }
}
