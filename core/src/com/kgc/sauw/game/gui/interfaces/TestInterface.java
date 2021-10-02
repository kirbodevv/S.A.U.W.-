package com.kgc.sauw.game.gui.interfaces;

import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.core.gui.elements.Text;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;

public class TestInterface extends Interface {
    public TestInterface() {
        super("TEST_INTERFACE");

        actionBar.setText("TOP SECRET");


        Layout testLayout = new Layout(Layout.Orientation.HORIZONTAL);
        testLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        testLayout.setGravity(Layout.Gravity.LEFT);

        Button button1 = new Button("", 0, 0, 0, 0);
        button1.setSizeInBlocks(2, 1);
        button1.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                for (InterfaceElement e : com.kgc.sauw.core.gui.elements.Elements.UI_ELEMENTS)
                    System.out.println(e.ID);
            }
        });
        button1.setText("А это кнопка");

        Text test = new Text();
        test.setSize(BLOCK_SIZE, BLOCK_SIZE);
        test.setText("Это не кнопка");

        Image img1 = new Image();
        Image img2 = new Image();
        //img1.setImg(TEXTURES.stone_shovel);
        //img2.setImg(TEXTURES.apple);

        testLayout.addElements(img1, test, button1, img2);

        mainLayout.addElements(testLayout);

        updateElementsList();
        //testSkin = new ElementSkin(TEXTURES.round_up, 3);
    }

    private ElementSkin testSkin;

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
        testSkin.draw(100, 100, 400, 100);
    }
}
