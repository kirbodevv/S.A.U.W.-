package com.kgc.sauw.core.callbacks;

import java.util.ArrayList;

public interface GameLoaded {
    ArrayList<GameLoaded> callbacks = new ArrayList<>();

    void main();
}
