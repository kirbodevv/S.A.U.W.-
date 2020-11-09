package com.KGC.SAUW;
import com.KGC.SAUW.mobs.mobs;
import java.util.Random;
import com.KGC.SAUW.mobs.itemMob;
import com.badlogic.gdx.Gdx;
import com.intbyte.bdb.DataBuffer;

public class maps {
	Random r = new Random();
	OpenSimplexNoise noise = new OpenSimplexNoise();
	public Tile[][][] map0 = new Tile[40][40][3];
	int width = Gdx.graphics.getWidth();
	
	public void generateWorld(World w){
		for(int i = 0; i < map0.length; i++){
            for(int j = 0; j < map0[i].length; j++){
				//map0[i][j][1] = new Tile(j, i, 1, bl.getBlockById(1));
				w.setBlock(j, i, 1, 1);
				//map0[i][j][2] = new Tile(j, i, 2, bl.getBlockById(2));
				w.setBlock(j, i, 2, 2);
				/*if(r.nextInt(50) == 0){
					//map0[i][j][0] = new Tile(j, i, 0, bl.getBlockById(6));
					w.setBlock(j, i, 0, 6);
				} else if(r.nextInt(25) == 0){
					w.setBlock(j, i, 0, 16);
				} else {*/
					//map0[i][j][0] = new Tile(j, i, 0, bl.getBlockById(4));
					w.setBlock(j, i, 0, 4);
				//}
				//map0[i][0][0] = new Tile(i, 0, 0, bl.getBlockById(14));
				w.setBlock(i, 0, 0, 14);
				//map0[i][map0[i].length - 1][0] = new Tile(i, map0[i].length - 1, 0, bl.getBlockById(14));
				w.setBlock(i, map0[i].length - 1, 0, 14);
				//map0[0][j][0] = new Tile(0, j, 0, bl.getBlockById(14));
				w.setBlock(0, j, 0, 14);
				//map0[map0.length - 1][j][0] = new Tile(map0.length - 1, j, 0, bl.getBlockById(14));
				w.setBlock(map0.length - 1, j, 0, 14);

			}
        }
	}
	public DataBuffer toDataBuffer(){
		DataBuffer buffer = new DataBuffer();
		int[] worldArray = new int[map0.length * map0[0].length * map0[0][0].length];
		int[] mapDmg = new int[map0.length * map0[0].length * map0[0][0].length];
		int i = 0;
		for(int y = 0; y < map0.length; y++){
			for(int x = 0; x < map0[y].length; x++){
				for(int z = 0; z < map0[y][x].length; z++){
					worldArray[i] = map0[y][x][z].id;
					mapDmg[i] = map0[y][x][z].damage;
					i++;
				}
			}
		}
		buffer.put("mapIds", worldArray);
		buffer.put("mapDamage", mapDmg);
		return buffer;
	}
	public void update(Camera2D cam, gameInterface GI, player pl, World w, blocks bl, mobs mobs, items items){
		for (int i = 0;i < map0.length;i++) {
			for (int j = 0; j < map0[i].length; j++) {
				for (int l = 0; l < map0[i][j].length; l++) {
					map0[i][j][l].update(cam, GI, pl, w, this, bl, mobs, items);
				}
			}
		}
	}
}
