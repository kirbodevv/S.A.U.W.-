package com.kgc.sauw.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

@RegistryMetadata(package_ = "sauw", id = "torch")
public class Torch extends Item {
    Animator animator = new Animator();

    public Torch() {
        setTexture(Resource.getTexture("items/torch_anim.png"));

        animator.addAnimationRegion("animation_region:torch", Resource.getTexture("items/torch_anim.png"), 4, 1);
        animator.addAnimation("animation:torch", "animation_region:torch", 0.2f, 1, 2, 3);

        itemConfiguration.weight = 0.25f;
    }

    @Override
    public Sprite getTexture(Container container) {
        sprite.setRegion(animator.getFrame("animation:torch"));
        return sprite;
    }

    @Override
    public void tick() {
        PLAYER.setLightActive(PLAYER.getCarriedItem().getId() == id);
    }
}
