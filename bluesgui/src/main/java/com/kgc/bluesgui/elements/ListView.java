package com.kgc.bluesgui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;

public class ListView extends InterfaceElement {
    private Layout viewLayout;
    private ListViewAdapter adapter;

    private final Slider slider;

    private float yPosition;

    private FrameBuffer fbo;

    public ListView() {
        slider = new Slider();
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        setSliderPosAndSize();
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) w, (int) h, false);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setSliderPosAndSize();
    }

    private void setSliderPosAndSize() {
        slider.setSizeInBlocks(0.5f, BHeight - 0.5f);
        slider.setTranslationX(-0.25f);
        slider.attachTo(this, Sides.RIGHT, Sides.RIGHT);
        slider.setMaxValue(100);
    }

    public void setViewLayout(Layout viewLayout) {
        this.viewLayout = viewLayout;
    }

    public void setAdapter(ListViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void tick(Camera2D cam) {
        slider.update(cam);
    }

    @Override
    protected void renderTick(SpriteBatch batch, Camera2D cam) {
        Skins.round_down.draw(x, y, width, height);
        slider.render(batch, cam);

        batch.end();
        fbo.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        Skins.round_down.draw(0, 0, BLOCK_SIZE, BLOCK_SIZE);
        batch.end();
        fbo.end();
        batch.begin();

        batch.draw(fbo.getColorBufferTexture(), cam.X + x, cam.Y + y);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }

    public interface ListViewAdapter {
        void getView(int position, Layout view);

        void onClick(int position, Layout view);
    }
}
