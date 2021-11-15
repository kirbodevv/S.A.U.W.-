package com.kgc.sauw.core.entity;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.core.callbacks.Callback;
import com.kgc.sauw.core.callbacks.InteractionButtonClicked;
import com.kgc.sauw.core.entity.entities.drop.DropFactory;
import com.kgc.sauw.core.entity.entities.npc.Npc;
import com.kgc.sauw.core.entity.entities.player.Player;
import com.kgc.sauw.core.entity.entities.player.PlayerFactory;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.game.entity.egor.EgorFactory;

import java.util.ArrayList;

import static com.kgc.sauw.core.GameContext.SAUW;

public class EntityManager implements ExtraData {
    public static final Player PLAYER;
    public static final ArrayList<Entity> ENTITIES_LIST;
    public static final EntityManager ENTITY_MANAGER;

    private static final EntityFactory[] factories;

    static {
        ENTITY_MANAGER = new EntityManager();
        ENTITIES_LIST = new ArrayList<>();
        factories = new EntityFactory[1200];

        PLAYER = (Player) new PlayerFactory().create();

        addFactory(new DropFactory());
        addFactory(new EgorFactory());

        Callback.addCallback(new InteractionButtonClicked() {
            @Override
            public void main() {
                for (Entity entity : ENTITIES_LIST) {
                    if (entity instanceof Npc)
                        if (Maths.distance(PLAYER.getPosition(), entity.getPosition()) < 3) {
                            ((Npc) entity).interact();
                        }
                }
            }
        });
    }

    public static void addFactory(EntityFactory entityFactory) {
        factories[entityFactory.getId()] = entityFactory;
    }

    public static void delete(Entity entity) {
        ENTITIES_LIST.remove(entity);
        Physic.getWorld().destroyBody(entity.body);
    }

    public static Entity add(Entity entity) {
        ENTITIES_LIST.add(entity);
        return entity;
    }

    public static Entity spawn(int id, float x, float y) {
        Entity entity = add(factories[id].create());
        entity.setPosition(x, y);

        return entity;
    }

    public static Entity spawn(String id, float x, float y) {
        return spawn(SAUW.getId(id), x, y);
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

    public static Entity findEntity(Entity entity, float radius) {
        for (Entity entity1 : ENTITIES_LIST) {
            if (Maths.distance(
                    entity.getPosition().x, entity.getPosition().y,
                    entity1.getPosition().x, entity1.getPosition().y
            ) <= radius) return entity1;
        }
        return null;
    }
}
