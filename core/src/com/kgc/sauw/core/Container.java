package com.kgc.sauw.core;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.core.environment.item.Items;

public class Container implements ExtraData {
    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("id", id);
        buffer.put("count", count);
        buffer.put("damage", damage);
        return buffer.toBytes();
    }

    @Override
    public void readBytes(byte[] bytes, int begin, int end) {
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes);
        id = buffer.getInt("id");
        count = buffer.getInt("count");
        damage = buffer.getInt("damage");
    }

    public int id, count, damage;
    public DataBuffer bdbData;
    public String containerId;

    public void clear() {
        this.id = 0;
        this.count = 0;
        this.damage = 0;
    }

    public Container(String containerId) {
        clear();
        this.containerId = containerId;
    }

    public Container() {
        clear();
    }

    public Container(Container container) {
        setItemFromContainer(container);
    }

    public void decreaseItem() {
        setItem(id, count - 1, damage);
    }

    public void setItem(int id, int count, int damage) {
        this.id = id;
        this.count = count;
        this.damage = damage;
        if (count <= 0 || (Items.getItemById(id).getItemConfiguration().maxDamage != 0 &&
                damage >= Items.getItemById(id).getItemConfiguration().maxDamage)) {
            clear();
        }
    }

    public void setItemFromContainer(Container container) {
        setItem(container.id, container.count, container.damage);
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getDamage() {
        return damage;
    }

    public DataBuffer getBdbData() {
        if (bdbData == null) bdbData = new DataBuffer();
        return bdbData;
    }

    public boolean hasBdbData() {
        return bdbData != null;
    }
}