package com.kgc.sauw.core.callbacks;

import com.badlogic.gdx.math.Vector3;

public class Callback {
    public static void addCallback(TouchOnBlock callback) {
        TouchOnBlock.callbacks.add(callback);
    }

    public static void executeTouchOnBlockCallback(Vector3 position) {
        for (TouchOnBlock callback : TouchOnBlock.callbacks)
            callback.main(position);
    }
}
