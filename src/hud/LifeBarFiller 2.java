package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Screen;

public class LifeBarFiller extends HUD {

	private int color = Colors.get(-1, 900, -1, -1);
	private int scale = 1;
	private int x;
	private int y;

	
	public LifeBarFiller(GameStateManager gsm, int x, int y) {
		super(gsm);
		this.x = x;
		this.y = y;
	}

	public void tick() {

	}
	
	public void render(Screen screen) {

		int xTile = 6;
		int yTile = 30;
		
		int modifier = 8 * scale;
		int xOffset = x + screen.getXOffset();
		int yOffset = y + screen.getYOffset();
		
		screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, color, 1, scale);
		screen.render(xOffset + modifier - modifier, yOffset, (xTile + 1) + yTile * 32, color, 1, scale);
	}	
}
