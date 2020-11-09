package com.KGC.SAUW.InterfaceAPI;
import org.json.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import java.util.Iterator;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.KGC.SAUW.*;

public class Interface {
	public static class InterfaceSizes {
		public static int STANDART = 0;
		public static int FULL = 1;
	}
    //private JSONObject interfaceObj;
	public boolean isOpen = false;
	public boolean isBlockInterface;
	private Textures t;
	private SpriteBatch b;
	private Items items;
	private int w, h;
	public BitmapFont text = new BitmapFont(Gdx.files.internal("ttf.fnt"));
	public float width, heigth, x, y;
	public Button exitButton;
	private final GameInterface GI;

	public ArrayList<Button> buttons = new ArrayList<Button>();
	public ArrayList<Image> images = new ArrayList<Image>();
	public ArrayList<Slot> slots = new ArrayList<Slot>();

	//public EditText input;

    private int currX, currY, currZ;
	private Maps maps;
	public InterfaceEvents IE = null;
	private String headerText = "";
	private int size = 0;
    
	public Interface(int size, Textures t, SpriteBatch b, Camera2D cam, Items items, GameInterface GI_) {
		this.t = t;
		this.b = b;
		this.GI = GI_;
		this.items = items;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		text.setColor(Color.BLACK);

		if (size == InterfaceSizes.FULL) {
			width = w;
			heigth = h;
		} else if (size == InterfaceSizes.STANDART) {
			width = w / 16 * (h / (w / 16.0f) - 2);
			heigth = w / 16 * (h / (w / 16.0f) - 2);
		}
		x = (w - width) / 2;
		y = (h - heigth) / 2;
        this.size = size;
		exitButton = new Button("", (int)(x + width - w / 16), (int)(y + heigth - w / 16), w / 32, w / 32, t.closeButton, t.closeButton);
		exitButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					isOpen = false;
					if(GI != null)GI.isInterfaceOpen = false;
				}
			});
	}
	public Interface setHeaderText(String text) {
		this.headerText = text;
		return this;
	}
	public Interface isBlockInterface(boolean b) {
		isBlockInterface = b;
		return this;
	}
	public Interface createInventory(){
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 8; i++) {
				if (j == 0) {
					slots.add(new Slot("Inventory_slot_" + (j * 8 + i), (int)(x + ((width - w / 24.0f * 8) / 2) + (w / 24 * i)), (int)(y + w / 32), w / 24, w / 24, t.selected_slot, true, j * 8 + i));
				} else {
					slots.add(new Slot("Inventory_slot_" + (j * 8 + i), (int)(x + ((width - w / 24.0f * 8) / 2) + (w / 24 * i)), (int)(y + w / 32 + w / 24 * j + w / 128), w / 24, w / 24, t.selected_slot, true, j * 8 + i));
				}
			}
		}
		return this;
	}
	public void open(int x, int y, int z) {
		isOpen = true;
		currX = x;
		currY = y;
		currZ = z;
	}
	public void open() {
		isOpen = true;
		if (IE != null) {
			IE.onOpen();
		}
	}
	public void setMaps(Maps maps) {
		this.maps = maps;
	}

	public void swap(Slot a, Slot a1, Player pl) {
		if (a1.SF == null || a1.SF.isValid(a.id, a.count, a.data)) {
			int temp = a.id;
			int temp1 = a.count;
			int temp2 = a.data;
			if (!a.isInventorySlot) {
				if (maps != null) {
					maps.map0[currY][currX][currZ].getContainer(a.ID).setItem(a1.id, a1.count, a1.data);
				} else {
					a.id = a1.id;
					a.count = a1.count;
					a.data = a1.data;
				}
			} else {
				pl.Inventory[a.invSlot].setItem(a1.id, a1.count, a1.data);
			}
			if (!a1.isInventorySlot) {
				if (maps != null) {
					maps.map0[currY][currX][currZ].getContainer(a1.ID).setItem(temp, temp1, temp2);
				} else {
					a1.id = temp;
					a1.count = temp1;
					a1.data = temp2;
				}
			} else {
				pl.Inventory[a1.invSlot].setItem(temp, temp1, temp2);
			}
		}
	}
	public void sendToSlot(Slot slot1, Slot slot2, Player pl, Camera2D cam) {
		if (slot2.SF == null || slot2.SF.isValid(slot1.id, slot1.count, slot1.data)) {
			swap(slot1, slot2, pl);
		}
		/*slot1.this_touched = false;
		slot2.this_touched = false;
		slot1.isSelected = false;
		slot2.isSelected = false;*/
	}
	public Slot getSlot(String ID) {
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i).ID.equals(ID)) {
				return slots.get(i);
			}
		}
		return null;
	}
	public Button getButton(String ID) {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).ID.equals(ID)) {
				return buttons.get(i);
			}
		}
		return null;
	}
	public Slot getTouchedSlot() {
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i).isTouched()) {
				return slots.get(i);
			}
		}
		return null;
	}
	/*public slot getSelectedSlot() {
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i).isSelected()) {
				return slots.get(i);
			}
		}
		return null;
	}*/
	public void update(Player pl, Camera2D cam) {
		if (isOpen) {
			if(GI != null) GI.isInterfaceOpen = true;
			exitButton.update(cam);
			for (int j = 0; j < 32; j++) {
				for (int i = 0; i < slots.size(); i++) {
				    if (slots.get(i).invSlot == j) {
					    slots.get(i).id = pl.Inventory[i].id;
					    slots.get(i).count = pl.Inventory[i].count;
				    	slots.get(i).data = pl.Inventory[i].data;
				    }
				}
			}
			/*slot selectedSlot = getSelectedSlot();
			slot touchedSlot = getTouchedSlot();
			if (selectedSlot != null && touchedSlot != null && !selectedSlot.ID.equals(touchedSlot.ID)) {
				sendToSlot(selectedSlot.ID, touchedSlot.ID, pl, cam);
			}*/
			if (maps != null) {
				for (int i = 0; i < maps.map0[currY][currX][currZ].containers.size(); i++) {
					getSlot(maps.map0[currY][currX][currZ].containers.get(i).ID).id = maps.map0[currY][currX][currZ].containers.get(i).getId();
					getSlot(maps.map0[currY][currX][currZ].containers.get(i).ID).count = maps.map0[currY][currX][currZ].containers.get(i).getCount();
					getSlot(maps.map0[currY][currX][currZ].containers.get(i).ID).data = maps.map0[currY][currX][currZ].containers.get(i).getData();
				}
			}
			for (int i = 0; i < slots.size(); i++) {
				slots.get(i).update(slots, this, pl, cam);
			}
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).update(cam);
			}
			//if (input != null) input.update();
		} 
		if (IE != null) {
			IE.tick();
		}
	}

	public void render(Player pl, Camera2D cam) {
		if (isOpen) {
			if (size == InterfaceSizes.FULL) {
				b.draw(t.standartBackground_full, x + cam.X, y + cam.Y, width, heigth);
			} else if (size == InterfaceSizes.STANDART) {
				b.draw(t.standartBackground, x + cam.X, y + cam.Y, width, heigth);
			}
			text.drawMultiLine(b, headerText, x + cam.X, (y + heigth + cam.Y) - w / 32, width, BitmapFont.HAlignment.CENTER);
			if (IE != null) {
				IE.renderBefore();
			}

			exitButton.render(b, cam);

			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).render(b, cam);
			}
			for (int i = 0; i < images.size(); i++) {
				images.get(i).render(b, cam);
			}
			for (int i = 0; i < slots.size(); i++) {
				slots.get(i).render(b, cam);
			}
			//if (input != null) input.render(b);
			for (int i = 0; i < slots.size(); i++) {
				/*if (slots.get(i).isSelected()) {
					float sc = slots.get(i).width / slots.get(i).selectedSlot.getWidth();
					float X = slots.get(i).X - ((slots.get(i).selectedSlot.getWidth() - slots.get(i).slot.getWidth()) / 2) * sc;
					float Y = slots.get(i).Y - ((slots.get(i).selectedSlot.getWidth() - slots.get(i).slot.getWidth()) / 2) * sc;
					float W = slots.get(i).width + (slots.get(i).selectedSlot.getWidth() - slots.get(i).slot.getWidth()) * sc;
					float H = slots.get(i).height + (slots.get(i).selectedSlot.getWidth() - slots.get(i).slot.getWidth()) * sc;
					b.draw(slots.get(i).selectedSlot, X, Y, W, H);
				}*/
				slots.get(i).itemRender(b, items);
			}
			if (IE != null) {
				IE.render();
			}
		}
	}
	public void setInterfaceEvents(InterfaceEvents IE) {
		this.IE = IE;
		this.IE.Interface = this;
		this.IE.initialize();
	}
}
