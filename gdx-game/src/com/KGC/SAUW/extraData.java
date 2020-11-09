package com.KGC.SAUW;

public class extraData {
    public String key;
	private Object value;
    public extraData(String key){
		this.key = key;
	}
	public void setValue(Object value){
		this.value = value;
	}
	public Object getValue(){
		return value;
	}
}
