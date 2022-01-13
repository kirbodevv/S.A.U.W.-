package com.kgc.sauw.core.callbacks;

import java.util.ArrayList;

public interface Initialization {
    ArrayList<Initialization> callbacks = new ArrayList<>();

    void main();
}
