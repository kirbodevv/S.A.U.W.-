package com.kgc.sauw.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.core.graphic.Graphic.INTERFACE_CAMERA;

public class Units {
    public static float fromStringToFloat(String string) {
        float result = 0.0f;
        Matcher matcher;

        Pattern patternBl = Pattern.compile("\\d+bl$");
        Pattern patternBlFromFixedBlCount = Pattern.compile("\\d+bl:\\d+$");
        Pattern patternPercentW = Pattern.compile("\\d+%W$");
        Pattern patternPercentH = Pattern.compile("\\d+%H$");

        matcher = patternBl.matcher(string);
        if (matcher.find()) {
            result = Float.parseFloat(string.substring(0, string.length() - 2));
        }
        matcher = patternBlFromFixedBlCount.matcher(string);
        if (matcher.find()) {
            String[] floats = string.split(":");
            float num = Float.parseFloat(floats[0].substring(0, floats[0].length() - 2));
            float size = Float.parseFloat(floats[1]);
            result = (num / size) * (INTERFACE_CAMERA.H / BLOCK_SIZE);
        }

        matcher = patternPercentW.matcher(string);
        if (matcher.find()) {
            float val = Float.parseFloat(string.substring(0, string.length() - 2)) / 100f;
            result = (val * INTERFACE_CAMERA.W) / BLOCK_SIZE;
        }

        matcher = patternPercentH.matcher(string);
        if (matcher.find()) {
            float val = Float.parseFloat(string.substring(0, string.length() - 2)) / 100f;
            result = (val * INTERFACE_CAMERA.H) / BLOCK_SIZE;
        }
        return result;
    }

    public static float fromStringToPx(String string) {
        return fromStringToFloat(string) * BLOCK_SIZE;
    }
}
