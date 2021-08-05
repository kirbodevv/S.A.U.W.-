package com.kgc.sauw.game.gui.screen;

import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.resource.TextureGenerator;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Checkbox;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.gui.elements.Slider;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import static com.kgc.sauw.core.graphic.Graphic.*;

public class ModsScreen implements Screen {
    private class Mod {
        public Texture modIcon;
        public String modName;
        public boolean isOn;

        public Mod(String modPath, boolean isOn) {
            FileHandle manifestFile = Gdx.files.external(modPath + "/manifest.json");
            this.isOn = isOn;
            try {
                JSONObject manifeat = new JSONObject(manifestFile.readString());

                modName = Gdx.files.external(modPath).name();
                modIcon = new Texture(Gdx.files.external(modPath + "/" + manifeat.getString("icon")));
            } catch (Exception e) {
                Gdx.app.log("ModInfoLoadError", e.toString());
            }
        }
    }

    private ArrayList<Mod> Mods = new ArrayList<Mod>();
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();

    private Button closeButton;
    private Slider slider;
    private ModInfo modInfo0;
    private ModInfo modInfo1;
    private ModInfo modInfo2;

    private Texture background1;

    private JSONArray modsList;

    public ModsScreen(final MenuScreen ms) {
        modsList = new JSONArray(Gdx.files.external("S.A.U.W./Mods/Mods.json").readString());
        background1 = TextureGenerator.generateTexture(13, height / (width / 16) - 1, false);
        closeButton = new Button("MODS_SCREEN_CLOSE_BUTTON", width - width / 16, height - width / 16, width / 32, width / 32);
        closeButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                MainGame.getGame().setScreen(ms);
                System.out.println("123");
            }
        });
        slider = new Slider();
        modInfo2 = new ModInfo(width / 16, width / 32 * 2, modsList);
        modInfo1 = new ModInfo(width / 16, width / 32 * 7, modsList);
        modInfo0 = new ModInfo(width / 16, width / 32 * 12, modsList);
        FileHandle modsFolder = Gdx.files.external("S.A.U.W./Mods");
        String[] mods = modsFolder.file().list();
        for (int i = 0; i < modsList.length(); i++) {
            if (!modsFolder.child(modsList.getJSONObject(i).getString("Mod")).exists())
                modsList.remove(i);
        }
        for (String mod : mods) {
            if (modsFolder.child(mod).isDirectory()) {
                if (modsFolder.child(mod).child("manifest.json").exists()) {
                    boolean isOn = true;
                    boolean modFound = false;
                    for (int j = 0; j < modsList.length(); j++) {
                        if (modsList.getJSONObject(j).getString("Mod").equals(mod)) {
                            modFound = true;
                            isOn = modsList.getJSONObject(j).getBoolean("isOn");
                        }
                    }
                    if (!modFound) {
                        JSONObject mod_ = new JSONObject();
                        mod_.put("Mod", mod);
                        mod_.put("isOn", isOn);
                        modsList.put(mod_);
                    }
                    this.Mods.add(new Mod(modsFolder.child(mod).path(), isOn));
                }
            }
        }
        saveModsListFile();
        setModsInfo(0);
        slider.setMaxValue((Mods.size() - 1) * 40);
        slider.setEventListener(new Slider.EventListener() {
            @Override
            public void onValueChange(int v1) {
                int v = (int) Math.floor(v1 / 40);
                setModsInfo(v);
            }
        });
        if (Mods.size() <= 3) slider.hide(true);
    }

    public void setModsInfo(int pos) {
        modInfo0.hide(false);
        modInfo1.hide(false);
        modInfo2.hide(false);
        if (Mods.size() > 0) {
            modInfo0.setMod(Mods.get(pos));
            if (pos + 1 < Mods.size()) modInfo1.setMod(Mods.get(pos + 1));
            else modInfo1.hide(true);
            if (pos + 2 < Mods.size()) modInfo2.setMod(Mods.get(pos + 2));
            else modInfo2.hide(true);
        } else {
            modInfo0.hide(true);
            modInfo1.hide(true);
            modInfo2.hide(true);
        }
    }

    public void saveModsListFile() {
        FileHandle modsListFile = Gdx.files.external("S.A.U.W./Mods/Mods.json");
        modsListFile.writeString(modsList.toString(), false);
    }

    @Override
    public void render(float p1) {
        closeButton.update(MENU_CAMERA);
        slider.update(MENU_CAMERA);
        modInfo0.update(MENU_CAMERA);
        modInfo1.update(MENU_CAMERA);
        modInfo2.update(MENU_CAMERA);
        BATCH.begin();
        //BATCH.draw(TEXTURES.standardBackground, MENU_CAMERA.X, MENU_CAMERA.Y, width, height);
        BATCH.draw(background1, MENU_CAMERA.X + width / 32, MENU_CAMERA.Y + width / 32, width / 16 * 13, height - (width / 16));
        closeButton.render(BATCH, MENU_CAMERA);
        modInfo0.render(BATCH, MENU_CAMERA);
        modInfo1.render(BATCH, MENU_CAMERA);
        modInfo2.render(BATCH, MENU_CAMERA);
        slider.render(BATCH, MENU_CAMERA);

        BATCH.end();
    }

    @Override
    public void resize(int p1, int p2) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public class ModInfo extends InterfaceElement {
        private int WIDTH = Gdx.graphics.getWidth();
        private Texture background;
        private Checkbox modActiv;
        private Texture modIcon;
        private String modName = "";
        private String modDescription = "";
        private BitmapFont text = new BitmapFont(Gdx.files.internal("ttf.fnt"));

        private final float ModIconHeight;
        private JSONArray modsList;

        public ModInfo(int X, int Y, final JSONArray modsList) {
            this.modsList = modsList;
            setPosition(X, Y);
            setSize(WIDTH / 16 * 12, WIDTH / 16 * 2);
            ModIconHeight = height - height / 8 * 2;
            background = TextureGenerator.generateTexture(13, 2, true);
            //modActiv = new Checkbox(TEXTURES.switch_0, TEXTURES.switch_1);
            modActiv.setSize(WIDTH / 16, WIDTH / 16);
            modActiv.setPosition(X + width - modActiv.width - WIDTH / 32, Y + (height - modActiv.height) / 2);
            modActiv.setEventListener(new Checkbox.EventListener() {
                @Override
                public void onClick(boolean isChecked) {
                    for (int i = 0; i < modsList.length(); i++) {
                        if (modsList.getJSONObject(i).getString("Mod").equals(modName)) {
                            modsList.getJSONObject(i).put("isOn", isChecked);
                            saveModsListFile();
                        }
                    }
                }
            });
        }

        public void setMod(Mod mod) {
            this.modIcon = mod.modIcon;
            this.modName = mod.modName;
            modActiv.setChecked(mod.isOn);
        }

        @Override
        public void tick(Camera2D cam) {
            modActiv.update(cam);
        }

        @Override
        public void renderTick(SpriteBatch batch, Camera2D cam) {
            batch.draw(background, cam.X + x, cam.Y + y, width, height);
            batch.draw((modIcon == null) ? Resource.getTexture("Blocks/SAUW_icon.png") : modIcon, cam.X + x + height / 8, cam.Y + y + height / 8, ModIconHeight, ModIconHeight);
            text.draw(batch, modName, cam.X + x + ModIconHeight + height / 4, cam.Y + y + height - height / 8);
            modActiv.render(batch, cam);
        }
    }
}
