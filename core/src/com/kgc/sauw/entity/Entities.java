package com.kgc.sauw.entity;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.utils.Camera2D;

import java.util.ArrayList;
import java.util.List;

public class Entities implements ExtraData {
	public static final Player PLAYER;
	public static final ArrayList<EntityL> ENTITIES_LIST;
	public static final Entities ENTITIES;
	static {
	    ENTITIES = new Entities();
		ENTITIES_LIST = new ArrayList<>();
		PLAYER = new Player();
	}
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		ExtraData[] ED;
		buffer.put("mobsCount", ENTITIES_LIST.size());
		if (ENTITIES_LIST.size() > 0) {
			ED = new ExtraData[ENTITIES_LIST.size()];
			for (int i = 0; i < ENTITIES_LIST.size(); i++) {
				ED[i] = ENTITIES_LIST.get(i);
			}
			buffer.put("mobs", ED);
		}
		return buffer.toBytes();
	}

	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes, begin, end);
		EntityL.MobFactory mobFactory = new EntityL.MobFactory();
		List<? extends ExtraData> mobs;
		if (buffer.getInt("mobsCount") > 0) {
			mobs = buffer.getExtraDataList("mobs", mobFactory);
			for (ExtraData mob : mobs) {
				EntityL entity = (EntityL) mob;
				if (entity.loadedEntity != null) {
					spawn(entity.loadedEntity);
				}
			}
		}
	}

	public void update() {
		for (EntityL entity : ENTITIES_LIST) {
			entity.update();
		}
	}
	public void render(Camera2D cam) {
		for (EntityL entity : ENTITIES_LIST) {
			if (Maths.rectCrossing(entity.posX, entity.posY, entity.plW, entity.plH, cam.X, cam.Y, cam.W, cam.H))
				entity.render();
		}
	}
	public void spawn(EntityL entity) {
		ENTITIES_LIST.add(entity);
	}
}
