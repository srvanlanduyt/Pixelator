package entity;

import java.util.ArrayList;

import gfx.Colors;
import gfx.Screen;
import levels.Level;
import tiles.Tile;

public class Shot extends Mob {
	
	public boolean collision = false;
	public ArrayList<Integer> shotTrail = new ArrayList<Integer>();
	private int color = Colors.get(-1, -1, 1900, 1100);
	private int scale = 1;
	private int dir;
	private int life;
	private int refreshSpeed = 4;
	
	public Shot(Level level, String name, int x, int y, int speed, int dir, int life) {
		super(level, name, x, y, speed);
		this.dir = dir;
		this.solid = true;
		this.life = life;
	}
	
	public int getShotX() {
		return x;
	}
	
	public int getShotY() {
		return y;
	}
	
	public boolean isAlive() {
		if (life <= 0) {
			return false;
		} 
		return true;
	}	
	
	
	public boolean hasCollided(int xa, int ya, Level tempLevel) {
/*
		int xMin = 0; 
		int xMax = 3;
		int yMin = 0;
		int yMax = 3;
		
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				collision = true;
			}
		}	
		for (int x = xMin; x < xMax; x++) {
		
			if (isSolidTile(xa, ya, x, yMax)) {
				collision = true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				collision = true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				collision = true;
			}
		}
*/	
	
	//Mower VS Shot collision
	for (Mower m : level.liveMowers) {
		if ((x + 2 < m.x + 8 && x + 14 > m.x + 8) && (y + 2 < m.y + 8 && y + 14 > m.y + 8)) {
			collision = true;
			level.increasePlayerScore(100);
		}
		//keeps shot rendering within the bounds of the level
		if (x < 7 || x > level.width * 8 - 12 || y < 8 || y > level.height * 8 - 9) {
			collision = true;
		}
	}
	return collision;
	}
	


	public void tick() {
		//setting direction values
		int xa = 0; 
		int ya = 0;
		
		{
			if (dir == 0) {
					ya--;
					life -= 1;
			}
			if (dir == 1) {
					ya++;
					life -= 1;
			}	
			if (dir == 2) {
					xa--;
					life -= 1;
				}	
			if (dir == 3) {
					xa++;
					life -= 1;
			}	
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya, true); 
			isMoving = true;
		} else {
			isMoving = false;
		}
		
		//make ANIMATEDGRASS into GRASS
		if (isMoving) {
			int tempX = this.getShotX();
			int tempY = this.getShotY();
			
			//keeps outer walls from changing
			if (tempX < 8) {
				tempX = 8; 
			}
			if (tempY < 8) {
				tempY = 8;
			}
			
			if (tempX > level.width * 8 - 9) {
				tempX = level.width * 8 - 9;
			}
			
			if (tempY > level.height * 8 - 9) {
				tempY = level.height * 8 - 9;
			}
			
			shotTrail.add(tempX);
			shotTrail.add(tempY);
			
			for (int i = 0; i < shotTrail.size(); i += 2) {
				int tileX = shotTrail.get(i);
				int tileY = shotTrail.get(i + 1); 
				if (isAnimatedTile(tileX, tileY)) {
					level.increasePlayerScore(1);
					level.alterTile(tileX, tileY, Tile.GRASS);
				}
			}
			//clear the trails to prevent massive slowdown
			shotTrail.clear();
		}
		
		//powerUps
		if (level.longShot.isActive()) {
			life += 1000;
		}
		
		if (level.lazerShot.isActive()) {
			level.setFireRate(0);
		}
		
		if (level.tripleShot.isActive()) {

		}
		
		
	}
	
	public boolean isAnimatedTile(int x, int y) {
		if (level == null) {
			return false;
		}
		Tile testTile = level.getTile(x / 8, y / 8);
		if (testTile.isAnimatedTile()) {
			return true;
		}
		return false;
	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 22;
		int walkingSpeed = 4;
		int flipTop = 0;//(numSteps >> walkingSpeed) & 1;
		int flipBottom = 0;//(numSteps >> walkingSpeed) & 1;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
	}
	
	public void setShotDir(int dir) {
		this.dir = dir;
	}
	
	public void setShotX(int x) {
		this.x = x;
	}
	
	public void setShotY(int y) {
		this.y = y;
	}
	
}
