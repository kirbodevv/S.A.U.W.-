package com.kgc.sauw;
import java.util.ArrayList;

public class ExtraData {
    public String key;
	private Object value;
    public ExtraData(String key){
		this.key = key;
	}
	public void setValue(Object value){
		this.value = value;
	}
	public Object getValue(){
		return value;
	}
	public static ExtraData getExtraData(ArrayList<ExtraData> data, String key){
		for(ExtraData d : data){
			if(d.key.equals(key)) return d;
		}
		return null;
	}
}
