package gfx;

import java.util.Random;

public class MowerBooms extends Booms {
	
	public long refreshRate = (die - born) / 3;
	public int mowerTick = 0;
	public int scale = 1;
	public int color = Colors.get(-1, randomMowerBoomColor(r), randomMowerBoomColor(r), randomMowerBoomColor(r));
	static Random r = new Random();
	
	public MowerBooms(long born, long die, int x, int y, boolean isAlive) {
		super(born, die, x, y, isAlive);	
	}

	public void tick() {
		mowerTick++;
	}

	public static int randomMowerBoomColor(Random r) {
		int low = 1;
		int high = 255;
		if (r == null) {
			System.out.println("r is null");
			System.exit(0);
		}
		return r.nextInt(high - low) + low;
	}

	public void render(Screen screen) {
		
		int xTile = 0;
		int yTile = 24;
		
		//Still now mowerBoom remove function... just rendering a blank tile.
		if (mowerTick > refreshRate && mowerTick < refreshRate - 1) {
			xTile = 2;
			System.out.println("***** " + mowerTick + " > " + refreshRate + " ******");
		}
		if (mowerTick > refreshRate * 2) {
			xTile = 4;
		}
		if (mowerTick > refreshRate * 3) {
			xTile = 6;
			this.isAlive = false;
		}
		
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		int flipTop = 0;
		int flipBottom = 0;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
	}
}
