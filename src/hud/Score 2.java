package hud;

import entity.Player;
import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Font;
import gfx.Screen;

public class Score extends HUD {
	
	private int color = Colors.get(0, 0, 0, 555);
	private int scale = 1;
	
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	
	public Player player;
	public String scoreString;

	public Score(GameStateManager gsm, Player player) {
		super(gsm);
		this.x = 150;
		this.y = 0;
		this.player = player;
	}
	
	
	public void setScore() {
		if (player == null) {
			System.out.println("player is null");
			System.exit(0);
		}
		int score = player.getPlayerScore();
		scoreString = Integer.toString(score);
	}
	
	
	public void render(Screen screen) {
		xOffset = x + screen.getXOffset();
		yOffset = y + screen.getYOffset();
		if (scoreString != null) {
			Font.render(scoreString, screen, xOffset, yOffset, color, scale);
		}
	}


	public void tick() {
		setScore();
	}
}
