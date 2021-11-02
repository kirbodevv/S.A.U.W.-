package com.kgc.sauw.game.entity.npc;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.entity.Npc;
import com.kgc.sauw.core.utils.Resource;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.game.gui.Interfaces.EGOR_INTERFACE;

public class Egor extends Npc {


    public Egor() {
        currentFrame = new TextureRegion(Resource.getTexture("Blocks/undefined.png"));
    }

    @Override
    public void interact() {
        EGOR_INTERFACE.open();
    }

    @Override
    public void render() {
        BATCH.draw(currentFrame, body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 8f, entityBodyW, entityBodyH);
    }
}
