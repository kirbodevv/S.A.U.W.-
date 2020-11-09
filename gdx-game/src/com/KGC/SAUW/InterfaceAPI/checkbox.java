package com.KGC.SAUW.InterfaceAPI;
import com.badlogic.gdx.graphics.Texture;
import com.KGC.SAUW.Vector2i;
import com.KGC.SAUW.Camera2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class checkbox extends InterfaceElement{
	private Texture t0, t1;
	private boolean isChecked;
	private Eventlistener EL;
	public checkbox(Texture t0, Texture t1){
		this.t0 = t0;
		this.t1 = t1;
	}
	public void setEventListener(Eventlistener EL){
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
		isChecked = !isChecked;
		if(EL != null && onButton) EL.onClick(isChecked);
	}
	public static abstract class Eventlistener{
		public abstract void onClick(boolean isChecked);
	}
}
