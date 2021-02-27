package com.kgc.sauw.ModAPI;
import com.kgc.sauw.GameInterface;
import java.util.ArrayList;

public class Console {
	private GameInterface gi;
	public ArrayList<String> inputs = new ArrayList<String>();
    public Console(GameInterface gi){
		this.gi = gi;
	}
	public void print(String txt){
		gi.consolePrint(txt);
	}
	public ArrayList<String> input(){
		return inputs;
	}
	public String input(int pointer){
		return inputs.get(pointer);
	}
}
