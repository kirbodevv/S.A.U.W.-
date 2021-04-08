package com.kgc.sauw.entity;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.map.Maps;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.resource.Textures;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.intbyte.bdb.ExtraData;
import java.util.ArrayList;
import com.intbyte.bdb.DataBuffer;
import java.util.List;

public class Entities implements ExtraData {
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		ExtraData[] ED = null;
		buffer.put("mobsCount", entities.size());
		if (entities.size() > 0) {
			ED = new ExtraData[entities.size()];
			for (int i = 0; i < entities.size(); i++) {
				ED[i] = entities.get(i);
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
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	private Maps m;
	private Textures t;
    public Entities(Maps m, Textures t) {
		this.m = m;
		this.t = t;
	}
	public void update() {
		for (Entity entity : entities) {
			entity.update();
		}
	}
	public void render(Camera2D cam) {
		for (Entity entity : entities) {
			if (Maths.rectCrossing(entity.posX, entity.posY, entity.plW, entity.plH, cam.X, cam.Y, cam.W, cam.H))
				entity.render();
		}
	}
	public boolean spawn(Entity entity) {
		entities.add(entity);
		return true;
	}
    public Entity getNearMob(int X, int Y, int mob, int type) {
        int id = -1;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).type == mob) {
                if (i == 0 || id == -1) {
					id = i;
                } else {
                    if (Maths.distance(X, Y, entities.get(i).mX, entities.get(i).mY) < Maths.distance(X, Y, entities.get(id).mX, entities.get(id).mY)) {
                        id = i;
                    }
                }
			}
            return entities.get(id);
        }
        return null;
    }
}
