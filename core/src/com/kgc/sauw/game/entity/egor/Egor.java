package com.kgc.sauw.game.entity.egor;

import com.kgc.sauw.core.entity.AbstractEntityRenderer;
import com.kgc.sauw.core.entity.entities.npc.Npc;

import static com.kgc.sauw.game.gui.Interfaces.EGOR_INTERFACE;

public class Egor extends Npc {


    public Egor() {
        //currentFrame = new TextureRegion(Resource.getTexture("Blocks/undefined.png"));
    }

    @Override
    public void tick() {
    }

    @Override
    public AbstractEntityRenderer getEntityRenderer() {
        return new EgorRenderer();
    }

    @Override
    public void interact() {
        EGOR_INTERFACE.open();
    }

    @Override
    public void onDead() {

    }

    /*@Override
    public void render() {
        BATCH.draw(currentFrame, body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 8f, entityBodyW, entityBodyH);
    }*/
}
