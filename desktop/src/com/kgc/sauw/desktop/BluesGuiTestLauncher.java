package com.kgc.sauw.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.kgc.bluesgui.Graphic;
import com.kgc.bluesgui.Interface;
import com.kgc.bluesgui.TextInputProcessor;

import static com.kgc.bluesgui.Graphic.BATCH;
import static com.kgc.bluesgui.Graphic.INTERFACE_CAMERA;

public class BluesGuiTestLauncher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 840;
        config.height = 480;
        config.title = "BLUES";
        new LwjglApplication(new Test(), config);
    }

    private static class Test implements ApplicationListener {

        Interface testInterface;

        @Override
        public void create() {
            Gdx.input.setInputProcessor(TextInputProcessor.INSTANCE);
            testInterface = new Interface();
            testInterface.open();
        }

        @Override
        public void resize(int width, int height) {
            Graphic.resize(width, height);
        }

        @Override
        public void render() {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            testInterface.update();
            INTERFACE_CAMERA.update(BATCH);
            BATCH.begin();
            testInterface.render();
            BATCH.end();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void dispose() {
            Graphic.dispose();
        }
    }
}
