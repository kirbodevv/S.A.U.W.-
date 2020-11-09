package com.KGC.SAUW.mobs;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.KGC.SAUW.*;
import java.util.ArrayList;

public class mobs {
	public ArrayList<mob> mobs = new ArrayList<mob>();
	private SpriteBatch b;
	private maps m;
	private Textures t;
    public mobs(SpriteBatch b, maps m, Textures t) {
		this.b = b;
		this.m = m;
		this.t = t;
	}
	public void update() {
		for (mob mob : mobs) {
			mob.update();
		}
	}
	public void render(Camera2D cam) {
		for (mob mob : mobs) {
			if(Maths.rectCrossing(mob.posX, mob.posY, mob.plW, mob.plH, cam.X, cam.Y, cam.W, cam.H))
			mob.render(b);
		}
	}
	public boolean spawn(mob mob) {
			mobs.add(mob);
		    return true;
	}
    public mob getNearMob(int X, int Y, String mob, int type) {
        int id = -1;
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).type.equals(mob)) {
                if (i == 0 || id == -1) {
                        id = i;
                } else {
                    if (Maths.distance(X, Y, mobs.get(i).mX, mobs.get(i).mY) < Maths.distance(X, Y, mobs.get(id).mX, mobs.get(id).mY)) {
                        id = i;
                    }
                }
			}
            return mobs.get(id);
        }
        return null;
    }
	/*public int[][] getAllMobsPosAndBounds(player pl) {
		int[][] posAndBounds = new int[maxMobsCount + 1][7];
		for (int i = 0; i < mobs.size(); i++) {
			posAndBounds[i][0] = mobs.get(i).posX;
			posAndBounds[i][1] = mobs.get(i).posY;
			posAndBounds[i][2] = mobs.get(i).plW;
			posAndBounds[i][3] = mobs.get(i).plH;
			posAndBounds[i][4] = mobs.get(i).mX;
			posAndBounds[i][5] = mobs.get(i).mY;
			//posAndBounds[i][6] = (mobs.get(i).exist) ? 1 : 0;
		}
		if (num != -1) {
			posAndBounds[maxMobsCount - 1][0] = pl.posX;
			posAndBounds[maxMobsCount - 1][1] = pl.posY;
			posAndBounds[maxMobsCount - 1][2] = pl.plW;
			posAndBounds[maxMobsCount - 1][3] = pl.plH;
			posAndBounds[maxMobsCount - 1][4] = pl.mX;
			posAndBounds[maxMobsCount - 1][5] = pl.mY;
			posAndBounds[maxMobsCount - 1][6] = 1;
		}
		return posAndBounds;
	}*/

}
