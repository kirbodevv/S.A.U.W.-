package com.KGC.SAUW;
import com.KGC.SAUW.mobs.Mobs;
import com.badlogic.gdx.Gdx;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import java.util.Random;
import com.KGC.SAUW.mobs.ItemMob;

public class Maps {
	Random r = new Random();
	OpenSimplexNoise noise = new OpenSimplexNoise();
	public Tile[][][] map0 = new Tile[40][40][3];
	private int[][] biomsMap;
	int width = Gdx.graphics.getWidth();

	public int getBiomFromNoiseValue(double v) {
		if (v < 0.3) return 1;
		else if (v < 0.66) return 2;
		else if (v < 0.8) return 12;
		else return 8;
	}
	public double noise(OpenSimplexNoise noise, double nx, double ny) {
		return noise.eval(nx, ny) / 2 + 0.5;
	}
	public void generateWorld(World w, Items ITEMS) {
		Random r = new Random();
		biomsMap = new int[map0.length][map0[0].length];
		OpenSimplexNoise noise = new OpenSimplexNoise();
		for (int i = 0; i < map0.length; i++) {
            for (int j = 0; j < map0[i].length; j++) {
				double nx = j / 40 - 0.5;
				double ny = i / 40 - 0.5;
				double e = 1 * noise(noise, 1 * nx, 1 * ny)
					+  0.5 * noise(noise, 2 * nx, 2 * ny);
				biomsMap[j][i] = getBiomFromNoiseValue(e);
				w.setBlock(j, i, 1, biomsMap[j][i]);
				//w.setBlock(j, i, 1, 1);
				w.setBlock(j, i, 2, 2);
				w.setBlock(j, i, 0, 4);

				w.setBlock(i, 0, 0, 14);
				w.setBlock(i, map0[i].length - 1, 0, 14);
				w.setBlock(0, j, 0, 14);
				w.setBlock(map0.length - 1, j, 0, 14);
			}
        }
		if (w.mobs != null) {
			Random r1 = new Random();
			int WIDTH = Gdx.graphics.getWidth();
			for (int i = 0; i < map0.length; i++) {
				for (int j = 0; j < map0[i].length; j++) {
					if (r1.nextInt(75) == 0) {
						w.mobs.spawn(new ItemMob(j * (int)WIDTH / 16, i * (int)WIDTH / 16, 7, 1, 0, ITEMS));
					}
					if (r1.nextInt(50) == 0) {
						w.mobs.spawn(new ItemMob(j * (int)WIDTH / 16, i * (int)WIDTH / 16, 12, 1, 0, ITEMS));
					}
				}
			}
		}
	}
	public DataBuffer toDataBuffer() {
		DataBuffer buffer = new DataBuffer();
		int[] worldArray = new int[map0.length * map0[0].length * map0[0][0].length];
		int[] mapDmg = new int[map0.length * map0[0].length * map0[0][0].length];
		ExtraData[] tileEntitys;
		int i = 0;
		int tileEntitysCount = 0;
		for (int y = 0; y < map0.length; y++) {
			for (int x = 0; x < map0[y].length; x++) {
				for (int z = 0; z < map0[y][x].length; z++) {
					worldArray[i] = map0[y][x][z].id;
					mapDmg[i] = map0[y][x][z].damage;
					if (map0[y][x][z].TileEntity != null) {
						tileEntitysCount++;
					}
					i++;
				}
			}
		}
		buffer.put("mapIds", worldArray);
		buffer.put("mapDamage", mapDmg);
		if (tileEntitysCount > 0) {
			tileEntitys = new ExtraData[tileEntitysCount];
			int j = 0;
			for (int y = 0; y < map0.length; y++) {
				for (int x = 0; x < map0[y].length; x++) {
					for (int z = 0; z < map0[y][x].length; z++) {
						if (map0[y][x][z].TileEntity != null) {
							tileEntitys[j] = map0[y][x][z];
						}
					}
				}
			}
			buffer.put("tileEntitys", tileEntitys);
		}
		buffer.put("tileEnCount", tileEntitysCount);
		return buffer;
	}
	public void update(Camera2D cam, GameInterface GI, Player pl, World w, Blocks bl, Mobs mobs, Items items) {
		for (int i = 0;i < map0.length;i++) {
			for (int j = 0; j < map0[i].length; j++) {
				for (int l = 0; l < map0[i][j].length; l++) {
					map0[i][j][l].update(cam, GI, pl, w, this, bl, mobs, items);
				}
			}
		}
	}
}
