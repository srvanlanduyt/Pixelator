package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Screen;

public class LifeBarFiller extends HUD {

	private int color = Colors.get(-1, 900, -1, -1);
	private int scale = 2;
	private int x;
	private int y;
	private Screen screen;
	private GameStateManager gsm;
	
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
		int flipTop = 1;
		int flipBottom = 1;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
	}
	
}
