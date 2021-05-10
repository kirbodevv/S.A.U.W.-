package com.kgc.sauw;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;

import java.awt.*;
import java.util.ArrayList;

public class InventoryContainer implements ExtraData {
    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("id", id);
        buffer.put("count", count);
        buffer.put("data", data);
        return buffer.toBytes();
    }

    @Override
    public void readBytes(byte[] bytes, int begin, int end) {
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes);
        id = buffer.getInt("id");
        count = buffer.getInt("count");
        data = buffer.getInt("data");
    }

    public int id, count, data;

    public DisplayParameters DisplayParameters = new DisplayParameters();

    public final ArrayList<com.kgc.sauw.utils.ExtraData> ExtraData = new ArrayList<>();

    public void setItem(int id, int count, int data) {
        this.id = id;
        this.count = count;
        this.data = data;
    }

    public void clear() {
        this.id = 0;
        this.count = 0;
        this.data = 0;
    }

    public static class DisplayParameters {
        public String name;
        public Color nameColor;
    }
}