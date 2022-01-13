package com.kgc.sauw.core.callbacks;

import com.badlogic.gdx.math.Vector3;

public class Callback {
    public static void addCallback(TouchOnBlock callback) {
        TouchOnBlock.callbacks.add(callback);
    }

    public static void addCallback(InteractionButtonClicked callback) {
        InteractionButtonClicked.callbacks.add(callback);
    }

    public static void addCallback(Initialization callback) {
        Initialization.callbacks.add(callback);
    }

    public static void executeTouchOnBlockCallbacks(Vector3 position) {
        for (TouchOnBlock callback : TouchOnBlock.callbacks)
            callback.main(position);
    }

    public static void executeInteractionButtonClickedCallbacks() {
        for (InteractionButtonClicked callback : InteractionButtonClicked.callbacks)
            callback.main();
    }

    public static void executeInitializationCallbacks() {
        for (Initialization callback : Initialization.callbacks)
            callback.main();
    }
}
