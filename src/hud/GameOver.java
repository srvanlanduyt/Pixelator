package hud;

import entity.Player;
import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Font;
import gfx.Screen;
import levels.Level;

public class GameOver extends HUD {

	private int color = Colors.get(0, 0, 0, 555);
	private int scale = 1;
	private Level level;
	private Player player;
	private GameStateManager gsm;
	
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	
	private String output;
	
	public GameOver(GameStateManager gsm, Player player, Level level) {
		super(gsm);
		this.x = 50;
		this.y = 50;
		this.player = player;
		this.level = level;
	}
	
	public void setOutput() {
		output = "GAME OVER \n " +
				 "Score: " + Integer.toString(player.getPlayerScore()) + "\n";
	}

	public void tick() {
		if (player.getPlayerHealth() <= 0) {
			System.out.print("GAME OVER");
			setOutput();
		}
	}

	public void render(Screen screen) {
		xOffset = x + screen.getXOffset();
		yOffset = y + screen.getYOffset();
		if (output != null) {
			Font.render(output, screen, xOffset, yOffset, color, scale);
			System.out.print("Rendering");
		}
	}
}
