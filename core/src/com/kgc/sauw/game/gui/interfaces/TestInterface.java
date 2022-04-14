package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.utils.Align;
import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.MultilineTextView;
import com.kgc.utils.OpenSimplexNoise;

@RegistryMetadata("sauw:test")
public class TestInterface extends Interface {
    OpenSimplexNoise openSimplexNoise = new OpenSimplexNoise();

    public TestInterface() {

        MultilineTextView multilineTextView = new MultilineTextView(10);
        multilineTextView.setSizeInBlocks(10, 4);
        multilineTextView.setTextAlign(Align.left);
        multilineTextView.setText("lol\nlol\nlol\nlol\nlol\nlol\nlol\nlol\nlol\nlol");
        mainLayout.addElements(multilineTextView);

    }

    @Override
    public void tick() {

    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void preRender() {

    }

    @Override
    public void postRender() {
    }
}
