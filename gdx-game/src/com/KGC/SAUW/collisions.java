package com.KGC.SAUW;
import com.badlogic.gdx.Gdx;
import com.KGC.SAUW.mobs.mobs;
import com.badlogic.gdx.math.Rectangle;

public class collisions {
    public void getCollisionWithBlocks(Rectangle r, Rectangle PWAX, Rectangle PWAY, Vector2i ac, maps m) {
		for (int y = 0; y < m.map0.length; y++) {
			for (int x = 0; x < m.map0[y].length; x++) {
				if (m.map0[y][x][0].id != 4) {
					if (PWAX.overlaps(m.map0[y][x][0].block)) {
						ac.x = 0;
					}
					if (PWAY.overlaps(m.map0[y][x][0].block)) {
						ac.y = 0;
					}
					if(r.overlaps(m.map0[y][x][0].block)){
						float xl, xr, yu, yd;
                        xl = r.x + r.width - m.map0[y][x][0].block.x;
						xr = m.map0[y][x][0].block.x + m.map0[y][x][0].block.width - r.x;
						yd = r.y + r.height - m.map0[y][x][0].block.y;
						yu = m.map0[y][x][0].block.y + m.map0[y][x][0].block.height - r.y;
						if(xl > 0 && xr > 0 && yd > 0 && yu > 0){
							if(xl < xr && xl < yd && xl < yu) ac.x = -(int)xl;
							if(xr < xl && xr < yd && xr < yu) ac.x = (int)xr;
							if(yu < xl && yu < xr && yu < yd) ac.y = (int)yu;
							if(yd < xl && yd < xr && yd < yu) ac.y = -(int)yd;
						}
					}
				}
			}
		}
	}
    public boolean[] getCollisionWithEntity(int posX, int posY, int plW, int plH, mobs mobs, player pl, int acX, int acY) {
        boolean[] boolArr = new boolean[4];
		boolArr[0] = true;
		boolArr[1] = true;
		boolArr[2] = true;
		boolArr[3] = true;

		for (int i = 0; i < mobs.mobs.size() + 1; i++) {
			int[] posAndBounds = new int[4];
			if (i == mobs.mobs.size()) {
				posAndBounds[0] = pl.posX;
				posAndBounds[1] = pl.posY;
				posAndBounds[2] = pl.plW;
				posAndBounds[3] = pl.plH;
			} else {
				posAndBounds[0] = mobs.mobs.get(i).posX;
				posAndBounds[1] = mobs.mobs.get(i).posY;
				posAndBounds[2] = mobs.mobs.get(i).plW;
				posAndBounds[3] = mobs.mobs.get(i).plH;
			}
			if (Maths.rectCrossing(posX + acX, posY, plW, plH, posAndBounds[0], posAndBounds[1], posAndBounds[2], posAndBounds[3])) {
				if (acX > 0) boolArr[3] = false;
				if (acX < 0) boolArr[2] = false;
			}
			if (Maths.rectCrossing(posX, posY + acY, plW, plH, posAndBounds[0], posAndBounds[1], posAndBounds[2], posAndBounds[3])) {
				if (acY > 0) boolArr[0] = false;
				if (acY < 0) boolArr[1] = false;
			}
		}
		return boolArr;
	}
}
