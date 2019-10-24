package powerUps;

import entity.Entity;
import gfx.Colors;
import gfx.Screen;
import levels.Level;

public class HealthPU extends PowerUps {
	private int scale = 1;
	public boolean collision = false;
	private int color = Colors.get(555, 555, 955, 500);
	private int x;
	private int y;
	

	public HealthPU(int type, Level level, String name, int x, int y) {
		super(type, level, name);
		this.x = x;
		this.y = y;
	}


	public boolean hasCollided() {
		//Player VS PUPs collision
		for (Entity e : level.entities) {
			if ((x + 2 < e.x + 8 && x + 14 > e.x + 8) && (y + 2 < e.y + 8 && y + 14 > e.y + 8)) {
				collision = true;
			} else {
				collision = false;
			}
		}
		return collision;
	}
	

	public void tick() {	
		hasCollided();
	}


	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 20;
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



	public void Activate() {
		level.increasePlayerHealth(2);
	}

	public boolean isUsed() {
		return false;
	}
}
