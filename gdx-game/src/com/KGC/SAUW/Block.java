package com.KGC.SAUW;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.KGC.SAUW.InterfaceAPI.Interface;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Block
{
	public boolean isTranspanent = false;
	private int xSize = 1;
	private int ySize = 1;
	public int[][] drop;
	public int IT = -1;
	private int w = Gdx.graphics.getWidth();
	public Rectangle collisionsRect = new Rectangle(0, 0, w / 16, w / 16);
	public int lightingRadius = -1;
	public int lightingColor;
	public static abstract class TileEntity {
		public void interfaceInitializate(){
			
		}
		public abstract void initialize(Tile tile);
		
		public abstract void tick(Tile tile);

		public abstract void click(Tile tile);

		public abstract Interface getGuiScreen();

		public abstract void onInteractionButtonPressed(Tile tile);

		public abstract void randomTick(Tile tile);
		
		public boolean renderIf(Tile tile){
			return true;
		}
		
		public boolean collisionsIf(Tile tile){
			return true;
		}
	
	}
	int id;
	int type;
	private int maxDamage = 1;
	public int RBOD = 4;
	Texture t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15;
	public TileEntity TileEntity = null;
	public void setMaxDamage(int md){
		this.maxDamage = md;
	}
	public int getMaxDamage(){
		return maxDamage;
	}
	public void setLightingRadius(int r){
		this.lightingRadius = r;
	}
	public void setLightingColor(int color){
		lightingColor = color;
	}
	public Block(int id, Texture t0){
		this.id = id;
		this.type = 0;
		this.t0 = t0;
	}
	public Block(int id, Texture t0, Texture t1, Texture t2, Texture t3, Texture t4, Texture t5,Texture t6, Texture t7, Texture t8, Texture t9, Texture t10, Texture t11, Texture t12, Texture t13,Texture t14, Texture t15){
		this.id = id;
		this.type = 1;
		this.t0 = t0;
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
		this.t5 = t5;
		this.t6 = t6;
		this.t7 = t7;
		this.t8 = t8;
		this.t9 = t9;
		this.t10 = t10;
		this.t11 = t11;
		this.t12 = t12;
		this.t13 = t13;
		this.t14 = t14;
		this.t15 = t15;
	}
	public void setDrop(int[][] drop){
		this.drop = drop;
	}
	public void setInstrumentType(int type){
		this.IT = type;
	}
    public void setRBOD(int id){
		RBOD = id;
	}
	public void setStandartDrop(Blocks bl){
		if(drop == null && bl.getItemByBlockId(id) != null)
		    setDrop(new int[][]{{bl.getItemByBlockId(id).id, 1}});
	}
	public void registerTileEntity(TileEntity TileEntity){
		TileEntity.interfaceInitializate();
		this.TileEntity = TileEntity;
	}
	public void setTransparent(){
		isTranspanent = true;
	}
	public boolean getTranspanent(){
		return isTranspanent;
	}
	public void setSize(Vector2i size){
		this.xSize = size.x;
		this.ySize = size.y;
	}
	public void setSize(int x, int y){
		this.xSize = x;
		this.ySize = y;
	}
	public void setCollisionsRectangleByPixels(int x, int y, int w, int h, int TextureW){
		double scX = this.w / 16.0 / TextureW;
	    collisionsRect.setPosition((float)scX * x, (float)scX * y);
		collisionsRect.setSize((float)scX * w, (float)scX * h);
	}
	public Vector2i getSize(){
		return new Vector2i(xSize, ySize);
	}
}
