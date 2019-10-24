package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Font;
import gfx.Screen;
import levels.Level;

public class LevelDisplay extends HUD {

	private int color = Colors.get(0, 0, 0, 555);
	private int scale = 1;
	
	private int x = 55;
	private int y = 0;
	private int xOffset;
	private int yOffset;
	
	public String whatLevel;
	GameStateManager gsm;
	
	public LevelDisplay(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;

	}
	
	public void setWhatLevel() {
		if (gsm == null) {
			System.out.println("GSM is null");
		}
		
		switch (gsm.getState()) {
			case 0 :
				break;
			case 1 : whatLevel = "Lv 1";
				break;
			case 2 : whatLevel = "Lv 2";
				break;
			case 3 : whatLevel = "Lv 3";
				break;
			case 4 : whatLevel = "Lv 4";
				break;
			case 5 : whatLevel = "Lv 5";
				break;
			case 6 : whatLevel = "Lv 6";
				break;
			case 7 : whatLevel = "Lv 7";
				break;
			case 8 : whatLevel = "Lv 8";
				break;
			case 9 : whatLevel = "Lv 9";
				break;
			case 10 : whatLevel = "Lv 10";
				break;
		
		}
	}

	public void render(Screen screen) {
		xOffset = x + screen.getXOffset();
		yOffset = y + screen.getYOffset();
		if (whatLevel != null) {
			Font.render(whatLevel, screen, xOffset, yOffset, color, scale);
		}
	}

	public void tick() {
		setWhatLevel();
	}
}
