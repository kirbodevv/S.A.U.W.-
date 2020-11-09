package com.KGC.SAUW.InterfaceAPI;
import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.InterfaceAPI.Interface;
import com.KGC.SAUW.Textures;
import com.KGC.SAUW.GameInterface;
import com.KGC.SAUW.Items;
import com.KGC.SAUW.Maps;
import com.KGC.SAUW.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.KGC.SAUW.commands.Cmd;

public abstract class InterfaceEvents {
	public Interface Interface;
	
	public abstract void initialize();

    public abstract void tick();

	public abstract void onOpen();
    
	public abstract void renderBefore();
	
	public abstract void render();
}
