package com.kgc.sauw.modding;

import org.json.JSONArray;
import org.json.JSONObject;

public class Manifest {
    public static class Dependence {
        public String package_;
        public String version;

        public Dependence(String package_, String version) {
            this.package_ = package_;
            this.version = version;
        }
    }

    public String package_;
    public int modFormat;
    public String[] authors;
    public String name;
    public String description;
    public Dependence[] dependencies;

    public Manifest(JSONObject jsonObject) {
        JSONObject header = jsonObject.getJSONObject("header");
        JSONArray authorsArray = jsonObject.getJSONArray("authors");
        JSONArray dependenciesArray = jsonObject.getJSONArray("dependencies");

        package_ = jsonObject.getString("package");
        modFormat = jsonObject.getInt("mod_format");
        name = header.getString("name");
        description = header.getString("description");

        authors = new String[authorsArray.length()];
        for (int i = 0; i < authors.length; i++) {
            authors[i] = (String) authorsArray.get(i);
        }
        dependencies = new Dependence[dependenciesArray.length()];
        for (int i = 0; i < dependencies.length; i++) {
            JSONObject dependence = dependenciesArray.getJSONObject(i);
            dependencies[i] = new Dependence(
                    dependence.getString("package"),
                    dependence.getString("version"));
        }
    }
}
