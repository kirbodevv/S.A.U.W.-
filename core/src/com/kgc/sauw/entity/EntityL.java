package com.kgc.sauw.entity;
import com.kgc.sauw.utils.ExtraData;
import com.badlogic.gdx.Gdx;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraDataFactory;
import java.util.ArrayList;

public class EntityL implements com.intbyte.bdb.ExtraData {
	public static class MobFactory implements ExtraDataFactory {
		@Override
		public com.intbyte.bdb.ExtraData getExtraData() {
			return new EntityL();
		}
	}
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		buffer.put("coords", new int[]{posX, posY});
		buffer.put("type", type);
		for (ExtraData data : ExtraData) {
			if (Integer.class.isInstance(data.getValue())) {
				buffer.put(data.key, (int)data.getValue());
			} else if (Float.class.isInstance(data.getValue())) {
				buffer.put(data.key, (float)data.getValue());
			} else if (Double.class.isInstance(data.getValue())) {
				buffer.put(data.key, (double)data.getValue());
			} else if (Short.class.isInstance(data.getValue())) {
				buffer.put(data.key, (short)data.getValue());
			} else if (Long.class.isInstance(data.getValue())) {
				buffer.put(data.key, (long)data.getValue());
			} else if (Byte.class.isInstance(data.getValue())) {
				buffer.put(data.key, (byte)data.getValue());
			}
		}
		return buffer.toBytes();
	}

	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes, begin, end);
		posX = buffer.getIntArray("coords")[0];
		posY = buffer.getIntArray("coords")[1];
		type = buffer.getInt("type");
		loadedEntity = createMob(type);
		loadedEntity.loadExtraData(bytes, begin, end);
	}
	public void loadExtraData(byte[] bytes, int begin, int end){
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes, begin, end);
		for (ExtraData data : ExtraData) {
			if (Integer.class.isInstance(data.getValue())) {
				data.setValue(buffer.getInt(data.key));
			} else if (Float.class.isInstance(data.getValue())) {
				data.setValue(buffer.getFloat(data.key));
			} else if (Double.class.isInstance(data.getValue())) {
				data.setValue(buffer.getDouble(data.key));
			} else if (Short.class.isInstance(data.getValue())) {
				data.setValue(buffer.getShort(data.key));
			} else if (Long.class.isInstance(data.getValue())) {
				data.setValue(buffer.getLong(data.key));
			} else if (Byte.class.isInstance(data.getValue())) {
				data.setValue(buffer.getByte(data.key));
			}
		}
	}
	public EntityL loadedEntity;
	public EntityL createMob(int type){
		if (type == 0) {
			//return new Drop(posX, posY, 0, 0, 0);
		}
		return null;
	}
	public int type;
	public int posX, posY;
	public int h = Gdx.graphics.getHeight();
	public int w = Gdx.graphics.getWidth();
	public float plW = w / 16 * 10 / 26;
	public float plH = w / 16;
	public int mX = (int) (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
	public int mY = (int) (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));
    public boolean collisions = true;
	public ArrayList<ExtraData> ExtraData = new ArrayList<ExtraData>();
	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	public void update() {

	}
	public void render() {

	}
	public void setExtraData(String key, Object value) {
		for (ExtraData ED : ExtraData) {
			if (ED.key.equals(key)) {
				ED.setValue(value);
				return;
			}
		}
		ExtraData.add(new ExtraData(key));
		for (ExtraData ED : ExtraData) {
			if (ED.key.equals(key)) {
				ED.setValue(value);
				return;
			}
		}
	}
	public Object getExtraData(String key) {
		for (ExtraData ED : ExtraData) {
			if (ED.key.equals(key)) {
				return ED.getValue();
			}
		}
		return null;
	}
}
