package com.KGC.SAUW.mobs;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Mob {
	public String type;
	public int posX, posY;
	public int h = Gdx.graphics.getHeight();
	public int w = Gdx.graphics.getWidth();
	public int plW = w / 16 * 10 / 26;
	public int plH = w / 16;
	public int mX = (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
	public int mY = (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));
    public boolean collisions = true;
	public void update(){
		
	}
	public void render(SpriteBatch b){
		
	}
}
