package com.kgc.sauw.CallbackAPI;
import java.util.ArrayList;

public class Callbacks {
	public static class Argument{
		Object val = new Object();
		public int toInt(){
			return Integer.parseInt(val.toString());
		}

		@Override
		public String toString(){
			return (String) val;
		}
		public float toFloat(){
			return Float.parseFloat(val.toString());
		}
		public double toDouble(){
			return Double.parseDouble(val.toString());
		}
	}
	public static abstract class callback{
        public String name;
		public int argsCount;
		public abstract void call(Argument[] args);
	}
	private ArrayList<callback> callbacks = new ArrayList<callback>();
    public void addCallback(String name, int AR, callback callback){
		callback.name = name;
		callback.argsCount = AR;
		callbacks.add(callback);
	}
	public void call(String name, Argument[] args){
		for(Callbacks.callback i : callbacks){
			if(i.name.equals(name) && args.length == i.argsCount){
				i.call(args);
			}
		}
	}
}
