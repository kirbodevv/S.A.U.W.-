package com.KGC.SAUW.mobs;
import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.Maps;
import com.KGC.SAUW.Maths;
import com.KGC.SAUW.Textures;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.intbyte.bdb.ExtraData;
import java.util.ArrayList;
import com.intbyte.bdb.DataBuffer;
import com.KGC.SAUW.Items;
import java.util.List;

public class Mobs implements ExtraData {
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		ExtraData[] ED = null;
		buffer.put("mobsCount", mobs.size());
		if (mobs.size() > 0) {
			ED = new ExtraData[mobs.size()];
			for (int i = 0; i < mobs.size(); i++) {
				ED[i] = mobs.get(i);
			}
			buffer.put("mobs", ED);
		}
		return buffer.toBytes();
	}

	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes, begin, end);
		Mob.MobFactory mobFactotory = new Mob.MobFactory(items);
		List<? extends ExtraData> mobs = null;
		if (buffer.getInt("mobsCount") > 0) {
			mobs = buffer.getExtraDataList("mobs", mobFactotory);
			for (int i = 0; i < mobs.size(); i++) {
				Mob mob = (Mob)mobs.get(i);
				if (mob.loadedMob != null) {
					spawn(mob.loadedMob);
				}
			}
		}
	}
	public ArrayList<Mob> mobs = new ArrayList<Mob>();
	private SpriteBatch b;
	private Maps m;
	private Textures t;
	private Items items;
    public Mobs(SpriteBatch b, Maps m, Textures t, Items items) {
		this.b = b;
		this.m = m;
		this.t = t;
		this.items = items;
	}
	public void update() {
		for (Mob mob : mobs) {
			mob.update();
		}
	}
	public void render(Camera2D cam) {
		for (Mob mob : mobs) {
			if (Maths.rectCrossing(mob.posX, mob.posY, mob.plW, mob.plH, cam.X, cam.Y, cam.W, cam.H))
				mob.render(b);
		}
	}
	public boolean spawn(Mob mob) {
		mobs.add(mob);
		return true;
	}
    public Mob getNearMob(int X, int Y, int mob, int type) {
        int id = -1;
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).type == mob) {
                if (i == 0 || id == -1) {
					id = i;
                } else {
                    if (Maths.distance(X, Y, mobs.get(i).mX, mobs.get(i).mY) < Maths.distance(X, Y, mobs.get(id).mX, mobs.get(id).mY)) {
                        id = i;
                    }
                }
			}
            return mobs.get(id);
        }
        return null;
    }
}
