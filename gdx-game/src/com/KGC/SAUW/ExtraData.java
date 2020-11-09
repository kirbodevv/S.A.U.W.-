package com.KGC.SAUW;

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
}
