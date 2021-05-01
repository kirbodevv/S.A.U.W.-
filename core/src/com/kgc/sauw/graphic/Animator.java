package com.kgc.sauw.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.utils.ID;

import java.util.HashMap;

public class Animator {
    private final HashMap<Integer, Animation> animations = new HashMap<>();
    private final TextureRegion[] frames;
    private static float stateTime = 0f;

    public Animator(Texture animationTexture, int xFramesCount, int yFramesCount) {
        TextureRegion[][] tmp = TextureRegion.split(animationTexture, animationTexture.getWidth() / xFramesCount, animationTexture.getHeight() / yFramesCount);
        frames = new TextureRegion[xFramesCount * yFramesCount];
        int index = 0;
        for (int i = 0; i < yFramesCount; i++) {
            for (int j = 0; j < xFramesCount; j++) {
                frames[index++] = tmp[i][j];
            }
        }
    }
    public TextureRegion[] getFrames(){
        return frames;
    }
    public void addAnimation(String id, float frameDuration, int... frameNumbers) {
        TextureRegion[] frames = new TextureRegion[frameNumbers.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = this.frames[frameNumbers[i]];
        }
        animations.put(ID.registeredId(id), new Animation(frameDuration, frames));
    }

    public static void update() {
        stateTime += Gdx.graphics.getRawDeltaTime();
    }

    public TextureRegion getFrame(String id) {
        return animations.get(ID.get(id)).getKeyFrame(stateTime, true);
    }
}
