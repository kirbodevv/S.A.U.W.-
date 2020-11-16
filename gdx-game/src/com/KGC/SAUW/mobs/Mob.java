package com.KGC.SAUW.mobs;
import com.KGC.SAUW.ExtraData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.intbyte.bdb.DataBuffer;
import java.util.ArrayList;
import com.intbyte.bdb.ExtraDataFactory;

public class Mob implements com.intbyte.bdb.ExtraData {
	/*public static class MobFactory implements ExtraDataFactory {
		@Override
		public com.intbyte.bdb.ExtraData getExtraData() {
			return ;
		}
	}*/
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		buffer.put("coords", new int[]{posX, posY});
		for (ExtraData data : ExtraData) {
			buffer.put(data.key, data.getValue());
			/*if(data.getValue() instanceof int){
			
			 } else if(data.getValue() instanceof float){

			 } else if(data.getValue() instanceof double){

			 } else if(data.getValue() instanceof String){

			 } else if(data.getValue() instanceof short){

			 } else if(data.getValue() instanceof long){

			 } else if(data.getValue() instanceof byte){

			 }*/
		}
		return null;
	}

	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes);
		posX = buffer.getIntArray("coords")[0];
		posY = buffer.getIntArray("coords")[1];
		for(ExtraData data : ExtraData){
			if(data.getValue() instanceof int){
				data.setValue(buffer.getInt(data.key));
			 } else if(data.getValue() instanceof float){
				 data.setValue(buffer.getFloat(data.key));
			 } else if(data.getValue() instanceof double){
				 data.setValue(buffer.getDouble(data.key));
			 } else if(data.getValue() instanceof short){
				 data.setValue(buffer.getShort(data.key));
			 } else if(data.getValue() instanceof long){
				 data.setValue(buffer.getLong(data.key));
			 } else if(data.getValue() instanceof byte){
				 data.setValue(buffer.getByte(data.key));
			 }
		}
	}
	public String type;
	public int posX, posY;
	public int h = Gdx.graphics.getHeight();
	public int w = Gdx.graphics.getWidth();
	public int plW = w / 16 * 10 / 26;
	public int plH = w / 16;
	public int mX = (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
	public int mY = (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));
    public boolean collisions = true;
	public ArrayList<ExtraData> ExtraData = new ArrayList<ExtraData>();
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	public void update() {

	}
	public void render(SpriteBatch b) {

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
