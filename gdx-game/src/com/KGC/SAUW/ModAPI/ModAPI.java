package com.KGC.SAUW.ModAPI;
import com.KGC.SAUW.gameInterface;

public class ModAPI {
    public Console Console;
	public ModAPI(gameInterface gi){
		Console = new Console(gi);
	}
}
