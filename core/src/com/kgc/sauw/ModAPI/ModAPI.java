package com.kgc.sauw.ModAPI;
import com.kgc.sauw.GameInterface;

public class ModAPI {
    public Console Console;
	public ModAPI(GameInterface gi){
		Console = new Console(gi);
	}
}
