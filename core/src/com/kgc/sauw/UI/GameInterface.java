package com.kgc.sauw.UI;

import com.badlogic.gdx.*;
import com.kgc.sauw.UI.Elements.*;
import com.kgc.sauw.Modding.ModAPI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.config.Settings;
import com.kgc.sauw.environment.Crafting;
import com.kgc.sauw.game.MainGame;
import com.kgc.sauw.utils.Version;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.map.World;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.utils.Langs;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import com.badlogic.gdx.graphics.Texture;

import static com.kgc.sauw.graphic.Graphic.BATCH;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.graphic.Graphic.INTERFACE_CAMERA;
public class GameInterface implements InputProcessor {
    float WIDTH;
    float HEIGHT;
    Inventory inv;
    public Button interactionButton;
    public Button dropButton;
    public Button atackButton;
    public Button consoleOpenButton;
    public Button pauseButton;
    private Settings settings;
    private Crafting crafting;
    public Joystick j;
    Health health;
    Items ITEMS;
    BitmapFont debug;
    public boolean isInterfaceOpen = false;
    public boolean hided = false;

    public Interface consoleInterface;
    public Interface craftingInterface;
    public Interface deadInterface;
    public Interface pauseInterface;

    private boolean isTouched;
    public Button craftingButton;
    public Notification notification;
    private boolean notifAnimation = false;
    private int notifW;
    private float notifAl = 1f;
    public BitmapFont Log = new BitmapFont(Gdx.files.internal("ttf.fnt"));

    private String logText = "Lol";
    public int R = 0, G = 0, B = 0;

    public Context cx;
    public Scriptable sc;

    public InputMultiplexer multiplexer;

    public void consolePrint(String txt) {
        this.logText = txt;
    }

    public void setConsoleTextColor(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
        settings.consoleTextColorRed = r;
        settings.consoleTextColorGreen = g;
        settings.consoleTextColorBlue = b;
        settings.saveSettings();
    }

    public GameInterface(Items ITEMS, Settings settings, Langs langs) {
        multiplexer = new InputMultiplexer();
        this.settings = settings;
        setConsoleTextColor(settings.consoleTextColorRed, settings.consoleTextColorGreen, settings.consoleTextColorBlue);
        WIDTH = INTERFACE_CAMERA.W;
        HEIGHT = INTERFACE_CAMERA.H;
        notification = new Notification(TEXTURES.generateBackground(4, WIDTH / 12.0f / (WIDTH / 16.0f)));
        this.ITEMS = ITEMS;
        debug = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        debug.setScale(WIDTH / 64 / debug.getCapHeight());
        atackButton = new Button("", (int) (12 * (WIDTH / 16)), (int) (3 * (WIDTH / 16)), (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), TEXTURES.button_0, TEXTURES.button_1);
        dropButton = new Button("", (int) (13 * (WIDTH / 16)), (int) (3 * (WIDTH / 32)), (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), TEXTURES.button_0, TEXTURES.button_1);
        interactionButton = new Button("", (int) (14 * (WIDTH / 16)), (int) (3 * (WIDTH / 16))/*(int)(13 * (WIDTH / 16)), (int)(3 * (WIDTH / 32))*/, (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), TEXTURES.button_0, TEXTURES.button_1);
        consoleOpenButton = new Button("", (int) (WIDTH - (WIDTH / 16)), (int) (HEIGHT - (WIDTH / 16)), (int) WIDTH / 16, (int) WIDTH / 16, TEXTURES.console_button_0, TEXTURES.console_button_1);
        craftingButton = new Button("", (int) (WIDTH / 16 * 4 + WIDTH / 16 * 9), 0, (int) WIDTH / 16, (int) WIDTH / 16, TEXTURES.crafting_button_0, TEXTURES.crafting_button_1);
        pauseButton = new Button("pauseButton", 0, (int) (HEIGHT - WIDTH / 16), (int) WIDTH / 16, (int) WIDTH / 16);
        j = new Joystick(TEXTURES.j_0, TEXTURES.j_1, (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), BATCH, (int) (WIDTH / 16 * 3), INTERFACE_CAMERA);
        j.setDiameters((int) WIDTH / 16 * 3, (int) WIDTH / 32 * 2);
        inv = new Inventory(INTERFACE_CAMERA, TEXTURES.inventory, TEXTURES.selected_slot, ITEMS, (int) ((WIDTH / 16) * 4), 0, TEXTURES, this, langs);
        health = new Health(BATCH, INTERFACE_CAMERA, TEXTURES.health_0, TEXTURES.health_1);
        notifW = (int) WIDTH / 16 * 4;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean t) {
        isTouched = t;
    }

