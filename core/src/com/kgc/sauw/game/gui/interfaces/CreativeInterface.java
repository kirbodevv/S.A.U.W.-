package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.creative_categories.Category;
import com.kgc.sauw.core.creative_categories.CreativeCategories;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

@RegistryMetadata(package_ = "sauw", id = "creative")
public class CreativeInterface extends Interface {

    private int currentTab = 0;

    private Button category0;
    private Button category1;
    private Button category2;

    private Button nTab;
    private Button pTab;

    private Slot[] itemSlots = new Slot[20];
    private Container[] containers = new Container[20];
    private int currentCategory = 0;

    public CreativeInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/CreativeInterface.xml"), this);
        getElement("layout.tabs").setSizeInBlocks(5, 1);

        pTab = (Button) getElement("button.left");
        nTab = (Button) getElement("button.right");
        category0 = (Button) getElement("button.category_0");
        category1 = (Button) getElement("button.category_1");
        category2 = (Button) getElement("button.category_2");

        category0.addEventListener(() -> setCategory(currentTab * 3));
        category1.addEventListener(() -> setCategory(currentTab * 3 + 1));
        category2.addEventListener(() -> setCategory(currentTab * 3 + 2));

        pTab.setIcon(Resource.getTexture("interface/button_left_0.png"));
        nTab.setIcon(Resource.getTexture("interface/button_right_0.png"));

        pTab.addEventListener(this::previousTab);
        nTab.addEventListener(this::nextTab);

        for (int i = 0; i < 20; i++) {
            itemSlots[i] = (Slot) getElement("slot.item_" + i);
            int finalI = i;
            containers[i] = new Container();
            itemSlots[i].setSF(new Slot.SlotFunctions() {
                @Override
                public boolean isValid(Container container, String fromSlotWithId) {
                    return false;
                }

                @Override
                public void onClick() {
                    int id = itemSlots[finalI].getContainer().id;
                    int count = itemSlots[finalI].getContainer().count;
                    PLAYER.inventory.addItem(id, count);
                }

                @Override
                public boolean possibleToDrag() {
                    return false;
                }

                @Override
                public void onItemSwapping(Container fromContainer) {
                }
            });
        }

    }

    @Override
    public void tick() {
        setCategory(currentCategory);
    }

    @Override
    public void onOpen() {
        currentTab = 0;
        setCategory(0);
        updateCategories();
    }

    private void nextTab() {
        if ((currentTab + 1) * 3 < CreativeCategories.registry.getObjects().size()) {
            currentTab++;
            updateCategories();
        }
    }

    private void previousTab() {
        if (currentTab > 0) {
            currentTab--;
            updateCategories();
        }
    }

    private void updateCategories() {
        category1.hide(currentTab * 3 + 1 >= CreativeCategories.registry.getObjects().size());
        category2.hide(currentTab * 3 + 2 >= CreativeCategories.registry.getObjects().size());

        category0.setIcon(CreativeCategories.registry.get(currentTab * 3).getIcon());
        if (!category1.isHidden())
            category1.setIcon(CreativeCategories.registry.get(currentTab * 3 + 1).getIcon());
        if (!category2.isHidden())
            category2.setIcon(CreativeCategories.registry.get(currentTab * 3 + 2).getIcon());
    }

    public void setCategory(int category) {
        currentCategory = category;
        Category category_ = CreativeCategories.registry.get(category);
        for (int i = 0; i < 20; i++) {
            if (i < category_.getItems().size) {
                int id = category_.getItems().get(i);
                itemSlots[i].setContainer(containers[i]);
                itemSlots[i].getContainer().id = id;
                itemSlots[i].getContainer().count = GameContext.getItem(id).getItemConfiguration().maxCount;
            }
        }
    }
}
