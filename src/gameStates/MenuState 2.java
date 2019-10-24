package gameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Player;
import gfx.Background;
import levels.Level;
import main.Game;
import main.InputHandler;

public class MenuState extends State {

	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {"Start", "Help", "Quit"};
	private InputHandler input;
	long lastKeyPress = 0;
	long screenStart;
	Level level1;
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
	
		try {
			bg = new Background("/res/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("American Typewriter", Font.PLAIN, 28);
			
			font = new Font ("Skia", Font.PLAIN, 12);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void init(Player player) {
		screenStart = System.currentTimeMillis();

	}

	public void update(InputHandler input) {
		if(System.currentTimeMillis() - lastKeyPress > 250 && System.currentTimeMillis() - screenStart > 250) {
			if (input.enter.isPressed()) {
				select();
			}
	
			if (input.up.isPressed()) {
				lastKeyPress = System.currentTimeMillis();
				currentChoice--;
				if (currentChoice == -1) {
					currentChoice = options.length - 1;
				}
			}
			
			if (input.down.isPressed()) {
				lastKeyPress = System.currentTimeMillis();
				currentChoice++;
				if (currentChoice == options.length) {
					currentChoice = 0;
				}
			}
		}	
		bg.update();
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		g.setColor(Color.WHITE);
		g.fillRect(70, 40, 210, 150);
		g.setColor(Color.BLACK);
		g.drawRect(70, 40, 210, 150);
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Pixelator", 115, 100);
		
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if ( i == currentChoice) {
				g.setColor(Color.BLACK);;
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 165, 140 + i * 15);
		}
	}
	
	
	private void select() {
		
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE, player);
		}
		
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.INFOSTATE, null);
		}
		
		if (currentChoice == 2) {
			System.exit(0);
		}
	}
}