package com.kgc.sauw;

import com.badlogic.gdx.*;
import com.kgc.sauw.InterfaceAPI.EditText;
import com.kgc.sauw.InterfaceAPI.Interface;
import com.kgc.sauw.InterfaceAPI.InterfaceEvents;
import com.kgc.sauw.InterfaceAPI.Notification;
import com.kgc.sauw.InterfaceAPI.Button;
import com.kgc.sauw.InterfaceAPI.Slot;
import com.kgc.sauw.ModAPI.ModAPI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import com.badlogic.gdx.graphics.Texture;

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
    Joystick j;
    Health health;
    Textures Textures;
    Camera2D interfaceCamera;
    public SpriteBatch batch;
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

    public GameInterface(Textures Textures, SpriteBatch batch, Items ITEMS, Settings settings, Langs langs) {
        multiplexer = new InputMultiplexer();
        this.settings = settings;
        setConsoleTextColor(settings.consoleTextColorRed, settings.consoleTextColorGreen, settings.consoleTextColorBlue);
        interfaceCamera = new Camera2D();
        WIDTH = interfaceCamera.W;
        HEIGHT = interfaceCamera.H;
        notification = new Notification(Textures.generateBackground(4, WIDTH / 12.0f / (WIDTH / 16.0f)));
        this.Textures = Textures;
        this.batch = batch;
        this.ITEMS = ITEMS;
        debug = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        debug.setColor(Color.BLACK);
        atackButton = new Button("", (int) (12 * (WIDTH / 16)), (int) (3 * (WIDTH / 16)), (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), Textures.button_0, Textures.button_1);
        dropButton = new Button("", (int) (13 * (WIDTH / 16)), (int) (3 * (WIDTH / 32)), (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), Textures.button_0, Textures.button_1);
        interactionButton = new Button("", (int) (14 * (WIDTH / 16)), (int) (3 * (WIDTH / 16))/*(int)(13 * (WIDTH / 16)), (int)(3 * (WIDTH / 32))*/, (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), Textures.button_0, Textures.button_1);
        consoleOpenButton = new Button("", (int) (WIDTH - (WIDTH / 16)), (int) (HEIGHT - (WIDTH / 16)), (int) WIDTH / 16, (int) WIDTH / 16, Textures.console_button_0, Textures.console_button_1);
        craftingButton = new Button("", (int) (WIDTH / 16 * 4 + WIDTH / 16 * 9), 0, (int) WIDTH / 16, (int) WIDTH / 16, Textures.crafting_button_0, Textures.crafting_button_1);
        pauseButton = new Button("pauseButton", 0, (int) (HEIGHT - WIDTH / 16), (int) WIDTH / 16, (int) WIDTH / 16);
        j = new Joystick(Textures.j_0, Textures.j_1, (int) (WIDTH / 32 * 3), (int) (WIDTH / 32 * 3), batch, (int) (WIDTH / 16 * 3), interfaceCamera);
        j.setDiameters((int) WIDTH / 16 * 3, (int) WIDTH / 32 * 2);
        inv = new Inventory(batch, interfaceCamera, Textures.inventory, Textures.selected_slot, ITEMS, (int) ((WIDTH / 16) * 4), 0, Textures, this, langs);
        health = new Health(batch, interfaceCamera, Textures.health_0, Textures.health_1);
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
        inv.init(w.pl, Textures, langs);
        crafting = c;
        consoleInterface = new Interface(Interface.InterfaceSizes.FULL, Textures, batch, interfaceCamera, ITEMS, this);
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
                input = new EditText((int) (Interface.x + WIDTH / 32), (int) (Interface.y + WIDTH / 32), inW, (int) WIDTH / 16, interfaceCamera, multiplexer);
                input.hide(true);
                log_bg = Textures.generateTexture((Interface.width - WIDTH / 8 + WIDTH / 16) / (WIDTH / 16), ((Interface.heigth - WIDTH / 16) - (WIDTH / 8)) / (WIDTH / 16), false);
                nextCommand = new Button("", input.X + input.w, (int) (Interface.y + WIDTH / 16), (int) (WIDTH / 32), (int) (WIDTH / 32), Textures.button_up_0, Textures.button_up_1);
                prevCommand = new Button("", input.X + input.w, (int) (Interface.y + WIDTH / 32), (int) (WIDTH / 32), (int) (WIDTH / 32), Textures.button_down_0, Textures.button_down_1);
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
                sendCommandButton = new Button("SendButton", input.X + input.w + (int) (WIDTH / 32), input.Y, (int) WIDTH / 16, (int) WIDTH / 16, Textures.button_right_0, Textures.button_right_1);
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
                batch.draw(log_bg, Interface.x + WIDTH / 32, Interface.y + WIDTH / 32 + WIDTH / 16, Interface.width - WIDTH / 8 + WIDTH / 16, (Interface.heigth - WIDTH / 16) - (WIDTH / 8));
            }

            @Override
            public void render() {
                Log.drawWrapped(batch, logText, Interface.x + WIDTH / 32 + WIDTH / 64, Interface.y + Interface.heigth - WIDTH / 16 - WIDTH / 32 - WIDTH / 64, WIDTH - WIDTH / 16 - WIDTH / 32);
                input.render(batch);
            }

        });
        craftingInterface = new Interface(Interface.InterfaceSizes.FULL, Textures, batch, interfaceCamera, ITEMS, this);
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
                background1 = Textures.generateTexture(7.5f, (HEIGHT - WIDTH / 16 * 2) / (WIDTH / 16), false);
                background2 = Textures.generateTexture(6f, (HEIGHT - WIDTH / 16 * 2) / (WIDTH / 16), false);
                BF = new BitmapFont(Gdx.files.internal("ttf.fnt"));
                BF.setColor(Color.BLACK);
                BF.setScale(interfaceCamera.W / 1100);
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
                c2 = new Slot("c2", xx + ww * 2, yy + ww, ww, ww, Textures.selected_slot);
                c1 = new Slot("c1", xx + ww, yy + ww, ww, ww, Textures.selected_slot);
                c0 = new Slot("c0", xx, yy + ww, ww, ww, Textures.selected_slot);
                c5 = new Slot("c5", xx + ww * 2, yy, ww, ww, Textures.selected_slot);
                c4 = new Slot("c4", xx + ww, yy, ww, ww, Textures.selected_slot);
                c3 = new Slot("c3", xx, yy, ww, ww, Textures.selected_slot);
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
                previos = new Button("pr", (int) (xxx + WIDTH / 32), (int) (yyy + hhh - hhh / 7 - WIDTH / 64), (int) hhh / 7, (int) hhh / 7, Textures.button_left_0, Textures.button_left_1);
                next = new Button("next", (int) (xxx + www - WIDTH / 32 - hhh / 7), (int) (yyy + hhh - hhh / 7 - WIDTH / 64), (int) hhh / 7, (int) hhh / 7, Textures.button_right_0, Textures.button_right_1);
                int dist = next.X - (previos.X + previos.width);
                background3 = Textures.generateTexture(dist / (WIDTH / 16), (hhh / 7) / (WIDTH / 16), true);
                background4 = Textures.generateTexture(2, 2, true);
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
                batch.draw(background2, WIDTH / 16 * 9, WIDTH / 32, WIDTH / 16 * 6, HEIGHT - WIDTH / 16 * 2);
                batch.draw(background1, WIDTH / 16, WIDTH / 32, WIDTH / 16 * 8 - WIDTH / 32, HEIGHT - WIDTH / 16 * 2);
                batch.draw(background3, previos.X + previos.width, previos.Y, next.X - (previos.X + previos.width), previos.height);
                batch.draw(background4, WIDTH / 32 * 22, WIDTH / 32 * 10, WIDTH / 16 * 2, WIDTH / 16 * 2);
            }

            @Override
            public void render() {
                for (int i = currentTab * 30; i < crafting.crafts.size(); i++) {
                    int x = this.Interface.getButton("craft_" + i).X;
                    int y = this.Interface.getButton("craft_" + i).Y;
                    int w = this.Interface.getButton("craft_" + i).width;
                    int h = this.Interface.getButton("craft_" + i).height;
                    batch.draw(ITEMS.getTextureById(crafting.crafts.get(i).result[0]), x + w / 8, y + w / 8, w - w / 4, w - w / 4);
                    if (currentCraft == i) {
                        for (int j = 0; j < crafting.crafts.get(i).ingr.length; j++) {
                            int xx = this.Interface.getSlot("c" + j).X;
                            int yy = this.Interface.getSlot("c" + j).Y;
                            int ww = this.Interface.getSlot("c" + j).width;
                            batch.draw(ITEMS.getTextureById(crafting.crafts.get(i).ingr[j][0]), xx + ww / 8, yy + ww / 8, ww - ww / 4, ww - ww / 4);
                            if (j == 0)
                                c0.IC.drawMultiLine(batch, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c0.X, c0.Y + c0.IC.getCapHeight(), c0.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 1)
                                c1.IC.drawMultiLine(batch, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c1.X, c1.Y + c1.IC.getCapHeight(), c1.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 2)
                                c2.IC.drawMultiLine(batch, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c2.X, c2.Y + c2.IC.getCapHeight(), c2.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 3)
                                c3.IC.drawMultiLine(batch, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c3.X, c3.Y + c3.IC.getCapHeight(), c3.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 4)
                                c4.IC.drawMultiLine(batch, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c4.X, c4.Y + c4.IC.getCapHeight(), c4.width, BitmapFont.HAlignment.RIGHT);
                            if (j == 5)
                                c5.IC.drawMultiLine(batch, pl.getCountOfItems(crafting.crafts.get(i).ingr[j][0]) + "/" + crafting.crafts.get(i).ingr[j][1], c5.X, c5.Y + c5.IC.getCapHeight(), c5.width, BitmapFont.HAlignment.RIGHT);
                        }
                        batch.draw(ITEMS.getTextureById(crafting.crafts.get(i).result[0]), WIDTH / 32 * 23, WIDTH / 32 * 11, WIDTH / 16, WIDTH / 16);
                        craftName.drawMultiLine(batch, ITEMS.getNameById(crafting.crafts.get(i).result[0]), WIDTH / 32 * 22, WIDTH / 16 * 5 - WIDTH / 128, WIDTH / 16 * 2, BitmapFont.HAlignment.CENTER);
                    }
                }
                BF.draw(batch, langs.getString("craftList"), previos.X + previos.width + (next.X - (previos.X + previos.width) - TB.width) / 2, previos.Y + previos.height - (previos.height - TB.height) / 2);
            }

        });
        deadInterface = new Interface(Interface.InterfaceSizes.STANDART, Textures, batch, interfaceCamera, ITEMS, this);
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
        pauseInterface = new Interface(Interface.InterfaceSizes.FULL, Textures, batch, interfaceCamera, ITEMS, this);
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
        interactionButton.update(interfaceCamera);
        dropButton.update(interfaceCamera);
        atackButton.update(interfaceCamera);
        consoleOpenButton.update(interfaceCamera);
        craftingButton.update(interfaceCamera);
        pauseButton.update(interfaceCamera);
        if (consoleOpenButton.isTouched() || dropButton.isTouched() || atackButton.isTouched() || interactionButton.isTouched() || j.isTouched() || inv.isTouched()) {
            isTouched = true;
        }
        consoleInterface.update(pl, interfaceCamera);
        craftingInterface.update(pl, interfaceCamera);
        deadInterface.update(pl, interfaceCamera);
        pauseInterface.update(pl, interfaceCamera);

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
        notification.update(interfaceCamera);
        if (notification.timer >= 4) {
            notifAnimation = true;
        }
    }

    public void render(Player pl, String debug) {
        interfaceCamera.update(batch);
        batch.setColor(1f, 1f, 1f, 0.7f);
        interactionButton.render(batch, interfaceCamera);
        dropButton.render(batch, interfaceCamera);
        atackButton.render(batch, interfaceCamera);
        consoleOpenButton.render(batch, interfaceCamera);
        craftingButton.render(batch, interfaceCamera);
        pauseButton.render(batch, interfaceCamera);
        batch.setColor(1f, 1f, 1f, 0.5f);
        j.render(interfaceCamera);
        batch.setColor(1f, 1f, 1f, 1f);
        health.render(pl.health, pl.maxHealth);
        consoleInterface.render(pl, interfaceCamera);
        craftingInterface.render(pl, interfaceCamera);
        deadInterface.render(pl, interfaceCamera);
        pauseInterface.render(pl, interfaceCamera);
        if (!isInterfaceOpen)
            this.debug.drawMultiLine(batch, debug, 3 + interfaceCamera.X, interfaceCamera.H - WIDTH / 16 + interfaceCamera.Y);
        inv.render(pl);
        batch.setColor(1, 1, 1, notifAl);
        notification.render(batch, interfaceCamera);
        batch.setColor(1, 1, 1, 1);
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
                if (settings.useConsole) if (consoleInterface.isOpen) consoleInterface.exitButton.EventListener.onClick();
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
