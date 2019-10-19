package levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import entity.Player;
import gameStates.GameStateManager;
import gameStates.State;
import gfx.Screen;
import gfx.SpriteSheet;
import main.Game;
import main.InputHandler;

public class Level1State extends State {
	
	private GameStateManager gsm;
	public static Level level1;
	private Graphics2D g;
	
	private Color titleColor = new Color(128, 0, 0);
	private Font titleFont = new Font("American Typewriter", Font.PLAIN, 28);
	
		
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init(Player player) {
		System.out.println("Achievement Unlocked!!!! -> Made it to Level1State!");
		player = new Player(level1, Game.WIDTH / 2, Game.HEIGHT / 2, 20, 0, Game.input);
		level1 = new Level("/levels/level2.png", 2, 10, gsm, player);

		/* At this point we need to close the menuState graphics and load in the Level graphics.  There
		 * is still no engine to run Level, but the variables need to be in a separate class so that
		 * adding more levels will be far easier in the future....  
		 */
		
	}

	public void update(InputHandler input) {
		
	}

	public void draw(java.awt.Graphics2D g) {
/*
		g.setColor(Color.WHITE);
		g.setColor(titleColor);;
		g.setFont(titleFont);
		g.drawString("Level 1", 100, 70);
*/
	}
}
