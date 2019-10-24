package entity;

import java.util.ArrayList;
import java.util.Random;

import gfx.Colors;
import gfx.Screen;
import levels.Level;
import tiles.Tile;

public class Mower extends Mob {

	Random r = new Random();
	public boolean wallCollision = false;
	public boolean collision = false;
	public ArrayList<Integer> mowerTrail = new ArrayList<Integer>();
    public static boolean tilesAlive = false;
//	private int color = Colors.get(-1, randomColorInt(r), randomColorInt(r), randomColorInt(r));
	private int scale = 1;
	private int color;
	private int dir;
	private int refreshSpeed = 4;
	public int damage = 2;

	
	//constructor for mower
	public Mower(Level level, String name, int color, int x, int y, int speed, int dir) {
		super(level, name, x, y, speed);
		this.color = Colors.get(-1, randomColorInt(r), randomColorInt(r), randomColorInt(r));
		this.dir = dir;
		this.solid = true;
	}
	
	public int getMowerX() {
		return x;
	}
	
	public void setMowerX(int x) {
		this.x = x;
	}
	
	public int getMowerY() {
		return y;
	}
	
	public void setMowerY(int y) {
		this.y = y;
	}
	
	public int getMowerColor() {
		return color;
	}
	
	public int getMowerDir() {
		return dir;
	}
	
	public void setMowerDir(int dir) {
		this.dir = dir;
	}
	
	public static int MowerDir() {
		return new Random().nextInt(4);
	}
	
	public int randomColorInt(Random r) {
		int low = 1;
		int high = 255;
		if (r == null) {
			System.out.println("r is null");
			System.exit(0);
		}
		return r.nextInt(high - low) + low;
	}
	
	//mowers move from their origin to the first obstacle in a straight line
	public void tick() {
		
		//setting direction values
		int xa = 0; 
		int ya = 0;
		
		{
			if (dir == 0) {
					ya--;
			}
			if (dir == 1) {
					ya++;
			}	
			if (dir == 2) {
					xa--;
				}	
			if (dir == 3) {
					xa++;
			}	
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya, false); 
			isMoving = true;
		} else {
			isMoving = false;
		}
		
		//make GRASS into ANIMATED GRASS
		if (isMoving) {
			int tempX = this.getMowerX();
			int tempY = this.getMowerY();
			//keeps return values positive
//			System.out.println("Before Adjustment - X: " + tempX + " / tempY: " + tempY);
			if (tempX < 8) {
				tempX = 8; 
			}
			if (tempY < 8) {
				tempY = 8;
			}
			
			if (tempX > level.width * 8 - 9) {
				tempX = level.width * 8 - 9;
			}
			
			if (tempY < 2 * 8) {
				tempY = 17;
			}
			
			if (tempY > level.height * 8 - 9) {
				tempY = level.height * 8 - 9;
			}
			
//			System.out.println("After Adjustment - X: " + tempX + " / tempY: " + tempY);
//			System.out.println("");
			mowerTrail.add(tempX);
			mowerTrail.add(tempY);
			
			for (int i = 0; i < mowerTrail.size(); i += 2) {
				int tileX = mowerTrail.get(i);
				int tileY = mowerTrail.get(i + 1); 
				level.alterTile(tileX, tileY, /*this.color,*/ Tile.ANIMATEDGRASS);
			}
			//clear the trails to prevent massive slow down
			mowerTrail.clear();
		}
	}

	
	
	//rendering the mower
	public void render(Screen screen) {
		int xTile = 0; 
		int yTile = 26;
		int flipTop = 0;//(Game.tickCount >> refreshSpeed) & 1;
		int flipBottom = 0;//(Game.tickCount >> refreshSpeed) & 1;
					
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
	}
	
	protected boolean isSolidEntitiy(int x, int y) {
		if (level == null) {
			return false;
		}
		for (Entity e1 : level.entities) {
			for (Entity e2 : level.entities) {
				if (e1.x == e2.x || e1.y == e2.y) {
					return true; 
				}
			}
		}
		return false;
	}
	
	//Wall collision is 100% on the mowers
	public boolean hasCollided(int xa, int ya, Level tempLevel) {
		
		//0 is up
		if (dir == 0) {
			
			int xMin = 0; 
			int xMax = 3;
			int yMin = 0;
			int yMax = 3;
			
			for (int x = xMin; x < xMax; x++) {
				if (isSolidTile(xa, ya, x, yMin)) {
					wallCollision = true;
				}
			}	
			for (int x = xMin; x < xMax; x++) {
			
				if (isSolidTile(xa, ya, x, yMax)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMin, y)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMax, y)) {
					wallCollision = true;
				}
			}
		}		
		// 1 is down
		if (dir == 1) {
			
			int xMin = 0; 
			int xMax = 3;
			int yMin = 0;
			int yMax = 3;
			
			for (int x = xMin; x < xMax; x++) {
				if (isSolidTile(xa, ya, x, yMin)) {
					wallCollision = true;
				}
			}	
			for (int x = xMin; x < xMax; x++) {
			
				if (isSolidTile(xa, ya, x, yMax)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMin, y)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMax, y)) {
					wallCollision = true;
				}
			}
		}
		
		//2 is left
		if (dir == 2) {
			
			int xMin = 0; 
			int xMax = 3;
			int yMin = 0;
			int yMax = 3;
			
			for (int x = xMin; x < xMax; x++) {
				if (isSolidTile(xa, ya, x, yMin)) {
					wallCollision = true;
				}
			}	
			for (int x = xMin; x < xMax; x++) {
			
				if (isSolidTile(xa, ya, x, yMax)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMin, y)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMax, y)) {
					wallCollision = true;
				}
			}
		}
		
		//3 is right
		if (dir == 3) {
			
			int xMin = 0; 
			int xMax = 3;
			int yMin = 0;
			int yMax = 3;
			
			for (int x = xMin; x < xMax; x++) {
				if (isSolidTile(xa, ya, x, yMin)) {
					wallCollision = true;
				}
			}	
			for (int x = xMin; x < xMax; x++) {
			
				if (isSolidTile(xa, ya, x, yMax)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMin, y)) {
					wallCollision = true;
				}
			}
			for (int y = yMin; y < yMax; y++) {
				if (isSolidTile(xa, ya, xMax, y)) {
					wallCollision = true;
				}
			}
		}
		
		//Mower VS Player collision
		for (Entity e : level.entities) {
			if ((x + 2 < e.x + 8 && x +14 > e.x + 8) && (y + 2 < e.y + 8 && y + 14 > e.y + 8)) {
				collision = true;
				level.reducePlayerHealth(damage);
			}
		}
		
		for (Shot s : level.shots) {
			if ((x + 2 < s.x + 8 && x +14 > s.x + 8) && (y + 2 < s.y + 8 && y + 14 > s.y + 8)) {
				collision = true;
			}
		}
		return collision;
	}
	
	public boolean isAnimatedTile(int x, int y) {
		if (level == null) {
			return false;
		}
		Tile testTile = level.getTile(x, y);
		if (testTile.isSolid()) {
			return true;
		}
		return false;
	}
}
