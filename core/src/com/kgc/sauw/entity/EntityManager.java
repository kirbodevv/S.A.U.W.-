package com.kgc.sauw.entity;

import com.badlogic.gdx.Gdx;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.utils.ID;

import java.util.ArrayList;

public class EntityManager implements ExtraData {
    public static final Player PLAYER;
    public static final ArrayList<Entity> ENTITIES_LIST;
    public static final EntityManager ENTITY_MANAGER;

    private static final EntityFactory[] factories;

    static {
        ENTITY_MANAGER = new EntityManager();
        ENTITIES_LIST = new ArrayList<>();
        factories = new EntityFactory[1200];

        PLAYER = (Player) new Player.PlayerFactory().create();

        addFactory(new DropFactory());
    }

    public static void addFactory(EntityFactory entityFactory) {
        factories[entityFactory.getId()] = entityFactory;
    }

    public static Entity spawn(Entity entity) {
        ENTITIES_LIST.add(entity);
        Gdx.app.log("ENTITY MANAGER", "Entity spawned with id : " + entity.getId());
        return entity;
    }

    public static Entity spawn(int id, float x, float y) {
        Entity entity = spawn(factories[id].create());
        entity.setPosition(x, y);

        return entity;
    }

    public static Entity spawn(String id, float x, float y) {
        return spawn(ID.get(id), x, y);
    }

    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        ExtraData[] ED;
        buffer.put("entitiesCount", ENTITIES_LIST.size());
        if (ENTITIES_LIST.size() > 0) {
            ED = new ExtraData[ENTITIES_LIST.size()];
            for (int i = 0; i < ENTITIES_LIST.size(); i++) {
                ED[i] = new Entity.EntitySaver(ENTITIES_LIST.get(i));
            }
            buffer.put("entities", ED);
        }
        return buffer.toBytes();
    }

    @Override
    public void readBytes(byte[] bytes, int begin, int end) {
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes, begin, end);
        Entity.EntityLoaderFactory entityLoaderFactory = new Entity.EntityLoaderFactory();
        if (buffer.getInt("entitiesCount") > 0) {
            buffer.getExtraDataList("entities", entityLoaderFactory);
        }
    }

    public void update() {
        for (Entity entity : ENTITIES_LIST) {
            entity.update();
        }
    }

    public void render(Camera2D cam) {
        for (Entity entity : ENTITIES_LIST) {
            if (Maths.rectCrossing(entity.getPosition().x, entity.getPosition().y, entity.getSize().x, entity.getSize().y, cam.X, cam.Y, cam.W, cam.H))
                entity.render();
        }
    }
}
