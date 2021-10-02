package com.kgc.sauw.core.gui;

public abstract class InterfaceController {
    public abstract void init();

    public abstract void tick();

    public abstract void onOpen();

    public abstract void onClose();

    public abstract void preRender();

    public abstract void postRender();
}