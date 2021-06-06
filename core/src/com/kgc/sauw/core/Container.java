package com.kgc.sauw.core;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;

import java.awt.*;
import java.util.ArrayList;

import static com.kgc.sauw.game.environment.Environment.ITEMS;

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
    public String ID;

    public DisplayParameters DisplayParameters = new DisplayParameters();

    public final ArrayList<com.kgc.sauw.core.utils.ExtraData> ExtraData = new ArrayList<>();

    public void clear() {
        this.id = 0;
        this.count = 0;
        this.damage = 0;
    }

    public Container(String ID) {
        clear();
        this.ID = ID;
    }

    public Container() {
        clear();
    }

    public void setItem(int id, int count, int damage) {
        this.id = id;
        this.count = count;
        this.damage = damage;
        if (count <= 0 || (ITEMS.getItemById(id).getItemConfiguration().maxDamage != 0 &&
                damage >= ITEMS.getItemById(id).getItemConfiguration().maxDamage)) {
            clear();
        }
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

    public static class DisplayParameters {
        public String name;
        public Color nameColor;
    }
}