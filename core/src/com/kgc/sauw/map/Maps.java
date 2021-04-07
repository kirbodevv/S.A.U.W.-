package com.kgc.sauw.map;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.entity.Entities;
import com.badlogic.gdx.Gdx;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import java.util.Random;
import com.kgc.sauw.entity.ItemEntity;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.environment.Blocks;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.Camera2D;

public class Maps {
	Random r = new Random();
	public Tile[][][] map0 = new Tile[40][40][3];
	int width = Gdx.graphics.getWidth();
	public void generateWorld(World w, Items ITEMS) {
		Random r = new Random();
		for (int i = 0; i < map0.length; i++) {
            for (int j = 0; j < map0[i].length; j++) {
				w.setBlock(j, i, 1, 1);
				w.setBlock(j, i, 2, 2);
				w.setBlock(j, i, 0, 4);
				if(r.nextInt(75) == 0){
					w.setBlock(j, i, 0, 6);
				} else if(r.nextInt(75) == 0){
					w.setBlock(j, i, 0, 10);
				} else if(r.nextInt(75) == 0){
					w.setBlock(j, i, 0, 9);
				}
				w.setBlock(i, 0, 0, 14);
				w.setBlock(i, map0[i].length - 1, 0, 14);
				w.setBlock(0, j, 0, 14);
				w.setBlock(map0.length - 1, j, 0, 14);
			}
        }
		if (w.entities != null) {
			Random r1 = new Random();
			int WIDTH = Gdx.graphics.getWidth();
			for (int i = 0; i < map0.length; i++) {
				for (int j = 0; j < map0[i].length; j++) {
					if (r1.nextInt(75) == 0) {
						w.entities.spawn(new ItemEntity(j * WIDTH / 16, i * WIDTH / 16, 7, 1, 0));
					}
					if (r1.nextInt(50) == 0) {
						w.entities.spawn(new ItemEntity(j * WIDTH / 16, i * WIDTH / 16, 12, 1, 0));
					}
					if(r1.nextInt(100) == 0){
						w.entities.spawn(new ItemEntity(j * WIDTH / 16, i * WIDTH / 16, 18, 1, 0));
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
							j++;
						}
					}
				}
			}
			buffer.put("tileEntitys", tileEntitys);
		}
		buffer.put("tileEnCount", tileEntitysCount);
		return buffer;
	}
	public void update(Camera2D cam, GameInterface GI, Player pl, World w, Blocks bl, Entities entities, Items items) {
		for (int i = 0;i < map0.length;i++) {
			for (int j = 0; j < map0[i].length; j++) {
				for (int l = 0; l < map0[i][j].length; l++) {
					map0[i][j][l].update(cam, GI, pl, w, this, bl, entities, items);
				}
			}
		}
	}
}
