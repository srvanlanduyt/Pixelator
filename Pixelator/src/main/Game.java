/*
 * 		README.TXT DATA
 * 	
 *		version 1.2 - 3/13/19 (in Chicago for Riot Fest)
 *			Colors are auto generated when the games starts to alter the backgrounds.  This still needs to be changed to generated for a new
 *			instance of each level.   The Colors needs to be stepped up to be full color instead of the odd earthy tones that there currently is.  
 *			Shots need to be dropped to a normal level. Also, readme.txt was started. 
 *			
 *		version 1.3 - 9/13/19 (in Chicago for Riot Fest)
 *			The level colors are now changing at ever level.  
 *			Maureen suggested that the colors are a little to much so we need to tone down the visuals.  Next addition will be the strafing function. 
 *			
 *		version 1.4 - 9/17/19
 *			Level colors are now much better, there is still some black showing in the tiles that I want removed, but generally it is good.
 *			Strafing is done and working fine and the shots have been moved to work with that new feature.  Shots have also been throttled back
 *			both on distance and fire rate allowing for some easy level ups.  NEXT FOCUS - heads up display... Diagonal moving enemies... power ups...
 *			
 *		version 1.5 - 9/22/19 (While Sarah was in Seattle)
 *			The player now moves between levels and everything responds to the static GSM manager is now the master GSM of everything.  To continue
 *			with the heads up display, the playable window needs to be moved down a notch and the health bar, level, score and whatever else can be
 *			put in the black space above. Then, we'll move onto Diagonal moving enemies and power ups. 
 *			
 *		version 2.7 - 10/5/19
 *
 *		All versions moving forward have been moved to a 2.x to represent the original mower game. 1.6 will be the last of the 1.x and 2.7 will be the
 *		next file in the series.  
 *		
 *			I have to find a way to get this file to show in Eclipse... anyway ->
 *			The HUD is mostly implemented.  I'm still not sure where (0, 0) truly is so I'm using (22, -5) as where the HUD starts.  This is fine
 *			for now, but I'm sure it'll make things interesting later on. The tile generation is messing up the algorithm for the calculation of
 *			convertedPercent.  This is the next issue I'll look into.  The next move will be to put in the powerups and health additions since this
 *			game is wicked hard right now.  Overall I am liking this progress and I think there will be a lot of fun to be had once the gameplay
 *			is balanced.  
 *
 *			This is a persistent fault that I need to troubleshoot {
 *				at java.base/java.util.ArrayList$Itr.remove(ArrayList.java:1009)
 *				at levels.Level.tick(Level.java:385)
 *				at main.Game.tick(Game.java:217)
 *				at main.Game.run(Game.java:181)
 * 				at java.base/java.lang.Thread.run(Thread.java:834)
 *			}
 *
 *			The levels are very difficult in the beginning.  There needs to be a more gradual incline to the start of the game. 
 *
 *
 */

package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Font;
import gfx.Screen;
import gfx.SpriteSheet;
import levels.Level;
import levels.Level10State;
import levels.Level1State;
import levels.Level2State;
import levels.Level3State;
import levels.Level4State;
import levels.Level5State;
import levels.Level6State;
import levels.Level7State;
import levels.Level8State;
import levels.Level9State;

public class Game extends Canvas implements Runnable {

private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 360;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Pixelator"; 
	public static final double VERSION_NUM = 2.7; 
	
	private JFrame frame;
	public boolean running = false;
	public static int tickCount = 0;
	public static int frames = 0;
	public static InputHandler input;
//	public Level level;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colors = new int[12 * 12 * 12];
	private Graphics2D g;
	public static GameStateManager gsm;
	public Level tempLevel;
	

	private Screen screen;
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); 
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
	
	public void init() {

		gsm = new GameStateManager();
		g = (Graphics2D) image.getGraphics();
		
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					
					colors[index++] = rr << 16 | gg  << 8 | bb;
				}
			}
		}
		
		input = new InputHandler(this);
		//loading the graphics sheet
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/res/sprite_sheet.png"));
		 

	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	
	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0; 
		
		init();
		
		while (running) {
			
			switch (gsm.getState()) {
				case 0 : 
					break;
				case 1 : tempLevel = Level1State.level1;
					break;
				case 2 : tempLevel = Level2State.level2;
					break;
				case 3 : tempLevel = Level3State.level3;
					break;
				case 4 : tempLevel = Level4State.level4;
					break;
				case 5 : tempLevel = Level5State.level5;
					break;
				case 6 : tempLevel = Level6State.level6;
					break;
				case 7 : tempLevel = Level7State.level7;
					break;
				case 8 : tempLevel = Level8State.level8;
					break;
				case 9 : tempLevel = Level9State.level9;
					break;
				case 10 : tempLevel = Level10State.level10;
					break;
			}
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			
			while (delta >= 1) {
				ticks++;	
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender) {
				frames++;
			}	
				
			if (gsm.getState() != gsm.MENUSTATE && gsm.getState() != gsm.INFOSTATE) {
				render();
			}
			
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;			
				System.out.println(ticks + " ticks, " + frames + " frames");
//				System.out.println("SCORE: " + level.player.score + ", HEALTH: " + level.player.health);
				frames = 0;
				ticks = 0;
			}	
		}	
	}
	
	public void tick() {
		tickCount++;
		update();
		draw();
		drawToScreen();

		if (gsm.getState() != gsm.MENUSTATE && tempLevel != null) { 
			tempLevel.tick();
		}

	}
	
	private void update() {
		gsm.update();
	}
	
	private void draw() {
		gsm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image,  0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy(); 
		if (bs == null) {
			createBufferStrategy(3); 
			return;
		}
		

		
		int xOffset = tempLevel.player.x - (screen.width / 2);
		int yOffset = tempLevel.player.y - (screen.height / 2);
		
		tempLevel.renderTiles(screen, xOffset, yOffset);
		
		for (int x = 0; x < tempLevel.width; x++) {
			int color = Colors.get(-1, 80, 129, 50);
			if (x % 10 == 0 && x!= 0) {
				color = Colors.get(-1, 150, 801, 100);
			}
//			Font.render("WTF", screen, 10, 50, Colors.get(000, -1, -1, 555), 1);
		}
		
		tempLevel.renderEntities(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		tempLevel.renderMowers(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		tempLevel.renderBooms(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}	
		
		tempLevel.renderShots(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		tempLevel.renderHUD(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		tempLevel.renderLBFS(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		tempLevel.renderPUPS(screen);
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public static void main(String[] args) {
		new Game().start();
	}
	
	
	
}
