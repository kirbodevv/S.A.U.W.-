package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.WorldLoader;
import com.kgc.sauw.game.Game;

import static com.kgc.sauw.game.gui.GameInterfaces.CREATE_NEW_WORLD_INTERFACE;

@RegistryMetadata(package_ = "sauw", id = "select_world")
public class SelectWorldInterface extends Interface {
    private final Button sel_0;
    private final Button sel_1;
    private final Button sel_2;

    private int worldSelIndex = 0;


    public SelectWorldInterface() {

        InterfaceUtils.createFromXml(Gdx.files.internal("xml/SelectWorldInterface.xml"), this);
        sel_0 = (Button) getElement("button.SELECT_0");
        sel_0.addEventListener(() -> loadGame(WorldLoader.worldNames[worldSelIndex]));
        sel_1 = (Button) getElement("button.SELECT_1");
        sel_1.addEventListener(() -> loadGame(WorldLoader.worldNames[worldSelIndex + 1]));
        sel_2 = (Button) getElement("button.SELECT_2");
        sel_2.addEventListener(() -> loadGame(WorldLoader.worldNames[worldSelIndex + 2]));


        /*ListView listView = new ListView();
        listView.setSizeInBlocks(5, 4);
        mainLayout.addElements(listView);*/
        Button up = (Button) getElement("button.UP");
        up.setIcon(Resource.getTexture("interface/button_up_0.png"));
        up.addEventListener(() -> {
            worldSelIndex--;
            if (worldSelIndex < 0) worldSelIndex = 0;
            hideButtonsIfNeed();
            setSelectButtonsText();
        });
        Button down = (Button) getElement("button.DOWN");
        down.setIcon(Resource.getTexture("interface/button_down_0.png"));
        down.addEventListener(() -> {
            worldSelIndex++;
            if (worldSelIndex >= WorldLoader.worldNames.length)
                worldSelIndex = WorldLoader.worldNames.length - 1;
            hideButtonsIfNeed();
            setSelectButtonsText();
        });
        CREATE_NEW_WORLD_INTERFACE.create.addEventListener(() -> {
            for (String name : WorldLoader.worldNames) {
                if (CREATE_NEW_WORLD_INTERFACE.worldName.getText().equals(name)) return;
            }
            loadGame(CREATE_NEW_WORLD_INTERFACE.worldName.getText());
        });

        Button createNewWorld = (Button) getElement("button.create_new_world");
        createNewWorld.setDefaultText();
        createNewWorld.addEventListener(CREATE_NEW_WORLD_INTERFACE::open);
    }

    @Override
    public void update() {
        if (!CREATE_NEW_WORLD_INTERFACE.isOpen) super.update();
        CREATE_NEW_WORLD_INTERFACE.update();
    }

    @Override
    public void tick() {
        hideButtonsIfNeed();
        setSelectButtonsText();
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
        CREATE_NEW_WORLD_INTERFACE.render();
    }

    public void loadGame(String worldName) {
        Game.load(worldName);
    }

    public void setSelectButtonsText() {
        if (!sel_0.isHidden() && worldSelIndex < WorldLoader.worldNames.length)
            sel_0.setText(WorldLoader.worldNames[worldSelIndex]);
        if (!sel_1.isHidden() && worldSelIndex + 1 < WorldLoader.worldNames.length)
            sel_1.setText(WorldLoader.worldNames[worldSelIndex + 1]);
        if (!sel_2.isHidden() && worldSelIndex + 2 < WorldLoader.worldNames.length)
            sel_2.setText(WorldLoader.worldNames[worldSelIndex + 2]);
    }

    public void hideButtonsIfNeed() {
        sel_0.hide(worldSelIndex >= WorldLoader.worldNames.length);
        sel_1.hide(worldSelIndex + 1 >= WorldLoader.worldNames.length);
        sel_2.hide(worldSelIndex + 2 >= WorldLoader.worldNames.length);
    }
}