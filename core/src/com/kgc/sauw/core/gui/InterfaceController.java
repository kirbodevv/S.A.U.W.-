package com.kgc.sauw.core.gui;

public interface InterfaceController {
    void init();

    void tick();

    void onOpen();

    void onClose();

    void preRender();

    void postRender();
}