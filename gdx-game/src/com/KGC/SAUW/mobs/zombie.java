package com.KGC.SAUW.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.KGC.SAUW.*;
import java.util.Random;

public class zombie extends mob{
/*   Random random = new Random();
	Vector2d v = new Vector2d();
	int h = Gdx.graphics.getHeight();
	int w = Gdx.graphics.getWidth();
    public boolean exist = false;
	public int posX, posY;
	int plW = w / 16 * 10 / 26;
	int plH = w / 16;
	int mX = (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
	int mY = (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));
	maps m;
	player pl;
    int zombieNum;
	collisions CWB = new collisions();
	boolean cmr;
	boolean cml;
	boolean cmu;
	boolean cmd;

	Animation walkL;
	Animation walkR;
	Animation walkU;
	Animation walkD;

	int acX,acY;

    int type;

	TextureRegion[] walkFrames;
	TextureRegion currentFrame;

	int zombieSpeed = 5;
	int rot = 0;
	float stateTime;
	float hitTime;
    int nextAccChanging = random.nextInt(7);
    float accChangingTime;
    private mob zombieP;
    mobs mobs;
	public zombie(int x, int y, maps m, Textures t, player pl, int type, mobs mobs, int num) {
		this.mX = x;
		this.mY = y;
		this.m = m;
		this.pl = pl;
		exist = true;
        this.type = type;
		this.posX = x * (w / 16);
		this.posY = y * (w / 16);
        this.mobs = mobs;
		this.zombieNum = num;
		TextureRegion[][] tmp =TextureRegion.split(t.zombie, t.zombie.getWidth() / 4, t.zombie.getHeight() / 3);
		walkFrames = new TextureRegion[4 * 3];
		int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
		walkL = new Animation(0.3f, walkFrames[0], walkFrames[1]);
		walkR = new Animation(0.3f, walkFrames[2], walkFrames[3]);
		walkD = new Animation(0.3f, walkFrames[4], walkFrames[5]);
		walkU = new Animation(0.3f, walkFrames[6], walkFrames[7]);
		currentFrame = walkFrames[4];
		stateTime = 0f;
	}
	public zombie(){
		exist = false;
	}
	@Override
	public void render(SpriteBatch b) {
		b.draw(currentFrame, this.posX, this.posY, plW, plH);
	}
	@Override
	public void update() {

        stateTime += Gdx.graphics.getDeltaTime();
        v.setZero();
      if (type == 0) {
            if (Maths.distance(pl.mX, pl.mY, mX, mY) <= 7) {
                v.setfa(Maths.angleBetweenVectors(pl.posX, pl.posY, posX, posY));
                acX = (int)(v.x * zombieSpeed);
                acY = (int)(v.y * zombieSpeed);

            } else {
                zombieP = mobs.getNearMob(mX, mY, "zombie", 1);
                if (zombieP != null && Maths.distance(mX, mY, zombieP.mX, zombieP.mY) <= 20) {
                    v.setfa(Maths.angleBetweenVectors(zombieP.posX, zombieP.posY, posX, posY));
                    acX = (int)(v.x * zombieSpeed / 2);
                    acY = (int)(v.y * zombieSpeed / 2);
                    if(Maths.distance(mX, mY, zombieP.mX, zombieP.mY) == 0){
                        acX = 0;
                        acY = 0;
                    }
                }
            }
            
        } else if (type == 1) {
            if (Maths.distance(pl.mX, pl.mY, mX, mY) <= 20) {
                v.setfa(Maths.angleBetweenVectors(pl.posX, pl.posY, posX, posY));
                acX = (int)(v.x * (zombieSpeed / 2));
                acY = (int)(v.y * (zombieSpeed / 2));

            }
        }/*
		if (Maths.distance(mX, mY, pl.mX, pl.mY) <= 1) {
			hitTime += Gdx.graphics.getRawDeltaTime();
			if (hitTime >= 1) {
				hitTime = 0;
				pl.hit(1);
			}
		} else {
			hitTime = 0;
		}

		if (acX != 0 || acY != 0) {
			if (Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) < 315 && Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) > 225) {
				rot = 0;
				currentFrame = walkU.getKeyFrame(stateTime, true);
			} else if (Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) < 225 && Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) > 135) {
				rot = 1;
				currentFrame = walkR.getKeyFrame(stateTime, true);
			} else if (Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) > 45 && Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) < 135) {
				rot = 2;
				currentFrame = walkD.getKeyFrame(stateTime, true);
			} else if (Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) < 45 || Maths.angleBetweenVectors(posX, posY, posX + acX, posY + acY) > 315) {
				rot = 3;
				currentFrame = walkL.getKeyFrame(stateTime, true);
			}
		} else {
			if (rot == 0) {
				currentFrame = walkFrames[9];
			} else if (rot == 1) {
				currentFrame = walkFrames[3];
			} else if (rot == 2) {
				currentFrame = walkFrames[8];
			} else if (rot == 3) {
				currentFrame = walkFrames[1];
			}
		}

		mX = (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
		mY = (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));
		boolean[] collisionWB = CWB.getCollisionWithBlocks(mX, mY, posX, posY, plW, plH, acX, acY, m);
		boolean[] collisionWE = CWB.getCollisionWithEntity(posX, posY, plW, plH, mobs, pl, acX, acY);
		cmu = collisionWB[0];
		cmd = collisionWB[1];
		cml = collisionWB[2];
		cmr = collisionWB[3];
		cmu = (!cmu) ? false : collisionWE[0];
		cmd = (!cmd) ? false : collisionWE[1];
		cml = (!cml) ? false : collisionWE[2];
		cmr = (!cmr) ? false : collisionWE[3];
		if (m.map0[mY][mX + 1][1].id != 4 && posX + plW > (mX + 1) * (w / 16)) {
			posX -= 1;
		}
		if (m.map0[mY][mX - 1][1].id != 4 && posX < (mX - 1) * (w / 16) + (w / 16)) {
			posX += 1;
		}
		if (m.map0[mY + 1][mX][1].id != 4 && posY + plH > (mY + 1) * (w / 16)) {
			posY -= 1;
		}
		if (m.map0[mY - 1][mX][1].id != 1 && posY < (mY - 1) * (w / 16) + (w / 16)) {
			posY += 1;
		}
		if (acX > 0 && !cmr) {
			acX = 0;
		}
		if (acX < 0 && !cml) {
			acX = 0;
		}
		if (acY > 0 && !cmu) {
			acY = 0;
		}
		if (acY < 0 && !cmd) {
			acY = 0;
		}
		posX += acX;
		posY += acY;
    }*/
}
