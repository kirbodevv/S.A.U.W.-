package com.kgc.sauw.InterfaceAPI;
import com.kgc.sauw.Camera2D;
import com.kgc.sauw.InterfaceAPI.Interface;
import com.kgc.sauw.Textures;
import com.kgc.sauw.GameInterface;
import com.kgc.sauw.Items;
import com.kgc.sauw.Maps;
import com.kgc.sauw.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.commands.Cmd;

public abstract class InterfaceEvents {
	public Interface Interface;
	
	public abstract void initialize();

    public abstract void tick();

	public abstract void onOpen();

	public abstract void onClose();

	public abstract void renderBefore();
	
	public abstract void render();
}
