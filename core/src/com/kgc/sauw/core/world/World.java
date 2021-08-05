package com.kgc.sauw.core.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.environment.Time;
import com.kgc.sauw.core.utils.StringUtils;

import java.util.Random;

import static com.kgc.sauw.core.entity.EntityManager.ENTITY_MANAGER;
import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.GAME_CAMERA;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_WIDTH;
import static com.kgc.sauw.game.environment.Environment.BLOCKS;
import static com.kgc.sauw.game.gui.Interfaces.HUD;
import static com.kgc.sauw.game.gui.Interfaces.isAnyInterfaceOpen;

public abstract class World {
    public Map map;

    private int skyLight = 0xFFFFFFFF;

    private boolean interfaceTouched;
    private boolean isTouched;
    private final Random random = new Random();
    private boolean worldTouched;
    public Time worldTime;

    private String worldName = "";

    public int getSkyLight() {
        return skyLight;
    }

    public String getWorldName() {
        return worldName;
    }

    public World() {
        worldTime = new Time();
        map = new Map();
        init();
    }

    public final void update() {
        PLAYER.update();
        ENTITY_MANAGER.update();
        if (Gdx.input.isTouched()) {
            if (!isTouched) {
                if (HUD.isTouched())
                    interfaceTouched = true;
                else worldTouched = true;

                isTouched = true;
            }
        }
        if (!Gdx.input.isTouched() && interfaceTouched)
            interfaceTouched = false;
        if (!Gdx.input.isTouched() && worldTouched) {
            worldTouched = false;
            if (!interfaceTouched && !isAnyInterfaceOpen()) {
                float sc = SCREEN_WIDTH / GAME_CAMERA.W;
                int bX = (int) Math.ceil(Gdx.input.getX() / sc + GAME_CAMERA.X) - 1;
                int bY = (int) Math.ceil((Gdx.graphics.getHeight() - Gdx.input.getY()) / sc + GAME_CAMERA.Y) - 1;

                Callback.executeTouchOnBlockCallback(new Vector3(bX, bY, map.getHighestBlock(bX, bY)));
            }
        }
        if (Gdx.input.isTouched()) {
            isTouched = false;
        }
        randomTick();
        tick();
    }

    private void randomTick() {
        int yy = random.nextInt(Map.ySize - 1) + 1;
        int xx = random.nextInt(Map.xSize - 1) + 1;
        BLOCKS.getBlockById(map.getTile(xx, yy, 0).id).randomTick(map.getTile(xx, yy, 0));
    }

    protected void setSkyLight(int skyLight) {
        this.skyLight = skyLight;
    }

    protected void setWorldName(String worldName) {
        this.worldName = StringUtils.getString(worldName);
    }

    protected abstract void init();

    protected abstract void tick();

    public abstract void createNewWorld();
}