package com.kgc.sauw.Modding;
import com.kgc.sauw.UI.GameInterface;
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
