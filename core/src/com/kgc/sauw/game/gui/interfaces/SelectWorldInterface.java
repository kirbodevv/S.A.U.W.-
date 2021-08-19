package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.WorldLoader;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.game.SAUW;

public class SelectWorldInterface extends Interface {
    private final Button sel_0;
    private final Button sel_1;
    private final Button sel_2;
    private final CreateNewWorldInterface createWorldInterface;

    private int worldSelIndex = 0;


    public SelectWorldInterface() {
        super("SELECT_WORLD_INTERFACE");

        createFromXml(Gdx.files.internal("xml/SelectWorldInterface.xml"));
        sel_0 = (Button) getElement("SELECT_0");
        sel_0.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(WorldLoader.worldNames[worldSelIndex]);
            }
        });
        sel_1 = (Button) getElement("SELECT_1");
        sel_1.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(WorldLoader.worldNames[worldSelIndex + 1]);
            }
        });
        sel_2 = (Button) getElement("SELECT_2");
        sel_2.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                loadGame(WorldLoader.worldNames[worldSelIndex + 2]);
            }
        });


        /*ListView listView = new ListView();
        listView.setSizeInBlocks(5, 4);
        mainLayout.addElements(listView);*/
        Button up = (Button) getElement("UP");
        up.setIcon(Resource.getTexture("Interface/button_up_0.png"));
        up.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                worldSelIndex--;
                if (worldSelIndex < 0) worldSelIndex = 0;
                hideButtonsIfNeed();
                setSelectButtonsText();
            }
        });
        Button down = (Button) getElement("DOWN");
        down.setIcon(Resource.getTexture("Interface/button_down_0.png"));
        down.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                worldSelIndex++;
                if (worldSelIndex >= WorldLoader.worldNames.length)
                    worldSelIndex = WorldLoader.worldNames.length - 1;
                hideButtonsIfNeed();
                setSelectButtonsText();
            }
        });
        createWorldInterface = new CreateNewWorldInterface();
        createWorldInterface.create.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                for (String name : WorldLoader.worldNames) {
                    if (createWorldInterface.worldName.input.equals(name)) return;
                }
                loadGame(createWorldInterface.worldName.input);
            }
        });

        Button createNewWorld = (Button) getElement("CREATE_NEW_WORLD");
        createNewWorld.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                createWorldInterface.open();
            }
        });
        hideButtonsIfNeed();
        setSelectButtonsText();
    }

    @Override
    public void update() {
        if (!createWorldInterface.isOpen) super.update();
        createWorldInterface.update();
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
        createWorldInterface.render();
    }

    public void loadGame(String worldName) {
        MainGame.getGame().setScreen(new SAUW(worldName));
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