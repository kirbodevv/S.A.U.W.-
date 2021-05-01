package com.kgc.sauw;

import com.kgc.sauw.utils.ID;

import java.util.ArrayList;

import static com.kgc.sauw.environment.Environment.ITEMS;

public class Inventory {
    public ArrayList<InventoryContainer> containers;
    private final ArrayList<InventoryContainer> slotsToRemove = new ArrayList<>();

    public Inventory(int count){
        containers = new ArrayList<>(count);
    }
    public Inventory(){
        this(0);
    }

    public void deleteItems() {
        ArrayList<InventoryContainer> toBeRemoved = new ArrayList<>(containers);
        containers.removeAll(toBeRemoved);
    }

    public void deleteItems(int id) {
        ArrayList<InventoryContainer> toBeRemoved = new ArrayList<>();
        for (InventoryContainer slot : containers) {
            if (slot.id == id)
                toBeRemoved.add(slot);
        }
        containers.removeAll(toBeRemoved);
    }

    public void deleteItems(int id, int count) {
        int r = count;
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).id == id) {
                if (containers.get(i).count > r) {
                    containers.get(i).count = containers.get(i).count - r;
                    r -= r;
                } else {
                    r -= containers.get(i).count;
                    clearSlot(i);
                }
            }
        }
    }

    public boolean addItem(String stringId, int count) {
        return addItem(ID.get(stringId), count);
    }

    public boolean addItem(int id, int count) {
        for (InventoryContainer inventoryContainer : containers) {
            if (inventoryContainer.id == id && inventoryContainer.count < ITEMS.getItemById(inventoryContainer.id).getItemConfiguration().maxCount) {
                int canAdd = ITEMS.getItemById(inventoryContainer.id).getItemConfiguration().maxCount - inventoryContainer.count;
                if (canAdd > count) {
                    inventoryContainer.count = inventoryContainer.count + count;
                    count -= count;
                } else {
                    inventoryContainer.count = inventoryContainer.count + canAdd;
                    count -= canAdd;
                }
            }
            if (count == 0) {
                return true;
            }
        }
        if (count > 0) {
            int slotsCount = (count % ITEMS.getItemById(id).getItemConfiguration().maxCount) + 1;
            for (int i = 0; i < slotsCount; i++)
                containers.add(new InventoryContainer());
        }
        for (InventoryContainer inventoryContainer : containers) {
            if (inventoryContainer.id == 0) {
                int canAdd = ITEMS.getItemById(id).getItemConfiguration().maxCount;
                inventoryContainer.id = id;
                if (canAdd > count) {
                    inventoryContainer.count = inventoryContainer.count + count;
                    count -= count;
                } else {
                    inventoryContainer.count = inventoryContainer.count + canAdd;
                    count -= canAdd;
                }
            }
            if (count == 0) {
                return true;
            }
        }
        return false;
    }

    public void clearSlot(int slot) {
        containers.remove(slot);
    }

    public int getCountOfItems(int id) {
        int count = 0;
        for (InventoryContainer inventoryContainer : containers) {
            if (inventoryContainer.id == id) {
                count += inventoryContainer.count;
            }
        }
        return count;
    }

    public float getItemsWeight() {
        float itemsWeight = 0f;
        for (InventoryContainer slot : containers) {
            itemsWeight += slot.count * ITEMS.getItemById(slot.id).getItemConfiguration().weight;
        }
        return itemsWeight;
    }

    public void removeItemsIfNeed() {
        for (InventoryContainer inventoryContainer : containers) {
            if (inventoryContainer.data >= ITEMS.getItemById(inventoryContainer.id).getItemConfiguration().maxData && ITEMS.getItemById(inventoryContainer.id).getItemConfiguration().maxData != 0) {
                slotsToRemove.add(inventoryContainer);
            }
            if (inventoryContainer.count <= 0) {
                slotsToRemove.add(inventoryContainer);
            }
        }
        if (slotsToRemove.size() > 0) {
            containers.removeAll(slotsToRemove);
            slotsToRemove.clear();
        }
    }
}