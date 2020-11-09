package com.KGC.SAUW.InterfaceAPI;
import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.InterfaceAPI.Interface;
import com.KGC.SAUW.Textures;
import com.KGC.SAUW.gameInterface;
import com.KGC.SAUW.items;
import com.KGC.SAUW.maps;
import com.KGC.SAUW.player;
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
