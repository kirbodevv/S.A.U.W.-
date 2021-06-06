package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.resource.TextureGenerator;
import com.kgc.sauw.core.utils.Camera2D;

import java.util.ArrayList;
import java.util.Arrays;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;

public class Layout extends InterfaceElement {
    public enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public enum Gravity {
        RIGHT, LEFT, TOP, BOTTOM, CENTER_VERTICAL, CENTER_HORIZONTAL
    }

    public enum Size {
        WRAP_CONTENT, FIXED_SIZE
    }

    Texture Background;

    private Orientation orientation;
    private Gravity gravity;
    private Size sizeX, sizeY;

    public ArrayList<InterfaceElement> elements;

    public Layout(Orientation orientation) {
        this.orientation = orientation;
        sizeX = Size.WRAP_CONTENT;
        sizeY = Size.WRAP_CONTENT;

        elements = new ArrayList<>();
        gravity = Gravity.LEFT;

        updateSize();
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setBackground(Texture background) {
        Background = background;
    }

    public void generateBackground(boolean b) {
        setBackground(TextureGenerator.generateTexture(width / BLOCK_SIZE, height / BLOCK_SIZE, b));
    }

    @Override
    public void tick(Camera2D cam) {
        for (InterfaceElement element : elements) {
            element.update(cam);
        }
    }

    public void updateSize() {
        if (sizeX == Size.WRAP_CONTENT || sizeY == Size.WRAP_CONTENT) {
            float minX = 0, minY = 0, maxX = 0, maxY = 0;

            for (int i = 0; i < elements.size(); i++) {
                InterfaceElement e = elements.get(i);
                if (i == 0) {
                    minX = e.x;
                    maxX = e.x + e.width;
                    minY = e.y;
                    maxY = e.y + e.height;
                } else {
                    minX = Math.min(e.x, minX);
                    minY = Math.min(e.y, minY);
                    maxX = Math.max(e.x + e.width, maxX);
                    maxY = Math.max(e.y + e.height, maxY);
                }
            }
            if (sizeX == Size.WRAP_CONTENT) width = maxX - minX;
            if (sizeY == Size.WRAP_CONTENT) height = maxY - minY;
        }
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        if (Background != null) batch.draw(Background, x, y, width, height);
        for (InterfaceElement element : elements) {
            element.render(batch, cam);
        }
    }

    @Override
    public void hide(boolean b) {
        super.hide(b);
        for (InterfaceElement e : elements) {
            e.hide(b);
        }
        if (!b) {
            setElementsPosition();
            updateSize();
        }
    }

    public void setSize(Size sizeX, Size sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        updateSize();
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        updateSize();
    }

    private void setElementsPosition() {
        Sides layoutAttachSide = null;
        Sides elementAttachSide = null;
        if (orientation == Orientation.VERTICAL) {
            if (gravity == Gravity.TOP) {
                layoutAttachSide = Sides.TOP;
                elementAttachSide = Sides.BOTTOM;
            } else if (gravity == Gravity.BOTTOM) {
                layoutAttachSide = Sides.BOTTOM;
                elementAttachSide = Sides.TOP;
            }
        } else if (orientation == Orientation.HORIZONTAL) {
            if (gravity == Gravity.RIGHT) {
                layoutAttachSide = Sides.RIGHT;
                elementAttachSide = Sides.LEFT;
            } else if (gravity == Gravity.LEFT) {
                layoutAttachSide = Sides.LEFT;
                elementAttachSide = Sides.RIGHT;
            }
        }
        for (int i = 0; i < elements.size(); i++) {
            if (i == 0) {
                elements.get(i).attachTo(this, layoutAttachSide, layoutAttachSide);
            } else {
                elements.get(i).attachTo(elements.get(i - 1), layoutAttachSide, elementAttachSide);
            }
        }
    }

    public void addElements(InterfaceElement... Elements) {
        elements.addAll(Arrays.asList(Elements));
        setElementsPosition();
        updateSize();
    }

    public InterfaceElement getElement(String ID) {
        for (InterfaceElement e : elements) {
            if (ID.equals(e.ID)) return e;
        }
        return null;
    }

    public ArrayList<InterfaceElement> getAllElements() {
        ArrayList<InterfaceElement> elements = new ArrayList<>();
        for (InterfaceElement e : this.elements) {
            elements.add(e);
            if (e instanceof Layout) elements.addAll(((Layout) e).getAllElements());
        }
        return elements;
    }

    @Override
    public void resize() {
        super.resize();
        for (InterfaceElement e : elements) {
            e.resize();
        }
    }
}