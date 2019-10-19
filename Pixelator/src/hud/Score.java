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
	public Player player;
	public String scoreString;

	public Score(GameStateManager gsm, Player player) {
		super(gsm);
		this.x = 150;
		this.y = -15;
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
		setScore();
		Font.render(scoreString, screen, x, y, color, scale);
	}

	
	
}
