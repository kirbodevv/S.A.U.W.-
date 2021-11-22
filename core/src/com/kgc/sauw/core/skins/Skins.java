package com.kgc.sauw.core.skins;

import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.utils.Resource;

public class Skins {
    public static ElementSkin round_up;
    public static ElementSkin round_up_1;
    public static ElementSkin round_down;
    public static ElementSkin round_down_1;

    public static ElementSkin slot_round;

    public static ElementSkin game_button_up;
    public static ElementSkin game_button_down;

    public static ElementSkin progress_bar_background_round;
    public static ElementSkin progress_bar_foreground_round;

    public static ElementSkin interface_background;
    public static ElementSkin transparent;

    public static final int DEFAULT_OUTLINE_SIZE = 7;

    static {
        round_up = new ElementSkin(Resource.getTexture("Interface/round_up.png"), DEFAULT_OUTLINE_SIZE);
        round_up.setColor(0x555555FF);
        round_up_1 = new ElementSkin(Resource.getTexture("Interface/round_up.png"), DEFAULT_OUTLINE_SIZE);
        round_up_1.setColor(0x555555FF);
        round_down = new ElementSkin(Resource.getTexture("Interface/round_down.png"), DEFAULT_OUTLINE_SIZE);
        round_down.setColor(0x666666FF);
        round_down_1 = new ElementSkin(Resource.getTexture("Interface/round_down.png"), DEFAULT_OUTLINE_SIZE);
        round_down_1.setColor(0x555555FF);

        slot_round = new ElementSkin(Resource.getTexture("Interface/round_down.png"), DEFAULT_OUTLINE_SIZE);
        slot_round.setColor(0x666666FF);

        game_button_up = new ElementSkin(Resource.getTexture("Interface/button_0.png"));
        game_button_down = new ElementSkin(Resource.getTexture("Interface/button_1.png"));

        interface_background = new ElementSkin(Resource.getTexture("Interface/round_up.png"), DEFAULT_OUTLINE_SIZE);
        interface_background.setColor(0x282828FF);

        transparent = new ElementSkin();

        progress_bar_background_round = new ElementSkin(Resource.getTexture("Interface/round_down.png"), DEFAULT_OUTLINE_SIZE);
        progress_bar_background_round.setColor(0x60769DFF);
        progress_bar_foreground_round = new ElementSkin(Resource.getTexture("Interface/progress_bar_foreground_round.png"), DEFAULT_OUTLINE_SIZE);
        progress_bar_foreground_round.setColor(0x60769DFF);
    }
}
