package com.KGC.SAUW.InterfaceAPI;

public class container {
    private int id, count, data;
	public String ID;
	public container(String ID){
		id = 0;
		count = 0;
		data = 0;
		this.ID = ID;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", count:" + count + ", data:" + data + ", ID:" + ID + "}";
	}
	
	public void setItem(int id, int count, int data){
		this.id = id;
		this.count = count;
		this.data = data;
		if(count <= 0){
			this.id = 0;
			this.count = 0;
			this.data = 0;
		}
	}
	public int getId(){
		return id;
	}
	public int getCount(){
		return count;
	}
	public int getData(){
		return data;
	}
}
