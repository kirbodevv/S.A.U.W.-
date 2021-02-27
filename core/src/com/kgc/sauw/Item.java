package com.kgc.sauw;
import com.badlogic.gdx.graphics.*;

public class Item{
	public int id;
	public String StringId;
	public Texture t;
	public String name;
	public int maxCount;
	public int maxData;
	public int type;
	public int blockId;
	public int intrumentType;
	private int foodScore;
	public float weight = 0.5f;
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
	public Item(int id, String StringId, String name, Texture t, int type, int bi, int maxCount, int maxData){
		this.id = id;
		this.StringId = StringId;
		this.t = t;
		this.name = name;
		this.type = type;
		this.blockId = bi;
		this.maxCount = maxCount;
		this.maxData = maxData;
	}
	public Item(int id, String StringId, String name, Texture t, int type, int maxCount, int maxData){
		this.id = id;
		this.StringId = StringId;
		this.t = t;
		this.name = name;
		this.type = type;
		this.maxCount = maxCount;
		this.maxData = maxData;
	}
	public void setInstrumentType(int type){
		this.intrumentType = type;
	}
	public void setFoodScore(int foodScore){
		this.foodScore = foodScore;
	}
	public int getFoodScore(){
		return foodScore;
	}
}
