package com.kgc.sauw.core.callbacks;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public interface TouchOnBlock {
    ArrayList<TouchOnBlock> callbacks = new ArrayList<>();

    void main(Vector3 position);
}
