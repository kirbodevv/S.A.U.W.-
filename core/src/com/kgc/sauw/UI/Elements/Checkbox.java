package com.kgc.sauw.UI.Elements;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.utils.Camera2D;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.UI.InterfaceElement;

public class Checkbox extends InterfaceElement {
	private Texture t0, t1;
	private boolean isChecked = false;
	private EventListener EL;
	public Checkbox(Texture t0, Texture t1){
		this.t0 = t0;
		this.t1 = t1;
	}
	public void setEventListener(EventListener EL){
		this.EL = EL;
	}
	public boolean isChecked(){
		return isChecked;
	}
	public void setChecked(boolean c){
		isChecked = c;
	}
	@Override
	public void update(Camera2D cam) {
		super.update(cam);
	}
	@Override
	public void render(SpriteBatch batch, Camera2D cam) {
		super.render(batch, cam);
		batch.draw(isChecked ? t1 : t0, cam.X + X, cam.Y + Y, width, height);
	}

	@Override
	public void onClick(boolean onButton) {
		super.onClick(onButton);
		if(EL != null && onButton){
			isChecked = !isChecked;
			EL.onClick(isChecked);
		}
	}
	public static abstract class EventListener {
		public abstract void onClick(boolean isChecked);
	}
}
