package com.KGC.SAUW.ModAPI;
import com.KGC.SAUW.gameInterface;
import java.util.ArrayList;

public class Console {
	private gameInterface gi;
	public ArrayList<String> inputs = new ArrayList<String>();
    public Console(gameInterface gi){
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
