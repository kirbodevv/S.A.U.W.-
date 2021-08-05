package com.kgc.sauw.game.skins;

import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.resource.Resource;

public class Skins {
    public static ElementSkin round_up;
    public static ElementSkin round_down;
    public static ElementSkin round_down_1;

    public static ElementSkin slot_round;

    public static ElementSkin game_button_up;
    public static ElementSkin game_button_down;

    public static ElementSkin progress_bar_background_round;
    public static ElementSkin progress_bar_foreground_round;

    public static ElementSkin interface_background;



    static {
        round_up = new ElementSkin(Resource.getTexture("Interface/round_up.png"), 5);
        round_up.setColor(0x60769DFF);
        round_down = new ElementSkin(Resource.getTexture("Interface/round_down.png"), 5);
        round_down.setColor(0x60769DFF);
        round_down_1 = new ElementSkin(Resource.getTexture("Interface/round_down.png"), 5);
        round_down_1.setColor(0x60769DFF);

        slot_round = new ElementSkin(Resource.getTexture("Interface/round_down.png"), 5);
        slot_round.setColor(0x7165A3FF);

        game_button_up = new ElementSkin(Resource.getTexture("Interface/button_0.png"));
        game_button_down = new ElementSkin(Resource.getTexture("Interface/button_1.png"));

        interface_background = new ElementSkin(Resource.getTexture("Interface/round_up.png"), 5);
        interface_background.setColor(0x374F8DFF);

        progress_bar_background_round = new ElementSkin(Resource.getTexture("Interface/round_down.png"), 5);
        progress_bar_background_round.setColor(0x60769DFF);
        progress_bar_foreground_round = new ElementSkin(Resource.getTexture("Interface/progress_bar_foreground_round.png"), 5);
        progress_bar_foreground_round.setColor(0x60769DFF);
    }
}
