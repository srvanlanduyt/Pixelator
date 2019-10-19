package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Screen;
import main.Game;

public abstract class HUD {
	
	private int color = Colors.get(-1, 100, 25, 255);
	private int scale = 2;
	private int x;
	private int y;
	private Screen screen;
	private GameStateManager gsm;
	
	public HUD (GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public void tick() {
		
	}
		
	public void render(Screen screen) {

		int xTile = 4;
		int yTile = 30;
		int walkingSpeed = 4;
		int flipTop = 1;
		int flipBottom = 1;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
	//	screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
	//	screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
	}
}
