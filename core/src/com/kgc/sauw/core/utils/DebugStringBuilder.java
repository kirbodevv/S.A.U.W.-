package com.kgc.sauw.core.utils;

public class DebugStringBuilder {
    private String result = "";

    public DebugStringBuilder header(String header) {
        result += "\n [DEB_H]" + header;
        return this;
    }

    public DebugStringBuilder parameter(String name, Object parameter) {
        result += "\n [SAUWTXT]" + name + ": " + parameter;
        return this;
    }

    public String build() {
        String result = this.result.substring(1); //Without "\n" in begin
        this.result = "";
        return result;
    }
}
