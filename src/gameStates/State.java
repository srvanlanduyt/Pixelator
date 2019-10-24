package gameStates;

import java.util.Random;

import entity.Player;
import levels.Level;
import main.Game;
import main.InputHandler;

public abstract class State {

	protected GameStateManager gsm;
	public Level level;
	public Player player;
	Random r = new Random();
	
	public abstract void init(Player player);
	public abstract void update(InputHandler input);
	public abstract void draw(java.awt.Graphics2D g);
//	public abstract void keyPressed(int k);
//	public abstract void keyReleased(int k);
	
	//random generator
	public int xNum() {
//		int low = 1;
//		int high = Level.getWidth() - 1;
		return 15;// r.nextInt(high - low) + low;
	}
	
	//random generator
	
	public int yNum() {	
//		int low = 1;
//		int high = Level.getHeight() - 1;
		return 15;// r.nextInt(high - low) + low;
	}
}