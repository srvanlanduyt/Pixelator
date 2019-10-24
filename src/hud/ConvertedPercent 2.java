package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Font;
import gfx.Screen;
import levels.Level;

public class ConvertedPercent extends HUD {

	private int color = Colors.get(0, 0, 0, 555);
	private int scale = 1;
	
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;

	private String output = "0";
	
	public ConvertedPercent(GameStateManager gsm) {
		super(gsm);
		this.x = 90;
		this.y = 0;
	}

	public void CalculateConversion(double percentConverted) {
		int intConverted = (int) percentConverted;
		output = intConverted + "%";
	}
	
	public void render(Screen screen) {
		xOffset = x + screen.getXOffset();
		yOffset = y + screen.getYOffset();
		if (output != null) {
			Font.render(output, screen, xOffset, yOffset, color, scale);
		}
	}

	public void tick() {		
	}
	
	
	
}
