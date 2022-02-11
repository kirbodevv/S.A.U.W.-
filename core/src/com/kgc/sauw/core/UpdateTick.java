package com.kgc.sauw.core;

import com.kgc.sauw.core.achievements.Achievements;
import com.kgc.sauw.core.item.Items;
import com.kgc.sauw.game.Game;
import com.kgc.sauw.game.SAUW;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;

public class UpdateTick extends Thread {
    @Override
    public void run() {
        try {
            super.run();
            getWorld().map.update();
            Items.tick();
            Achievements.checkAchievements(PLAYER.achievementsData);
            getWorld().getTime().updateTime();
            try {
                sleep(50);
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
        if (Game.isRunning)
            new UpdateTick().start();

    }
}
