package com.kgc.sauw.jsonbuilder;

import com.kgc.sauw.jsonbuilder.builder.AchievementBuilder;
import com.kgc.sauw.jsonbuilder.builder.ItemsBuilder;

import java.io.File;
import java.io.IOException;

public class JsonBuilder {
    public static void build() throws IOException {
        File itemsFile = FileUtils.getFile("%game%/src/com/kgc/sauw/game/generated/ItemsGenerated.java");
        File achievementsFile = FileUtils.getFile("%game%/src/com/kgc/sauw/game/generated/AchievementsGenerated.java");
        File blocksFile = FileUtils.getFile("%game%/src/com/kgc/sauw/game/generated/BlocksGenerated.java");

        FileUtils.writeString(itemsFile, new ItemsBuilder().build());
        FileUtils.writeString(achievementsFile, new AchievementBuilder().build());
    }
}
