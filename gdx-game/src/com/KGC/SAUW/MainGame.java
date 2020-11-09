package com.KGC.SAUW;

import com.badlogic.gdx.Game;
import com.KGC.SAUW.screen.*;
import android.content.Intent;

public class MainGame extends Game {
    public int keyboardHeight = 0;
	
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
