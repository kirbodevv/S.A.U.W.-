package com.kgc.sauw.core.callbacks;

import java.util.ArrayList;

public interface InteractionButtonClicked {
    ArrayList<InteractionButtonClicked> callbacks = new ArrayList<>();

    void main();
}
