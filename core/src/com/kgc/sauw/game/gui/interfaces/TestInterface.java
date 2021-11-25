package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.core.gui.elements.TextView;
import com.kgc.sauw.core.skins.Skins;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;

public class TestInterface extends Interface {
    public TestInterface() {

        actionBar.setText("TOP SECRET");


        Layout testLayout = new Layout(Layout.Orientation.HORIZONTAL);
        testLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        testLayout.setGravity(Layout.Gravity.LEFT);

        Button button1 = new Button("", 0, 0, 0, 0);
        button1.setSizeInBlocks(2, 1);
        button1.setText("А это кнопка");

        TextView test = new TextView();
        test.setSize(BLOCK_SIZE, BLOCK_SIZE);
        test.setText("Это не кнопка");

        testLayout.addElements(test, button1);

        mainLayout.addElements(testLayout);

        updateElementsList();
    }

    private ElementSkin testSkin = new ElementSkin(Skins.round_up);
    Color[] colors = new Color[]{
            new Color(0xFF0000FF),
            new Color(0x0000FFFF),
            new Color(0x00FF00FF),
            new Color(0x00FFFFFF),
            new Color(0x000000FF)
    };
    Color color = new Color(0x000000FF);
    int counter = 0;

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
        testSkin.draw(100, 100, 400, 100, color, colors[counter], 0.01f);
        if (testSkin.getColor().equals(colors[counter])) counter++;
    }
}
