package levels;

import java.awt.Graphics2D;

import entity.Player;
import gameStates.GameStateManager;
import gameStates.State;
import main.InputHandler;
import tiles.Tile;

public class Level2State extends State {
	
	public static Level level2;
	
	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init(Player player) {
		Tile.levelChangeColor();
		System.out.println("Achievement Unlocked!!!! -> Made it to Level 2!");
		level2 = new Level("/levels/level2.png", 5, 5, gsm, player);
	}


	public void update(InputHandler input) {

	}

	public State getStateState() {
		return this;
	}
	
	public void draw(Graphics2D g) {
		
	}

}