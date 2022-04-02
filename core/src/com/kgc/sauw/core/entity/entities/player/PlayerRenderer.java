package com.kgc.sauw.core.entity.entities.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.entity.AbstractEntityRenderer;
import com.kgc.sauw.core.entity.Entity;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.core.environment.Environment.getWorld;

public class PlayerRenderer implements AbstractEntityRenderer {
    public static final PlayerRenderer INSTANCE = new PlayerRenderer();

    private final Animator animator = new Animator();
    private TextureRegion currentFrame;
    private final Sprite sprite = new Sprite();

    public PlayerRenderer() {
        animator.addAnimationRegion("animation_region:player", Resource.getTexture("entity/player.png"), 4, 3);
        animator.addAnimation("animation:player_walk_left", "animation_region:player", 0.2f, 0, 1);
        animator.addAnimation("animation:player_walk_right", "animation_region:player", 0.2f, 2, 3);
        animator.addAnimation("animation:player_walk_down", "animation_region:player", 0.2f, 4, 5);
        animator.addAnimation("animation:player_walk_up", "animation_region:player", 0.2f, 6, 7);

        sprite.setRegion(animator.getFrames("animation_region:player")[4]);
    }

    @Override
    public void render(Entity entity, SpriteBatch batch) {
        float AL = 0.75f - (Maths.module(720 - getWorld().worldTime.getTime()) / (720 / 0.3f));
        Player player = (Player) entity;

        int angle = player.getMoveAngle();

        if (player.moving()) {
            if (angle < 315 && angle > 225) {
                player.rotation = 0;
            } else if (angle < 225 && angle > 135) {
                player.rotation = 1;
            } else if (angle > 45 && angle < 135) {
                player.rotation = 2;
            } else if (angle < 45 || angle > 315) {
                player.rotation = 3;
            }
        }
        if (player.rotation == 0) {
            currentFrame = animator.getFrame("animation:player_walk_up");
        } else if (player.rotation == 1) {
            currentFrame = animator.getFrame("animation:player_walk_right");
        } else if (player.rotation == 2) {
            currentFrame = animator.getFrame("animation:player_walk_down");
        } else if (player.rotation == 3) {
            currentFrame = animator.getFrame("animation:player_walk_left");
        }

        if (!player.moving()) {
            if (player.rotation == 1) {
                currentFrame = animator.getFrames("animation_region:player")[3];
            } else if (player.rotation == 3) {
                currentFrame = animator.getFrames("animation_region:player")[1];
            } else if (player.rotation == 0) {
                currentFrame = animator.getFrames("animation_region:player")[9];
            } else if (player.rotation == 2) {
                currentFrame = animator.getFrames("animation_region:player")[8];
            }
        }
        sprite.setRegion(currentFrame);
        sprite.setPosition(player.getPosition().x, player.getPosition().y + player.getHeight() / 3);
        sprite.setSize(entity.getWidth(), entity.getHeight());
        batch.setColor(1f, 1f, 1f, AL);
        //batch.draw(TEXTURES.shadow, body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 2f, entityBodyW, entityBodyH);
        batch.setColor(1f, 1f, 1f, 1f);
        sprite.draw(batch);
    }
}
