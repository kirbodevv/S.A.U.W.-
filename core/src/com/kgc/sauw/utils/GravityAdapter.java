package com.kgc.sauw.utils;

import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.ui.InterfaceElement;

public class GravityAdapter {
    public static Vector2 getPosition(InterfaceElement element, InterfaceElement attachedTo, InterfaceElement.Sides attachableSide, InterfaceElement.Sides attachTo) {
        float attachableSideX = 0, attachableSideY = 0;
        float attachToX = 0, attachToY = 0;

        switch (attachableSide) {
            case RIGHT:
                attachableSideX = element.width;
                attachableSideY = element.height / 2;
                break;
            case LEFT:
                attachableSideX = 0;
                attachableSideY = element.height / 2;
                break;
            case TOP:
                attachableSideX = element.width / 2;
                attachableSideY = element.height;
                break;
            case BOTTOM:
                attachableSideX = element.width / 2;
                attachableSideY = 0;
                break;
            case CENTER:
                attachableSideX = element.width / 2;
                attachableSideY = element.height / 2;
                break;
            case LEFT_TOP:
                attachableSideX = 0;
                attachableSideY = element.height;
                break;
            case LEFT_BOTTOM:
                attachableSideX = 0;
                attachableSideY = 0;
                break;
            case RIGHT_TOP:
                attachableSideX = element.width;
                attachableSideY = element.height;
                break;
            case RIGHT_BOTTOM:
                attachableSideX = element.width;
                attachableSideY = 0;
                break;
        }
        switch (attachTo) {
            case LEFT:
                attachToX = attachedTo.X - attachableSideX;
                attachToY = attachedTo.Y + attachedTo.height / 2 - attachableSideY;
                break;
            case RIGHT:
                attachToX = attachedTo.X + attachedTo.width - attachableSideX;
                attachToY = attachedTo.Y + attachedTo.height / 2 - attachableSideY;
                break;
            case TOP:
                attachToX = attachedTo.X + attachedTo.width / 2 - attachableSideX;
                attachToY = attachedTo.Y + attachedTo.height - attachableSideY;
                break;
            case BOTTOM:
                attachToX = attachedTo.X + attachedTo.width / 2 - attachableSideX;
                attachToY = attachedTo.Y - attachableSideY;
                break;
            case LEFT_TOP:
                attachToX = attachedTo.X - attachableSideX;
                attachToY = attachedTo.Y + attachedTo.height - attachableSideY;
                break;
            case LEFT_BOTTOM:
                attachToX = attachedTo.X - attachableSideX;
                attachToY = attachedTo.Y - attachableSideY;
                break;
            case RIGHT_TOP:
                attachToX = attachedTo.X + attachedTo.width - attachableSideX;
                attachToY = attachedTo.Y + attachedTo.height - attachableSideY;
                break;
            case RIGHT_BOTTOM:
                attachToX = attachedTo.X + attachedTo.width - attachableSideX;
                attachToY = attachedTo.Y - attachableSideY;
                break;
            case CENTER:
                attachToX = attachedTo.X + attachedTo.width / 2 - attachableSideX;
                attachToY = attachedTo.Y + attachedTo.height / 2 - attachableSideY;
                break;
        }
        return new Vector2(attachToX, attachToY);
    }
}
