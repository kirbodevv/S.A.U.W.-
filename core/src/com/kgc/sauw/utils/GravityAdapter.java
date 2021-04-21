package com.kgc.sauw.utils;

import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.UI.InterfaceElement;

public class GravityAdapter {
    public static Vector2 getPosition(InterfaceElement element, InterfaceElement attachedTo, InterfaceElement.Sides attachableSide, InterfaceElement.Sides attachTo) {
        float attachableSideX = 0, attachableSideY = 0;
        float attachToX = 0, attachToY = 0;

        if (attachableSide == InterfaceElement.Sides.RIGHT) {
            attachableSideX = element.width;
            attachableSideY = element.height / 2;
        } else if (attachableSide == InterfaceElement.Sides.LEFT) {
            attachableSideX = 0;
            attachableSideY = element.height / 2;
        } else if (attachableSide == InterfaceElement.Sides.TOP) {
            attachableSideX = element.width / 2;
            attachableSideY = element.height;
        } else if (attachableSide == InterfaceElement.Sides.BOTTOM) {
            attachableSideX = element.width / 2;
            attachableSideY = 0;
        } else if (attachableSide == InterfaceElement.Sides.CENTER) {
            attachableSideX = element.width / 2;
            attachableSideY = element.height / 2;
        } else if (attachableSide == InterfaceElement.Sides.LEFT_TOP) {
            attachableSideX = 0;
            attachableSideY = element.height;
        } else if (attachableSide == InterfaceElement.Sides.LEFT_BOTTOM) {
            attachableSideX = 0;
            attachableSideY = 0;
        } else if (attachableSide == InterfaceElement.Sides.RIGHT_TOP) {
            attachableSideX = element.width;
            attachableSideY = element.height;
        } else if (attachableSide == InterfaceElement.Sides.RIGHT_BOTTOM) {
            attachableSideX = element.width;
            attachableSideY = 0;
        }
        if (attachTo == InterfaceElement.Sides.LEFT) {
            attachToX = attachedTo.X - attachableSideX;
            attachToY = attachedTo.Y + attachedTo.height / 2 - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.RIGHT) {
            attachToX = attachedTo.X + attachedTo.width - attachableSideX;
            attachToY = attachedTo.Y + attachedTo.height / 2 - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.TOP) {
            attachToX = attachedTo.X + attachedTo.width / 2 - attachableSideX;
            attachToY = attachedTo.Y + attachedTo.height - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.BOTTOM) {
            attachToX = attachedTo.X + attachedTo.width / 2 - attachableSideX;
            attachToY = attachedTo.Y - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.LEFT_TOP) {
            attachToX = attachedTo.X - attachableSideX;
            attachToY = attachedTo.Y + attachedTo.height - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.LEFT_BOTTOM) {
            attachToX = attachedTo.X - attachableSideX;
            attachToY = attachedTo.Y - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.RIGHT_TOP) {
            attachToX = attachedTo.X + attachedTo.width - attachableSideX;
            attachToY = attachedTo.Y + attachedTo.height - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.RIGHT_BOTTOM) {
            attachToX = attachedTo.X + attachedTo.width - attachableSideX;
            attachToY = attachedTo.Y - attachableSideY;
        } else if (attachTo == InterfaceElement.Sides.CENTER) {
            attachToX = attachedTo.X + attachedTo.width / 2 - attachableSideX;
            attachToY = attachedTo.Y + attachedTo.height / 2 - attachableSideY;
        }
        return new Vector2(attachToX, attachToY);
    }
}
