package com.kgc.sauw.modding.json_data;

import com.kgc.sauw.core.creative_categories.Category;
import com.kgc.sauw.core.resource.Resource;
import org.json.JSONObject;

public class JSONCreativeTab implements JSONData<Category> {
    public String id;
    public String icon;

    @Override
    public void parse(JSONObject json) {
        id = json.getString("id");
        icon = json.getString("icon");
    }

    @Override
    public Category toObject() {
        return new Category(Resource.getTexture(icon));
    }
}
