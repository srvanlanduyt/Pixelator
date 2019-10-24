package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Screen;

public class LifeBar extends HUD {

	private int color = Colors.get(-1, 150, 300, 555);
	private int scale = 1;
	private int x;
	private int y;
	
	public LifeBar (GameStateManager gsm, int x, int y) {
		super(gsm);
		this.x = x;
		this.y = y;
	}
	
	public void tick() {

	}
		
	public void render(Screen screen) {

		int xTile = 2;
		int yTile = 30;
		
		int modifier = 8 * scale;
		int xOffset = x + screen.getXOffset();
		int yOffset = y + screen.getYOffset();
		
		screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, color, 0, scale);
		screen.render(xOffset + modifier + 8, yOffset, (xTile + 1) + yTile * 32, color, 0, scale);
		screen.render(xOffset + modifier + 16, yOffset, (xTile + 2) + yTile * 32, color, 0, scale);
		screen.render(xOffset + modifier + 24, yOffset, (xTile + 3) + yTile * 32, color, 0, scale);
	}
}
