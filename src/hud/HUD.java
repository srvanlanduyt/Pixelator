package hud;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Screen;
import gfx.SpriteSheet;
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
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
}
