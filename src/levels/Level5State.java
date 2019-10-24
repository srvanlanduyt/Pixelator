package levels;

import java.awt.Graphics2D;

import entity.Player;
import gameStates.GameStateManager;
import gameStates.State;
import main.Game;
import main.InputHandler;
import tiles.Tile;

public class Level5State extends State {
	
	public static Level level5;
	
	public Level5State(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init(Player player) {
		Tile.levelChangeColor();
		System.out.println("Achievement Unlocked!!!! -> Made it to Level 5!");
		level5 = new Level("/levels/level2.png", 25, 50, gsm, player);
	}

	public void update(InputHandler input) {
		
	}

	public State getStateState() {
		return this;
	}
	
	public void draw(Graphics2D g) {
	
	}
}