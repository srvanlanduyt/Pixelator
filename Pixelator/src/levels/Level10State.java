package levels;

import java.awt.Graphics2D;

import entity.Player;
import gameStates.GameStateManager;
import gameStates.State;
import main.Game;
import main.InputHandler;
import tiles.Tile;

public class Level10State extends State {
	
	public static Level level10;
	
	public Level10State(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init(Player player) {
		Tile.levelChangeColor();
		System.out.println("Achievement Unlocked!!!! -> Made it to Level 10!");
		level10 = new Level("/levels/level2.png", 1000, 750, gsm, player);	
	}

	public void update(InputHandler input) {
	
	}

	public State getStateState() {
		return this;
	}
	
	public void draw(Graphics2D g) {
	
	}
}