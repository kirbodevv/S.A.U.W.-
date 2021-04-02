package com.kgc.sauw.map;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.kgc.sauw.UI.Container;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.environment.Blocks;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.entity.ItemEntity;
import com.kgc.sauw.entity.Entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.ArrayList;
import java.util.Random;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraDataFactory;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.utils.ExtraData;

public class Tile implements com.intbyte.bdb.ExtraData {
	public static class TileEntityFactory implements ExtraDataFactory {
		private Blocks b;
		public TileEntityFactory(Blocks b) {
			this.b = b;
		}
		@Override
		public com.intbyte.bdb.ExtraData getExtraData() {
			return new Tile(b);
		}
	}
	public int id;
	public int x, y, z;
	public int type;
	public int damage;
	public int biomId = 0;
	private int instrumentType;
	private Blocks blocks;
	public Tile.TileEntity TileEntity = null;
	public Interface Interface = null;
	float timer;
	public TextureRegion t;
	public Rectangle block;
	private int WIDTH = Gdx.graphics.getWidth();

	public ArrayList<ExtraData> extraData = new ArrayList<ExtraData>();
    public ArrayList<Container> containers = new ArrayList<Container>();

	public PointLight PL;
	public Body body;

	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		buffer.put("id", id);
		buffer.put("coords", new int[]{x, y, z});
		for (Container c : containers) {
			buffer.put(c.ID, new int[]{c.getId(), c.getCount(), c.getData()});
		}
		return buffer.toBytes();
	}

	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
	    DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes, begin, end);
		this.x = buffer.getIntArray("coords")[0];
		this.y = buffer.getIntArray("coords")[1];
		this.z = buffer.getIntArray("coords")[2];
		this.id = buffer.getInt("id");
		createTile(x, y, z, blocks.getBlockById(id));
		for (Container c : containers) {
			c.setItem(buffer.getIntArray(c.ID)[0],
					  buffer.getIntArray(c.ID)[1],
					  buffer.getIntArray(c.ID)[2]);
		}
	}
	public Tile(Blocks b) {
		this.blocks = b;
	}
	public void createTile(int X, int Y, int Z, Blocks.Block bl) {
		this.x = X;
		this.y = Y;
		this.z = Z;
		this.id = bl.id;
		this.type = bl.type;
		this.block = new Rectangle();
		this.block.setPosition(X * WIDTH / 16 + bl.collisionsRect.x, Y * WIDTH / 16 + bl.collisionsRect.y);
		this.block.setSize(bl.collisionsRect.width, bl.collisionsRect.height);
		if (bl.t0 != null) t = TextureRegion.split(bl.t0, bl.t0.getWidth(), bl.t0.getHeight())[0][0];
		if (bl.TileEntity != null) {
			this.TileEntity = bl.TileEntity;
			this.Interface = bl.TileEntity.getGuiScreen();
			if (Interface != null)
				for (int i = 0; i < Interface.slots.size(); i++) {
					if (!Interface.slots.get(i).isInventorySlot) {
						containers.add(new Container(Interface.slots.get(i).ID));
					}
				}
			this.TileEntity.initialize(this);
		}
		damage = bl.getMaxDamage();
		instrumentType = bl.IT;

	}
	public void setBody(Body body) {
		this.body = body;
	}
    public void setLight(RayHandler rh, Blocks.Block bl) {
		if (bl.lightingRadius != -1) {
			PL = new PointLight(rh, 100, bl.lightingColor, bl.lightingRadius * WIDTH / 16, x * WIDTH / 16 + WIDTH / 32, y * WIDTH / 16 + WIDTH / 32);
		    PL.attachToBody(body);
		}
	}
	public Container getContainer(String ID) {
		for (int i = 0; i < containers.size(); i++) {
			if (containers.get(i).ID.equals(ID)) {
				return containers.get(i);
			}
		}
		return null;
	}
	public void setExtraData(String key, Object value) {
		for (ExtraData ED : extraData) {
			if (ED.key.equals(key)) {
				ED.setValue(value);
				return;
			}
		}
		extraData.add(new ExtraData(key));
		for (ExtraData ED : extraData) {
			if (ED.key.equals(key)) {
				ED.setValue(value);
				return;
			}
		}
	}
	public Object getExtraData(String key) {
		for (ExtraData ED : extraData) {
			if (ED.key.equals(key)) {
				return ED.getValue();
			}
		}
		return null;
	}
	public int hit(int IT) {
		if (IT == instrumentType) {
			damage -= 1;
			return 1;
		} else {
			return 0;
		}
	}
	public void update(Camera2D cam, GameInterface GI, Player pl, World w, Maps m, Blocks b, Entities entities, Items items) {
		if (damage <= 0 && id != 4) {
			w.setBlock(x, y, z, b.getBlockById(id).RBOD);
			for (int i = 0; i < b.getBlockById(id).drop.length; i++) {
				Random r = new Random();
				int xx = r.nextInt(WIDTH / 16) + WIDTH / 16 * x;
				int yy = r.nextInt(WIDTH / 16) + WIDTH / 16 * y;
				entities.spawn(new ItemEntity(xx, yy, b.getBlockById(id).drop[i][0], b.getBlockById(id).drop[i][1], 0, items));
			}
		}
		if (TileEntity != null) {
			timer += Gdx.graphics.getRawDeltaTime();
			if (timer >= 0.05) {
				timer = 0;
				TileEntity.tick(this);
			}
			if (Gdx.input.isTouched() && !GI.isTouched()) {
				double sc = (double)cam.W / WIDTH;
				int cX = (int)(Gdx.input.getX() * sc + cam.X);
				int cY = (int)(cam.H - Gdx.input.getY() * sc + cam.Y);
				int bX = (cX - (cX % (WIDTH / 16))) / (WIDTH / 16);
				int bY = (cY - (cY % (WIDTH / 16))) / (WIDTH / 16);
				if (bX == x && bY == y) {
					TileEntity.click(this);
					//Interface.isOpen = true;
				}
			}
			if (GI.interactionButton.wasClicked && Maths.distance(x, y, pl.currentTileX, pl.currentTileY) <= 1.5  && ((pl.currentTileY + 1 == y && pl.rot == 0) || (pl.currentTileX + 1 == x && pl.rot == 1) || (pl.currentTileY - 1 == y && pl.rot == 2) || (pl.currentTileX - 1 == x && pl.rot == 3))) {
				TileEntity.onInteractionButtonPressed(this);
				if (Interface != null)
					if (!Interface.isOpen)
				        Interface.open(x, y, z);
			}
		}
	}

	public static abstract class TileEntity {
		public void interfaceInitialize() {

		}

		public abstract void initialize(Tile tile);

		public abstract void tick(Tile tile);

		public abstract void click(Tile tile);

		public abstract Interface getGuiScreen();

		public abstract void onInteractionButtonPressed(Tile tile);

		public abstract void randomTick(Tile tile);

		public boolean renderIf(Tile tile) {
			return true;
		}

		public boolean collisionsIf(Tile tile) {
			return true;
		}

	}
}
