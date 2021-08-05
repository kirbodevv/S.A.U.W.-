package com.kgc.sauw.game.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

public class Torch extends Item {
    Animator animator = new Animator();

    public Torch() {
        super(ID.registeredId("item:torch"));

        setTexture(Resource.getTexture("Items/torch_anim.png"));

        animator.addAnimationRegion("animation_region:torch", Resource.getTexture("Items/torch_anim.png"), 4, 1);
        animator.addAnimation("animation:torch", "animation_region:torch", 0.2f, 1, 2, 3);

        itemConfiguration.name = Languages.getString("torch");
        itemConfiguration.weight = 0.25f;
    }

    @Override
    public TextureRegion getTextureRegion(Container container) {
        return animator.getFrame("animation:torch");
    }

    @Override
    public void tick() {
        PLAYER.setLightActive(PLAYER.getCarriedItem().id == id);
    }
}
