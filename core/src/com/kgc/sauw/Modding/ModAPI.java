package com.kgc.sauw.Modding;
import com.kgc.sauw.UI.GameInterface;

public class ModAPI {
    public Console Console;
	public ModAPI(GameInterface gi){
		Console = new Console(gi);
	}
}
