package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.item.Items;
import com.kgc.sauw.modding.json_data.JSONItem;
import org.json.JSONObject;

public class ModItems {
    public ModItems(FileHandle modItemsDir, GameContext gameContext) {
        FileHandle[] files = modItemsDir.list();
        for (FileHandle file : files) {
            if (file.name().endsWith(".item.json")) {
                JSONItem jsonItem = new JSONItem();
                jsonItem.parse(new JSONObject(file.readString()));
                Item item = jsonItem.toObject();
                Items.INSTANCE.register(item, gameContext.getPackage(), jsonItem.id);
            }
        }
    }
}
