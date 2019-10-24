package powerUps;

import gfx.Colors;
import gfx.Screen;
import levels.Level;

public class LongShot extends WeaponPU {

	private int x;
	private int y;
	private int scale = 1;
	private int color = Colors.get(555, 555, 1955, 5500);
	private boolean active = false;
	private boolean used = false;
	long start;
	long length = 10000;
	
	
	public LongShot(int type, Level level, String name, int x, int y) {
		super(type, level, name, x, y);
		this.x = x;
		this.y = y;
	}
	
	public void Activate() {
		active = true;
		start = System.currentTimeMillis();
	}
	
	public void tick() {
		System.out.println(System.currentTimeMillis() - start);
		if (System.currentTimeMillis() - start > length) {
			active = false;
			used = true;
		}
	}
	
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 18;
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
	
	public boolean isUsed() {
		return used;
	}
	
	public boolean isActive() {
		return active;
	}

}
