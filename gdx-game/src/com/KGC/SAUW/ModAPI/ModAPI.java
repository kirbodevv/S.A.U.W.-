package com.KGC.SAUW.ModAPI;
import com.KGC.SAUW.GameInterface;

public class ModAPI {
    public Console Console;
	public ModAPI(GameInterface gi){
		Console = new Console(gi);
	}
}
