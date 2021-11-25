package com.kgc.sauw.core.environment.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class AchievementsData {
    public void load(FileHandle file) {
        receivedAchievements.addAll(Arrays.asList(FileUtils.loadList(file)));
    }

    public void save(FileHandle file) {
        FileUtils.saveList(file, receivedAchievements);
    }

    private final ArrayList<String> receivedAchievements = new ArrayList<>();

    public void addAchievement(Achievement achievement) {
        if (!hasAchievement(achievement.getStringId())) {
            this.receivedAchievements.add(achievement.getStringId());
            Gdx.app.log("Achievements", "Player received achievement " + achievement.getStringId());
        }
    }

    public boolean hasAchievement(String id) {
        for (String s : receivedAchievements) {
            if (s.equals(id)) return true;
        }
        return false;
    }

}
