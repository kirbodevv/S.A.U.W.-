package com.kgc.sauw.ui.interfaces;

import com.kgc.sauw.ui.elements.Button;
import com.kgc.sauw.ui.elements.Image;
import com.kgc.sauw.ui.elements.Layout;
import com.kgc.sauw.ui.elements.Text;
import com.kgc.sauw.ui.Interface;
import com.kgc.sauw.ui.InterfaceElement;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class TestInterface extends Interface {
    public TestInterface() {
        super("TEST_INTERFACE");

        setHeaderText("TOP SECRET");


        Layout testLayout = new Layout(Layout.Orientation.HORIZONTAL);
        testLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        testLayout.setGravity(Layout.Gravity.LEFT);

        Button button1 = new Button("", 0, 0, BLOCK_SIZE * 2, BLOCK_SIZE);

        button1.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                for (InterfaceElement e : com.kgc.sauw.ui.elements.Elements.UI_ELEMENTS)
                    System.out.println(e.ID);
            }
        });
        button1.setText("А это кнопка");

        Text test = new Text();
        test.setSize(BLOCK_SIZE, BLOCK_SIZE);
        test.setText("Это не кнопка");

        Image img1 = new Image(0, 0, BLOCK_SIZE / 2, BLOCK_SIZE / 2);
        Image img2 = new Image(0, 0, BLOCK_SIZE / 2, BLOCK_SIZE / 2);
        img1.setImg(TEXTURES.stone_shovel);
        img2.setImg(TEXTURES.apple);

        testLayout.addElements(img1, test, button1, img2);

        MainLayout.addElements(testLayout);

        updateElementsList();
    }
}