    public void showNotification(String title, String txt, Texture t, int time) {
        notification.show((int) WIDTH, (int) (HEIGHT - WIDTH / 12), (int) (WIDTH / 16 * 4), (int) (WIDTH / 12), title, txt, t, time);
        notifAnimation = true;
        notifAl = 1;
    }

    public void initialize(Crafting c, final ModAPI ModAPI, final MainGame game, final Langs langs, final World w) {
        final Player pl = w.pl;
        inv.init(w.pl, TEXTURES, langs);
        crafting = c;
        consoleInterface = new Interface(Interface.InterfaceSizes.FULL, INTERFACE_CAMERA, ITEMS, this);
        consoleInterface.setHeaderText(langs.getString("console")).isBlockInterface(false);
        consoleInterface.setInterfaceEvents(new InterfaceEvents() {
            public Button sendCommandButton;
            public Button nextCommand;
            public Button prevCommand;
            private int currCom = -1;
            public EditText input;
            Texture log_bg;
            String inputTxt = "";

            @Override
            public void initialize() {
                int inW = (int) (Interface.width - WIDTH / 8 - WIDTH / 32);
                input = new EditText((int) (Interface.x + WIDTH / 32), (int) (Interface.y + WIDTH / 32), inW, (int) WIDTH / 16, INTERFACE_CAMERA, multiplexer);
                input.hide(true);
                log_bg = TEXTURES.generateTexture((Interface.width - WIDTH / 8 + WIDTH / 16) / (WIDTH / 16), ((Interface.heigth - WIDTH / 16) - (WIDTH / 8)) / (WIDTH / 16), false);
                nextCommand = new Button("", input.X + input.w, (int) (Interface.y + WIDTH / 16), (int) (WIDTH / 32), (int) (WIDTH / 32), TEXTURES.button_up_0, TEXTURES.button_up_1);
                prevCommand = new Button("", input.X + input.w, (int) (Interface.y + WIDTH / 32), (int) (WIDTH / 32), (int) (WIDTH / 32), TEXTURES.button_down_0, TEXTURES.button_down_1);
                nextCommand.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        currCom++;
                        if (currCom >= ModAPI.Console.inputs.size()) {
                            currCom = ModAPI.Console.inputs.size() - 1;
                        }
                        if (currCom != -1) {
                            input.input = ModAPI.Console.input(currCom);
                        } else {
                            input.input = inputTxt;
                        }
                    }
                });
                prevCommand.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        currCom--;
                        if (currCom < 0) {
                            currCom = -1;
                        }
                        if (currCom != -1) {
                            input.input = ModAPI.Console.input(currCom);
                        } else {
                            input.input = inputTxt;
                        }
                    }
                });
                sendCommandButton = new Button("SendButton", input.X + input.w + (int) (WIDTH / 32), input.Y, (int) WIDTH / 16, (int) WIDTH / 16, TEXTURES.button_right_0, TEXTURES.button_right_1);
                this.Interface.buttons.add(sendCommandButton);
                this.Interface.buttons.add(nextCommand);
                this.Interface.buttons.add(prevCommand);
                Log.setColor(Color.BLACK);
                cx = Context.enter();
                cx.setOptimizationLevel(-1);
                try {
                    sc = cx.initStandardObjects();
                    ScriptableObject.putProperty(sc, "Player", pl);
                    ScriptableObject.putProperty(sc, "GI", GameInterface.this);
                    ScriptableObject.putProperty(sc, "World", w);
                } catch (Exception e) {
                    Gdx.app.log("error", e.toString());
                } finally {
                    Context.exit();
                }
            }

            @Override
            public void tick() {
                Log.setColor(R / 255f, G / 255f, B / 255f, 1);
                input.setTextColor(R / 255f, G / 255f, B / 255f);
                if (currCom == -1) {
                    inputTxt = input.input;
                }
                if (sendCommandButton.isTouched() && input.input != null && input.input != "") {
                    cx = Context.enter();
                    cx.setOptimizationLevel(-1);
                    try {
                        cx.evaluateString(sc, Gdx.files.internal("js/commands.js").readString() + input.input, "Command", 1, null);
                    } catch (Exception e) {
                        consolePrint(e.toString());
                        Gdx.app.log("error", e.toString());
                    } finally {
                        Context.exit();
                    }
                    ModAPI.Console.inputs.add(input.input);
                    input.clear();
                }
                input.Y = input.isKeyboardOpen ? game.keyboardHeight : (int) (Interface.y + WIDTH / 32);
                if (input.Y == 0) input.Y = (int) (Interface.y + WIDTH / 32);
                sendCommandButton.Y = input.Y;
                prevCommand.Y = input.Y;
                nextCommand.Y = prevCommand.Y + prevCommand.height;
                input.update();
            }

            @Override
            public void onOpen() {
                input.hide(false);
            }

            @Override
            public void onClose() {
                input.hide(true);
            }

            @Override
            public void renderBefore() {
                BATCH.draw(log_bg, Interface.x + WIDTH / 32, Interface.y + WIDTH / 32 + WIDTH / 16, Interface.width - WIDTH / 8 + WIDTH / 16, (Interface.heigth - WIDTH / 16) - (WIDTH / 8));
            }

            @Override
            public void render() {
                Log.drawWrapped(BATCH, logText, Interface.x + WIDTH / 32 + WIDTH / 64, Interface.y + Interface.heigth - WIDTH / 16 - WIDTH / 32 - WIDTH / 64, WIDTH - WIDTH / 16 - WIDTH / 32);
                input.render(BATCH);
            }

        });
        craftingInterface = new Interface(Interface.InterfaceSizes.FULL, INTERFACE_CAMERA, ITEMS, this);
        craftingInterface.setHeaderText(langs.getString("crafting")).isBlockInterface(false);
        craftingInterface.setInterfaceEvents(new InterfaceEvents() {
            Button craft;
            BitmapFont craftName;
            BitmapFont BF;
            Slot c0;
            Slot c1;
            Slot c2;
            Slot c3;
            Slot c4;
            Slot c5;
            float txtX;
            float txtY;
            Button previos;
            Button next;
            int currentCraft = -1;
            int currentTab = 0;
            Texture background1, background2, background3, background4;
            BitmapFont.TextBounds TB;

            @Override
            public void initialize() {
                background1 = TEXTURES.generateTexture(7.5f, (HEIGHT - WIDTH / 16 * 2) / (WIDTH / 16), false);
                background2 = TEXTURES.generateTexture(6f, (HEIGHT - WIDTH / 16 * 2) / (WIDTH / 16), false);
                BF = new BitmapFont(Gdx.files.internal("ttf.fnt"));
                BF.setColor(Color.BLACK);
                BF.setScale(INTERFACE_CAMERA.W / 1100);
                craftName = new BitmapFont(Gdx.files.internal("ttf.fnt"));
                craftName.setColor(Color.BLACK);
                craftName.setScale(WIDTH / 32 / 2 / craftName.getCapHeight());
                TB = BF.getBounds(langs.getString("craftList"));
                craft = new Button("craftButton", (int) (WIDTH / 16 * 9 + WIDTH / 32), (int) WIDTH / 16, (int) WIDTH / 16 * 5, (int) WIDTH / 16);
                craft.setText(langs.getString("craft"));
                craft.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        if (currentCraft != -1) {
                            for (int i = 0; i < crafting.crafts.get(currentCraft).ingr.length; i++) {
                                int IC = pl.getCountOfItems(crafting.crafts.get(currentCraft).ingr[i][0]);
                                if (IC < crafting.crafts.get(currentCraft).ingr[i][1]) {
                                    return;
                                }
                            }
                            for (int i = 0; i < crafting.crafts.get(currentCraft).ingr.length; i++) {
                                pl.deleteItems(crafting.crafts.get(currentCraft).ingr[i][0], crafting.crafts.get(currentCraft).ingr[i][1]);
                            }
                            pl.addItem(crafting.crafts.get(currentCraft).result[0], crafting.crafts.get(currentCraft).result[1], crafting.crafts.get(currentCraft).result[2]);
                        }
                    }
                });
                this.Interface.buttons.add(craft);
                int xx = (int) WIDTH / 16 * 9 + (int) WIDTH / 32 + (int) WIDTH / 16;
                int yy = (int) WIDTH / 32 * 5;
                int ww = (int) WIDTH / 16;
                c2 = new Slot("c2", xx + ww * 2, yy + ww, ww, ww, TEXTURES.selected_slot);
                c1 = new Slot("c1", xx + ww, yy + ww, ww, ww, TEXTURES.selected_slot);
                c0 = new Slot("c0", xx, yy + ww, ww, ww, TEXTURES.selected_slot);
                c5 = new Slot("c5", xx + ww * 2, yy, ww, ww, TEXTURES.selected_slot);
                c4 = new Slot("c4", xx + ww, yy, ww, ww, TEXTURES.selected_slot);
                c3 = new Slot("c3", xx, yy, ww, ww, TEXTURES.selected_slot);
                this.Interface.slots.add(c0);
                this.Interface.slots.add(c1);
                this.Interface.slots.add(c2);
                this.Interface.slots.add(c3);
                this.Interface.slots.add(c4);
                this.Interface.slots.add(c5);
                float xxx = WIDTH / 16;
                float yyy = WIDTH / 32;
                float www = WIDTH / 16 * 8 - WIDTH / 32;
                float hhh = HEIGHT - WIDTH / 16 * 2;
                previos = new Button("pr", (int) (xxx + WIDTH / 32), (int) (yyy + hhh - hhh / 7 - WIDTH / 64), (int) hhh / 7, (int) hhh / 7, TEXTURES.button_left_0, TEXTURES.button_left_1);
                next = new Button("next", (int) (xxx + www - WIDTH / 32 - hhh / 7), (int) (yyy + hhh - hhh / 7 - WIDTH / 64), (int) hhh / 7, (int) hhh / 7, TEXTURES.button_right_0, TEXTURES.button_right_1);
                int dist = next.X - (previos.X + previos.width);
                background3 = TEXTURES.generateTexture(dist / (WIDTH / 16), (hhh / 7) / (WIDTH / 16), true);
                background4 = TEXTURES.generateTexture(2, 2, true);
                this.Interface.buttons.add(previos);
                this.Interface.buttons.add(next);
                for (int y = 0; y < 5; y++) {
                    for (int x = 0; x < 6; x++) {
                        final int num = y * 6 + x;
                        String id = "craft_" + num;
                        Button b = new Button(id, (int) (xxx + WIDTH / 32 + hhh / 7 * x + WIDTH / 64), (int) (yyy + WIDTH / 32 + hhh / 7 * (4 - y)), (int) (hhh / 7), (int) (hhh / 7));
                        b.setEventListener(new Button.EventListener() {
                            @Override
                            public void onClick() {
                                currentCraft = currentTab * 30 + num;
                            }
                        });
                        this.Interface.buttons.add(b);

                    }
                }
            }

            @Override
            public void tick() {
                int temp = 0;
                for (int i = currentTab * 30; i < currentTab + 1 * 30; i++) {
                    if (this.Interface.getButton("craft_" + temp) != null) {
                        if (i < crafting.crafts.size()) {
                            this.Interface.getButton("craft_" + temp).hide(false);
                        } else {
                            this.Interface.getButton("craft_" + temp).hide(true);
                        }
                    }
                    temp += 1;
                }
            }

            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void renderBefore() {
                BATCH.draw(background2, WIDTH / 16 * 9, WIDTH / 32, WIDTH / 16 * 6, HEIGHT - WIDTH / 16 * 2);
                BATCH.draw(background1, WIDTH / 16, WIDTH / 32, WIDTH / 16 * 8 - WIDTH / 32, HEIGHT - WIDTH / 16 * 2);
                BATCH.draw(background3, previos.X + previos.width, previos.Y, next.X - (previos.X + previos.width), previos.height);
                BATCH.draw(background4, WIDTH / 32 * 22, WIDTH / 32 * 10, WIDTH / 16 * 2, WIDTH / 16 * 2);
            }

            @Override
            public void render() {
                for (int i = currentTab * 30; i < crafting.crafts.size(); i++) {
                    int x = this.Interface.getButton("craft_" + i).X;
                    int y = this.Interface.getButton("craft_" + i).Y;
                    int w = this.Interface.getButton("craft_" + i).width;
                    int h = this.Interface.getButton("craft_" + i).height;
                    BATCH.draw(ITEMS.getTextureById(crafting.crafts.get(i).result[0]), x + w / 8, y + w / 8, w - w / 4, w - w / 4);
                    if (currentCraft == i) {
                        for (int j = 0; j < crafting.crafts.get(i).ingr.length; j++) {
                            int xx = this.Interface.getSlot("c" + j).X;
                            int yy = this.Interface.getSlot("c" + j).Y;
                            int ww = this.Interface.getSlot("c" + j).width;
                            BATCH.draw(ITEMS.getTextureById(crafting.crafts.get(i).ingr[j][0]), xx + ww / 8, yy + ww / 8, ww - ww / 4, ww - ww / 4);
                            if (j == 0)
                                c0.IC.drawMultiLine(BATCH, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c0.X, c0.Y + c0.IC.getCapHeight(), c0.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 1)
                                c1.IC.drawMultiLine(BATCH, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c1.X, c1.Y + c1.IC.getCapHeight(), c1.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 2)
                                c2.IC.drawMultiLine(BATCH, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c2.X, c2.Y + c2.IC.getCapHeight(), c2.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 3)
                                c3.IC.drawMultiLine(BATCH, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c3.X, c3.Y + c3.IC.getCapHeight(), c3.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 4)
                                c4.IC.drawMultiLine(BATCH, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c4.X, c4.Y + c4.IC.getCapHeight(), c4.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 5)
                                c5.IC.drawMultiLine(BATCH, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c5.X, c5.Y + c5.IC.getCapHeight(), c5.width, BitmapFont.HAlignment.RIGHT);
                        }
                        BATCH.draw(ITEMS.getTextureById(crafting.crafts.get(i).result[0]), WIDTH / 32 * 23, WIDTH / 32 * 11, WIDTH / 16, WIDTH / 16);
                        craftName.drawMultiLine(BATCH, ITEMS.getNameById(crafting.crafts.get(i).result[0]), WIDTH / 32 * 22, WIDTH / 16 * 5 - WIDTH / 128, WIDTH / 16 * 2, BitmapFont.HAlignment.CENTER);
                    }
                }
                BF.draw(BATCH, langs.getString("craftList"), previos.X + previos.width + (next.X - (previos.X + previos.width) - TB.width) / 2, previos.Y + previos.height - (previos.height - TB.height) / 2);
            }

        });
        deadInterface = new Interface(Interface.InterfaceSizes.STANDART, INTERFACE_CAMERA, ITEMS, this);
        deadInterface.setHeaderText("").isBlockInterface(false);
        deadInterface.setInterfaceEvents(new InterfaceEvents() {
            Button respawn;

            @Override
            public void initialize() {
                respawn = new Button("respawn", (int) (WIDTH / 16 * 5), (int) (HEIGHT - WIDTH / 16 * 5), (int) (WIDTH / 16 * 6), (int) (WIDTH / 16));
                //respawn.setTextColor(Color.BLACK);
                respawn.setText(langs.getString("respawn"));
                respawn.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        pl.spawn();
                        Interface.exitButton.EventListener.onClick();
                    }
                });
                Interface.exitButton.hide(true);
                Interface.buttons.add(respawn);
            }

            @Override
            public void tick() {
            }

            @Override
            public void onOpen() {
            }

            @Override
            public void onClose() {

            }

            @Override
            public void renderBefore() {
            }

            @Override
            public void render() {
            }
        });
        pauseInterface = new Interface(Interface.InterfaceSizes.FULL, INTERFACE_CAMERA, ITEMS, this);
        pauseInterface.setHeaderText("").isBlockInterface(false);
        pauseInterface.setInterfaceEvents(new InterfaceEvents() {
            Button saveWorldButton;
            Button resumeButton;
            Button exitButton;

            @Override
            public void initialize() {
                resumeButton = new Button("ResumeButton", (int) (WIDTH / 16), (int) (HEIGHT - WIDTH / 16 * 4), (int) (WIDTH / 16 * 4), (int) WIDTH / 16);
                resumeButton.setText(langs.getString("resume"));
                resumeButton.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        Interface.exitButton.EventListener.onClick();
                    }
                });
                saveWorldButton = new Button("saveWorldButton", (int) (WIDTH / 16), (int) (resumeButton.Y - WIDTH / 16), (int) (WIDTH / 16 * 4), (int) WIDTH / 16);
                saveWorldButton.setText(langs.getString("save"));
                saveWorldButton.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        w.save();
                    }
                });
                Interface.exitButton.hide(true);
                Interface.buttons.add(resumeButton);
                Interface.buttons.add(saveWorldButton);

            }

            @Override
            public void tick() {
            }

            @Override
            public void onOpen() {
            }

            @Override
            public void onClose() {

            }

            @Override
            public void renderBefore() {
            }

            @Override
            public void render() {
            }
        });
        craftingButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                craftingInterface.open();
            }
        });
        consoleOpenButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                consoleInterface.open();
            }
        });
        pauseButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                pauseInterface.open();
            }
        });
        game.multiplexer.addProcessor(this);
        game.multiplexer.addProcessor(multiplexer);
    }

    public void update(Player pl) {
        if (isInterfaceOpen) {
            interactionButton.hide(true);
            dropButton.hide(true);
            atackButton.hide(true);
            //button2.hide(true);
            inv.hide(true);
            j.hide(true);
            health.hide(true);
            consoleOpenButton.hide(true);
            craftingButton.hide(true);
            pauseButton.hide(true);
        } else {
            interactionButton.hide(false);
            dropButton.hide(false);
            atackButton.hide(false);
            //button2.hide(false);
            inv.hide(false);
            j.hide(false);
            health.hide(false);
            consoleOpenButton.hide(!settings.useConsole);
            craftingButton.hide(false);
            pauseButton.hide(false);
        }
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            j.hide(true);
            consoleOpenButton.hide(true);
            pauseButton.hide(true);
            craftingButton.hide(true);
            interactionButton.hide(true);
            dropButton.hide(true);
            atackButton.hide(true);
        }
        isTouched = false;
        inv.update(pl);
        interactionButton.update(INTERFACE_CAMERA);
        dropButton.update(INTERFACE_CAMERA);
        atackButton.update(INTERFACE_CAMERA);
        consoleOpenButton.update(INTERFACE_CAMERA);
        craftingButton.update(INTERFACE_CAMERA);
        pauseButton.update(INTERFACE_CAMERA);
        if (consoleOpenButton.isTouched() || dropButton.isTouched() || atackButton.isTouched() || interactionButton.isTouched() || j.isTouched() || inv.isTouched()) {
            isTouched = true;
        }
        consoleInterface.update(pl, INTERFACE_CAMERA);
        craftingInterface.update(pl, INTERFACE_CAMERA);
        deadInterface.update(pl, INTERFACE_CAMERA);
        pauseInterface.update(pl, INTERFACE_CAMERA);

        if (notifAnimation) {
            if (notification.timer < 4) {
                notification.X -= notifW / 15;
                if (notification.X <= WIDTH - notifW) {
                    notification.X = (int) WIDTH - notifW;
                    notifAnimation = false;
                }
            } else {
                notifAl -= 0.01f;
            }
        }
        notification.update(INTERFACE_CAMERA);
        if (notification.timer >= 4) {
            notifAnimation = true;
        }
    }

    public void render(World World,Player pl, boolean debug) {
        INTERFACE_CAMERA.update(BATCH);
        BATCH.setColor(1f, 1f, 1f, 0.7f);
        interactionButton.render(BATCH, INTERFACE_CAMERA);
        dropButton.render(BATCH, INTERFACE_CAMERA);
        atackButton.render(BATCH, INTERFACE_CAMERA);
        consoleOpenButton.render(BATCH, INTERFACE_CAMERA);
        craftingButton.render(BATCH, INTERFACE_CAMERA);
        pauseButton.render(BATCH, INTERFACE_CAMERA);
        BATCH.setColor(1f, 1f, 1f, 0.5f);
        j.render(INTERFACE_CAMERA);
        BATCH.setColor(1f, 1f, 1f, 1f);
        health.render(pl.health, pl.maxHealth);
        consoleInterface.render(pl, INTERFACE_CAMERA);
        craftingInterface.render(pl, INTERFACE_CAMERA);
        deadInterface.render(pl, INTERFACE_CAMERA);
        pauseInterface.render(pl, INTERFACE_CAMERA);
        if (!isInterfaceOpen && debug)
            drawDebugString(World);
        inv.render(pl);
        BATCH.setColor(1, 1, 1, notifAl);
        notification.render(BATCH, INTERFACE_CAMERA);
        BATCH.setColor(1, 1, 1, 1);
    }

    public void drawDebugString(World World) {
        String Main = " Version : " + Version.VERSION +
                "\n FPS : " + Gdx.graphics.getFramesPerSecond() +
                "\n " + (Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap()) / 1024 / 1024 + " Mb";
        String Player = "\n Hunger:" + World.pl.hunger + "/20" +
                "\n X : " + World.pl.currentTileX +
                "\n Y : " + World.pl.currentTileY;
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.drawMultiLine(BATCH, Main, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - WIDTH / 16 + INTERFACE_CAMERA.Y);
        this.debug.setColor(0.25f, 0.25f, 1f, 1f);
        this.debug.drawMultiLine(BATCH, "\n Player", INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - WIDTH / 16 + INTERFACE_CAMERA.Y - this.debug.getMultiLineBounds(Main).height);
        this.debug.setColor(0f, 0f, 0f, 1f);
        this.debug.drawMultiLine(BATCH, Player, INTERFACE_CAMERA.X, INTERFACE_CAMERA.H - WIDTH / 16 + INTERFACE_CAMERA.Y - this.debug.getMultiLineBounds(Main + "\n Player").height);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!isInterfaceOpen) {
            if (keycode == Input.Keys.ESCAPE) {
                if (!pauseInterface.isOpen) pauseInterface.open();
            }
            if (keycode == Input.Keys.TAB) {
                if (!inv.inventoryInterface.isOpen) inv.inventoryInterface.open();
            }
            if (keycode == Input.Keys.F1) {
                if (settings.useConsole) if (!consoleInterface.isOpen) consoleInterface.open();
            }
            if (keycode == Input.Keys.C) {
                if (!craftingInterface.isOpen) craftingInterface.open();
            }
        } else {
            if (keycode == Input.Keys.ESCAPE) {
                if (pauseInterface.isOpen) pauseInterface.exitButton.EventListener.onClick();
            }
            if (keycode == Input.Keys.TAB) {
                if (inv.inventoryInterface.isOpen) inv.inventoryInterface.exitButton.EventListener.onClick();
            }
            if (keycode == Input.Keys.F1) {
                if (settings.useConsole)
                    if (consoleInterface.isOpen) consoleInterface.exitButton.EventListener.onClick();
            }
            if (keycode == Input.Keys.C) {
                if (craftingInterface.isOpen) craftingInterface.exitButton.EventListener.onClick();
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
