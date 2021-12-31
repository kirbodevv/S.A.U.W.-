package com.kgc.sauw.modding.json_data;

import org.json.JSONObject;

public interface JSONData<T> {
    void parse(JSONObject json);

    T toObject();
}
