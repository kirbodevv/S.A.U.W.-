package com.KGC.SAUW;
import com.badlogic.gdx.graphics.*;

public class Item{
	public int id;
	public Texture t;
	public String name;
	public int maxCount;
	public int maxData;
	public int type;
	public int blockId;
	public int intrumentType;
	public Item(int id, String name, Texture t, int type, int maxCount, int maxData ){
		this.id = id;
		this.t = t;
		this.name = name;
		this.type = type;
		this.maxCount = maxCount;
		this.maxData = maxData;
	}
	public Item(int id, String name, Texture t, int type, int bi, int maxCount, int maxData){
		this.id = id;
		this.t = t;
		this.name = name;
		this.type = type;
		this.blockId = bi;
		this.maxCount = maxCount;
		this.maxData = maxData;
	}
	public void setInstrumentType(int type){
		this.intrumentType = type;
	}
}
