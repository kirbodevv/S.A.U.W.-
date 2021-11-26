package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

import static com.kgc.sauw.core.GameContext.SAUW;
import static com.kgc.sauw.core.graphic.Graphic.*;

public class Slot extends InterfaceElement {
    private Container container;

    public ElementSkin slot;

    private TextureRegion iconRegion;

    public boolean isInventorySlot = false;
    public int inventorySlot;


    private float itemX;
    private float itemY;
    public SlotFunctions SF = null;

    public void setSF(SlotFunctions SF) {
        this.SF = SF;
    }

    private final Interface interface_;
    public static final ProgressBar itemDamageProgressBar;

    private Color textColor = null;

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    static {
        itemDamageProgressBar = new ProgressBar(true);
        itemDamageProgressBar.setSizeInBlocks(2, 0.5f);
    }

    public Slot(String ID, Interface interface_) {
        this.interface_ = interface_;
        this.id = ID;
        slot = Skins.slot_round;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public void tick(Camera2D cam) {
        float toItemX;
        float toItemY;
        if (isTouched() && (SF == null || SF.possibleToDrag())) {
            toItemX = (Gdx.input.getX() + cam.X - height / 4);
            toItemY = (Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4);
        } else {
            toItemX = cam.X + x;
            toItemY = cam.Y + y;
        }
        itemX = isTouched() ? MathUtils.lerp(itemX, toItemX, 0.05f) : toItemX;
        itemY = isTouched() ? MathUtils.lerp(itemY, toItemY, 0.05f) : toItemY;
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        slot.draw(cam.X + x, cam.Y + y, width, height);
        if (iconRegion != null && container.id == 0) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(iconRegion, cam.X + x, cam.Y + y, width, height);
            batch.setColor(1, 1, 1, 1);
        }
        if (container != null && container.id != 0 && GameContext.getItem(container.id).getItemConfiguration().maxDamage != 0 &&
                Maths.isLiesOnRect(x, y, width, height, Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY()) &&
                !Gdx.input.isTouched()) {
            itemDamageProgressBar.hide(false);
            itemDamageProgressBar.setPosition(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY() - itemDamageProgressBar.height);
            itemDamageProgressBar.setColor(0, 255, 0);
            itemDamageProgressBar.setMaxValue(GameContext.getItem(container.id).getItemConfiguration().maxDamage);
            itemDamageProgressBar.setValue(GameContext.getItem(container.id).getItemConfiguration().maxDamage - container.damage);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
        if (SF != null && onElement) {
            SF.onClick();
        }
        if (container != null && interface_ != null) {
            for (Slot slot : interface_.slots) {
                if (!slot.id.equals(this.id) && Maths.isLiesOnRect(slot.x, slot.y, slot.width, slot.height, INTERFACE_CAMERA.touchX(), INTERFACE_CAMERA.touchY())) {
                    InterfaceUtils.sendToSlot(this, slot);
                }
            }
        }
        itemX = INTERFACE_CAMERA.X + x;
        itemY = INTERFACE_CAMERA.Y + y;
    }

    public void itemRender() {
        if (container != null && container.id != 0) {
            BATCH.draw(GameContext.getItem(container.id).getTexture(container), itemX + BLOCK_SIZE / 8f, itemY + BLOCK_SIZE / 8f, width - BLOCK_SIZE / 4f, height - BLOCK_SIZE / 4f);
            BITMAP_FONT.getData().setScale((width / 3f) / BITMAP_FONT_CAP_HEIGHT);
            GLYPH_LAYOUT.setText(BITMAP_FONT, container.count + "");
            Graphic.setTextColor(textColor == null ? TEXT_COLOR : textColor);
            BITMAP_FONT.getColor().a = COLOR_ALPHA;
            BITMAP_FONT.draw(BATCH, container.count + "", itemX, itemY + GLYPH_LAYOUT.height + width / 32f, width, Align.right, false);
        }
    }

    public void setIcon(TextureRegion icon) {
        this.iconRegion = icon;
    }

    public void setIcon(Texture icon) {
        this.iconRegion = new TextureRegion(icon);
    }

    public void setIconFromItem(String id) {
        setIcon(GameContext.getItem(SAUW.getId(id)).getTexture(null));
    }

    public interface SlotFunctions {
        boolean isValid(Container container, String fromSlotWithId);

        void onClick();

        boolean possibleToDrag();

        void onItemSwapping(Container fromContainer);
    }
}