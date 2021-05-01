package com.kgc.sauw.ui.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.ui.InterfaceElement;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;

import java.util.ArrayList;
import java.util.Arrays;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.graphic.Graphic.INTERFACE_CAMERA;

public class Layout extends InterfaceElement {
    public enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public enum Gravity {
        RIGHT, LEFT, TOP, BOTTOM, CENTER_VERTICAL, CENTER_HORIZONTAL
    }

    public enum Size {
        MATCH_PARENT, WRAP_CONTENT, FIXED_SIZE
    }

    Texture Background;

    private Orientation orientation;
    private Gravity gravity;
    private Size sizeX, sizeY;

    public ArrayList<InterfaceElement> elements;

    private Sides LayoutAttachSide = null;
    private Sides ElementAttachSide = null;

    public Layout(Orientation orientation) {
        this.orientation = orientation;
        sizeX = Size.MATCH_PARENT;
        sizeY = Size.MATCH_PARENT;

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
        if (Background != null) Background.dispose();
        Background = background;
    }

    public void generateBackground(boolean b) {
        setBackground(Textures.generateTexture(width / BLOCK_SIZE, height / BLOCK_SIZE, b));
    }

    @Override
    public void update(Camera2D cam) {
        if (!hided) {
            super.update(cam);
            for (InterfaceElement element : elements) {
                element.update(cam);
            }
        }
    }

    public void updateSize() {
        if (attachedTo == null) {
            if (sizeX == Size.MATCH_PARENT) {
                width = INTERFACE_CAMERA.W;
                X = 0;
            }
            if (sizeY == Size.MATCH_PARENT) {
                height = INTERFACE_CAMERA.H;
                Y = 0;
            }
        } else {
            if (sizeX == Size.MATCH_PARENT) {
                width = attachedTo.width;
                X = attachedTo.X;
            }
            if (sizeY == Size.MATCH_PARENT) {
                height = attachedTo.height;
                Y = attachedTo.Y;
            }
        }
        if (sizeX == Size.WRAP_CONTENT || sizeY == Size.WRAP_CONTENT) {
            float minX = 0, minY = 0, maxX = 0, maxY = 0;

            for (int i = 0; i < elements.size(); i++) {
                InterfaceElement e = elements.get(i);
                if (i == 0) {
                    minX = e.X;
                    maxX = e.X + e.width;
                    minY = e.Y;
                    maxY = e.Y + e.height;
                } else {
                    minX = Math.min(e.X, minX);
                    minY = Math.min(e.Y, minY);
                    maxX = Math.max(e.X + e.width, maxX);
                    maxY = Math.max(e.Y + e.height, maxY);
                }
            }
            if (sizeX == Size.WRAP_CONTENT) width = maxX - minX;
            if (sizeY == Size.WRAP_CONTENT) height = maxY - minY;
        }
    }

    @Override
    public void render(SpriteBatch batch, Camera2D cam) {
        if (!hided) {
            if (Background != null) batch.draw(Background, X, Y, width, height);
            for (InterfaceElement element : elements) {
                element.render(batch, cam);
            }
        }
    }

    @Override
    public void hide(boolean b) {
        super.hide(b);
        if (!b) {
            setElementsPosition();
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
        if (orientation == Orientation.VERTICAL) {
            if (gravity == Gravity.TOP) {
                LayoutAttachSide = Sides.TOP;
                ElementAttachSide = Sides.BOTTOM;
            } else if (gravity == Gravity.BOTTOM) {
                LayoutAttachSide = Sides.BOTTOM;
                ElementAttachSide = Sides.TOP;
            }
        } else if (orientation == Orientation.HORIZONTAL) {
            if (gravity == Gravity.RIGHT) {
                LayoutAttachSide = Sides.RIGHT;
                ElementAttachSide = Sides.LEFT;
            } else if (gravity == Gravity.LEFT) {
                LayoutAttachSide = Sides.LEFT;
                ElementAttachSide = Sides.RIGHT;
            }
        }
        for (int i = 0; i < elements.size(); i++) {
            if (i == 0) {
                elements.get(i).attachTo(this, LayoutAttachSide, LayoutAttachSide);
            } else {
                elements.get(i).attachTo(elements.get(i - 1), LayoutAttachSide, ElementAttachSide);
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
}