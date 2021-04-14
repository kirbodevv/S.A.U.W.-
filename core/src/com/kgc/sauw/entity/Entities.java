package com.kgc.sauw.entity;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.map.Maps;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.resource.Textures;
import com.intbyte.bdb.ExtraData;
import java.util.ArrayList;
import com.intbyte.bdb.DataBuffer;
import java.util.List;

public class Entities implements ExtraData {
	public static final Player PLAYER;
	public static final ArrayList<Entity> ENTITIES;
	static {
		ENTITIES = new ArrayList<>();
		PLAYER = new Player();
	}
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		ExtraData[] ED = null;
		buffer.put("mobsCount", ENTITIES.size());
		if (ENTITIES.size() > 0) {
			ED = new ExtraData[ENTITIES.size()];
			for (int i = 0; i < ENTITIES.size(); i++) {
				ED[i] = ENTITIES.get(i);
			}
			buffer.put("mobs", ED);
		}
		return buffer.toBytes();
	}

	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes, begin, end);
		Entity.MobFactory mobFactotory = new Entity.MobFactory();
		List<? extends ExtraData> mobs = null;
		if (buffer.getInt("mobsCount") > 0) {
			mobs = buffer.getExtraDataList("mobs", mobFactotory);
			for (int i = 0; i < mobs.size(); i++) {
				Entity entity = (Entity)mobs.get(i);
				if (entity.loadedEntity != null) {
					spawn(entity.loadedEntity);
				}
			}
		}
	}

	private Maps m;
	private Textures t;
    public Entities(Maps m, Textures t) {
		this.m = m;
		this.t = t;
	}
	public Player getPlayer(){
    	return PLAYER;
	}
	public void update() {
		for (Entity entity : ENTITIES) {
			entity.update();
		}
	}
	public void render(Camera2D cam) {
		for (Entity entity : ENTITIES) {
			if (Maths.rectCrossing(entity.posX, entity.posY, entity.plW, entity.plH, cam.X, cam.Y, cam.W, cam.H))
				entity.render();
		}
	}
	public boolean spawn(Entity entity) {
		ENTITIES.add(entity);
		return true;
	}
    public Entity getNearMob(int X, int Y, int mob, int type) {
        int id = -1;
		for (int i = 0; i < ENTITIES.size(); i++) {
			if (ENTITIES.get(i).type == mob) {
                if (i == 0 || id == -1) {
					id = i;
                } else {
                    if (Maths.distance(X, Y, ENTITIES.get(i).mX, ENTITIES.get(i).mY) < Maths.distance(X, Y, ENTITIES.get(id).mX, ENTITIES.get(id).mY)) {
                        id = i;
                    }
                }
			}
            return ENTITIES.get(id);
        }
        return null;
    }
}
