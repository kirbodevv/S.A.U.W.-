package com.kgc.sauw.UI;

public abstract class InterfaceEvents {
	public Interface Interface;
	
	public abstract void initialize();

    public abstract void tick();

	public abstract void onOpen();

	public abstract void onClose();

	public abstract void renderBefore();
	
	public abstract void render();
}
