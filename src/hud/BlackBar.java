package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Font;
import gfx.Screen;

public class BlackBar extends HUD {

	private int color = Colors.get(0, 0, 0, 555);
	private int scale = 1;
	
	private int x = 0;
	private int y = 0;
	private int xOffset;
	private int yOffset;
	
	public String blackString = "    ";
	GameStateManager gsm;
	
	public BlackBar(GameStateManager gsm) {
		super(gsm);
		for (int i = 0; i < Screen.MAP_WIDTH; i++) {
			blackString += " ";
		}	
	}

	public void tick() {
	}

	public void render(Screen screen) {
		xOffset = x + screen.getXOffset();
		yOffset = y + screen.getYOffset();
		if (blackString != null) {
			Font.render(blackString, screen, xOffset, yOffset, color, scale);
		}
	}

}
