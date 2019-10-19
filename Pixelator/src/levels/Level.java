package levels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Mower;
import entity.Player;
import entity.Shot;
import gameStates.GameStateManager;
import gfx.Booms;
import gfx.MowerBooms;
import gfx.Screen;
import hud.ConvertedPercent;
import hud.HUD;
import hud.LevelDisplay;
import hud.LifeBar;
import hud.LifeBarFiller;
import hud.Score;
import powerUps.HealthPU;
import powerUps.PowerUps;
import powerUps.WeaponPU;
import tiles.Tile;

public class Level {
	private static final Tile Tile = null;
	public static int[] tiles;
	public static int width;
	public static int height;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Mower> liveMowers = new ArrayList<Mower>();
	public Stack<Mower> spareMowers = new Stack<>();
	public List<Booms> booms = new ArrayList<Booms>();
	public List<Shot> shots = new ArrayList<Shot>();
	public Stack<HUD> huds = new Stack<HUD>();
	public Stack<LifeBarFiller> lbfs = new Stack<LifeBarFiller>();
	public List<PowerUps> powerUps = new ArrayList<PowerUps>();
	private String imagePath;
	private BufferedImage image;
	Random r = new Random();
	public GameStateManager gsm;
	
	public Player player;
	public HUD lifeBar;
	public LifeBarFiller lifeBarFiller;
	public Score scoreHUD;
	public HUD levelDisplay;
	public ConvertedPercent convertedPercent;
	public WeaponPU weaponPU1;
	public HealthPU healthPU1;
	private int numOfMowers;
	private int numOfMowersAtTime;
	private long lastShotFired = 0;
	private int fireRate = 100;
	private int fireDistance = 15;
	
	// The hashed out lines below load files from the res file, leaving only
	// what is needed for the mathematic level generator.
	public Level(String imagePath, int numOfMowers, int numOfMowersAtTime, GameStateManager gsm, Player player) {
//		if (imagePath !=null) {
//			this.imagePath = imagePath;
//			this.loadLevelFromFile();
//		} else {
			this.numOfMowers = numOfMowers;
			this.numOfMowersAtTime = numOfMowersAtTime;
			this.width = 42;
			this.height = 30;
			tiles = new int[width * height];
			this.gsm = gsm;
			this.player = player;
			this.generateLevel();
//		}
	}
		
	//random generator
	public int speedNum() {
		int low = 1;
		int high = 5;
		return r.nextInt(high - low) + low;
	}
	
	//random generator
	public int xNum() {
		int low = 1;
		int high = width - 1;
		return r.nextInt(high - low) + low;
	}
	
	//random generator
	public int yNum() {	
		int low = 1;
		int high = height - 1;
		return r.nextInt(high - low) + low;
	}
	
//	private void loadLevelFromFile() {
//	try {
//		this.image = ImageIO.read(Level.class.getResource(this.imagePath));
//		this.width = image.getWidth();
//		this.height = image.getHeight();
//		tiles = new byte [width * height];
//		this.loadTiles();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//}
/*
	private void loadTiles() {
		int[] tileColors = image.getRGB(0, 0, width, height, null, width * 8, 8);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
						tiles[x + y * width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}
	
	
	private void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/	
	public void alterTile(int x, int y, Tile newTile) {
		
		tiles[(x / 8) + ((y / 8) * width)] = newTile.getId();
//		if (image != null) {
//			image.setRGB(x,  y, newTile.getLevelColor());
//		}
	}
	
	//Mathematic level generator
	public void generateLevel() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					tiles[x + y * width] = Tile.STONE.getId(); 
//				} else if (x * y % 100 < 100) {
// 				tiles[x + y * width] = Tile.ANIMATEDGRASS.getId(); 
				} else {
					tiles[x + y * width] = Tile.GRASS.getId(); 
				}					
			}
		}

		
		//Starts the player in the first non-solid square
		if (player == null) {
			System.out.println("player is null");
			System.exit(0);
		}
		while (this.getTile((player.getPlayerX() / 8), (player.getPlayerY() / 8)).isSolid()) {
			for (int y = player.getPlayerY(); y < this.height; y++) {
				player.y++;
				player.x = 0;
				for (int x = player.getPlayerX(); x < this.width; x++) {
					player.x++;
				}
			}
		}
		
		//adds mowers to the stack of level mowers
		for (int i = 0; i < numOfMowers; i++) {
			Mower mower = new Mower(this, "Mower", -1, xNum() * 8, yNum() * 8, speedNum(), Mower.MowerDir());
			spareMowers.push(mower);
		}
		
		
//		if (liveMowers.size() < 10) {
//			addLiveMower(spareMowers.pop());		
//		}
			
		//forces the mower to go right if spawned on the very left of the screen
		//throws a fault if level.numOfMowers < 2
		if (spareMowers.peek().getMowerX() < 32 && spareMowers.peek().getMowerDir() == 2) {
			spareMowers.peek().setMowerDir(3);
		}
		
		//forces the mower to go down if spawned on the very top of the screen
		if (spareMowers.peek().getMowerY() < 32 && spareMowers.peek().getMowerDir() == 0) {
			spareMowers.peek().setMowerDir(1);
		}
	
		//starts spareMowers on non-solid tiles
		for (int i = 0; i < spareMowers.size(); i++) {	
			if (getTile((spareMowers.get(i).getMowerX() / 8), (spareMowers.get(i).getMowerY() / 8)).isSolid()) {
				spareMowers.get(i).setMowerX(xNum());
				spareMowers.get(i).setMowerY(yNum());
			}
		}	
		
		//add player
		addEntity(player);
		
		//add HUDs
		lifeBar = new LifeBar(gsm, 21, -5);
		huds.push(lifeBar);	

		for (int i = 1; i < player.getPlayerHealth(); i++) {
			lifeBarFiller = new LifeBarFiller(gsm, 2 + i, -5);
			lbfs.push(lifeBarFiller);
		}
		
		convertedPercent = new ConvertedPercent(gsm);
		huds.push(convertedPercent);
		
		scoreHUD = new Score(gsm, player);
		huds.push(scoreHUD);
		
		levelDisplay = new LevelDisplay(gsm);
		huds.push(levelDisplay);
		
		//add PowerUps
		weaponPU1 = new WeaponPU(0, this, "PowerUp", xNum() * 8, yNum() * 8);
		powerUps.add(weaponPU1);
		
//		System.out.println(weaponPU1 + " : " + weaponPU1.getPowerUpX() + " / " + weaponPU1.getPowerUpY());

		healthPU1 = new HealthPU(1, this, "HealthUp", xNum() * 8, yNum() * 8);
		powerUps.add(healthPU1);
		
	}
	
		
	
	public void tick() {

		for (Entity e : entities) {
			e.tick();
		}
		
		for (Tile t : Tile.tiles) {
			if (t == null) {
				break;
			}
			t.tick();	
		}
		
		for (HUD h : huds) {
			h.tick();
		}
		
		for (HUD l : lbfs) {
			l.tick();
		}
		
		for (Mower m : liveMowers) {
			m.tick();
		}
		
		for (Shot s : shots) {
			s.tick();
		}
		
		for (Booms b : booms) {
			b.tick();
		}
		
		for (PowerUps p : powerUps) {
			p.tick();
			for (PowerUps q : powerUps) {
//				System.out.println(q + " : " + q.getPowerUpX() + " / " + q.getPowerUpY());
			}
		}

		double startingTiles = width * height;
		double convertedTiles = 0;
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] == 3) {
				convertedTiles += 1;
			}
		}
		
		double percentConverted = ((convertedTiles / startingTiles) * 100.0);
		if (percentConverted >= 100.00) {
			percentConverted = 100.00;
		}
		convertedPercent.CalculateConversion(percentConverted);

		if (player.isShooting) {
			int shotX = 0;
			int shotY = 0;
			int shotDirection;
			
			if (player.getLastKnownDirection() == 0) {
				shotX = player.getPlayerX() - 1;
				shotY = player.getPlayerY() - 5;
			}
			if (player.getLastKnownDirection() == 1) {
				shotX = player.getPlayerX();
				shotY = player.getPlayerY() + 13;
			}
			if (player.getLastKnownDirection() == 2) {
				shotX = player.getPlayerX() - 10;
				shotY = player.getPlayerY() + 4;
			}
			if (player.getLastKnownDirection() == 3) {
				shotX = player.getPlayerX() + 8;
				shotY = player.getPlayerY() + 4;
			}
			if (System.currentTimeMillis() - lastShotFired > fireRate) {
				if (player.isStrafing) {
					shotDirection = player.getLastKnownDirection();
				} else {
					shotDirection = player.getPlayerDirection();
				}
				Shot shot = new Shot(this, "shot", shotX, shotY, 6, shotDirection, fireDistance);
				lastShotFired = System.currentTimeMillis();
				shots.add(shot);
			}
		}
	
	
		//Troubleshooting code for min/max view and mower spawns
//		System.out.println("minViewX: " + player.getMinViewX() + " --> maxViewX: " + player.getMaxViewX() + 
//			               " ***** minViewY: " + player.getMinViewY() + " --> maxViewY: " + player.getMaxViewY() +
//						   " ///// PlayerX: " + player.x + " PlayerY: " + player.y);
	
		
		//adds mowers to the screen to a specified amount
		if (liveMowers.size() < numOfMowersAtTime) {
			if (spareMowers.size() > 0) {
/* Currently causing out of bounds exceptions				//keeps mower spawns 5 tiles from the player
				if ((spareMowers.peek().getMowerX() < player.getPlayerX() + 5 * 8 && spareMowers.peek().getMowerX() > player.getPlayerX() - 5 * 8)) {
					if (spareMowers.peek().getMowerX() > width - 8 * 8) {
						spareMowers.peek().setMowerX(player.getPlayerX() - 8 * 8);	
						addLiveMower(spareMowers.pop());
					} else {
						spareMowers.peek().setMowerX(player.getPlayerX() + 8 * 8);
						addLiveMower(spareMowers.pop());
					}
				} else {
					addLiveMower(spareMowers.pop());
				}
				if ((spareMowers.peek().getMowerY() < player.getPlayerY() + 5 * 8 && spareMowers.peek().getMowerY() > player.getPlayerY() - 5 * 8)) {
					if (spareMowers.peek().getMowerY() > height - 8 * 8) {
						spareMowers.peek().setMowerY(player.getPlayerY() - 8 * 8);
						addLiveMower(spareMowers.pop());
					} else {
						spareMowers.peek().setMowerY(player.getPlayerY() + 8 * 8);
						addLiveMower(spareMowers.pop());
					}
				} else {
					addLiveMower(spareMowers.pop());
				}	
*/					addLiveMower(spareMowers.pop());							
			}
		}
		
		//reverses the pixels so they bounce back and forth
		Iterator<Mower> wallIterator = liveMowers.iterator();
		while(wallIterator.hasNext()) {
			Mower mower = wallIterator.next();
			if (mower.wallCollision) {
				mower.wallCollision = false;
				if (mower.getMowerDir() == 0) {
					mower.setMowerDir(1);
				} else if (mower.getMowerDir() == 1) {
					mower.setMowerDir(0);
				} else if (mower.getMowerDir() == 2) {
					mower.setMowerDir(3);
				} else if (mower.getMowerDir() == 3) {
					mower.setMowerDir(2);
				}
			}
		}
		
		Iterator<Mower> iterator = liveMowers.iterator();
		while(iterator.hasNext()) {
			Mower mower = iterator.next();
			if (mower.collision) { 
				mower.collision = false;
				MowerBooms mowerBoom = new MowerBooms(System.currentTimeMillis(), System.currentTimeMillis() + 25, mower.getMowerX(), mower.getMowerY(), true);
				try {
					iterator.remove();
				} catch (ConcurrentModificationException e) {
					e.printStackTrace();
				}
				addBoom(mowerBoom);
				if (mowerBoom.isAlive == false) {
					removeBoom(mowerBoom);
				}
			}
		}
		
		Iterator<PowerUps> powerUpsIterator = powerUps.iterator();
		while(powerUpsIterator.hasNext()) {
			PowerUps powerUp = powerUpsIterator.next();
			if (powerUp.hasCollided()) {
				powerUpsIterator.remove();
			}
		}
		
		Iterator<Shot> shotIterator = shots.iterator();
		while(shotIterator.hasNext()) {
			Shot shot = shotIterator.next();
			if (!shot.isAlive()) {
				shotIterator.remove();
				if(shotIterator.hasNext()) {
					shotIterator.next();
				}
			}
			
			if (shot.collision) { 
				shot.collision = false;
				try {
					shotIterator.remove();
				} catch (ConcurrentModificationException e) {
					System.out.println("shotIterator: " + shot);
					e.printStackTrace();
					System.exit(0);
				}
			}
		}

		if (player.getPlayerHealth() == 0) {
			System.out.println("Player is DEAD");
			removeEntity(player);
			shots.clear();
//			Font.render("GAME OVER", screen, 50, 50, Colors.get(-1, 555, 555, 555), 1);
		}
		
		if (liveMowers.size() == 0) {
			int nowState = gsm.getState();
			nowState++;
			gsm.setState(nowState, player);
		}
	}
	

	
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0) {
			xOffset = 0;
		}
		if (xOffset > ((width << 3) - screen.width)) {
			xOffset = ((width << 3) - screen.width);
		}
		if (yOffset < 0) {
			yOffset = 0;
		}
		if (yOffset > ((height << 3) - screen.height)) {
			yOffset = ((height << 3) - screen.height);
		}
		
		
		if (screen.width > width * 8) {
			xOffset = (screen.width - (width* 8)) / 2 * -1;
		}
		if (screen.height > height * 8) {
			yOffset = (screen.height - (height * 8)) / 2 * -1;
		}
		
		screen.setOffset(xOffset, yOffset);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) { 
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}
	
	public void reducePlayerHealth(int amount) {
		if (player.getPlayerHealth() > amount + 1) {
			player.health -= amount;
			for (int i = 0; i < amount; i++) {
				lbfs.pop();
			}
		} else {
			lbfs.clear();
			player.setPlayerHealth(0);
		}
	}
	
	public void increasePlayerHealth(int amount) {
		player.health += amount;
	}
	
	public void increasePlayerScore(int amount) {
		player.score += amount;
		scoreHUD.setScore();
	}
	
	public void renderEntities(Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}
	
	public void renderMowers(Screen screen) {
		for (Mower m : liveMowers) {
			m.render(screen);;
		}
	}

	public void renderBooms(Screen screen) {
		for (Booms b : booms) {
			b.render(screen);
		}
	}
	
	public void renderShots(Screen screen) {
		for (Shot s : shots) {	
			s.render(screen);
		}
	}
	
	public void renderHUD(Screen screen) {
		for (HUD h : huds) {
			h.render(screen);
		}
	}
	
	public void renderLBFS(Screen screen) {
		for (LifeBarFiller l : lbfs) {
			l.render(screen);
		}
	}
	
	public void renderPUPS(Screen screen) {
		for (PowerUps p : powerUps) {
			p.render(screen);
		}
	}

	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height) {
			return Tile.VOID;
		}
		return Tile.tiles[tiles[x + y * width]];
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getWidth() {
		return width;
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

	public void removeEntity(Entity entity) {
		this.entities.remove(entity);
	}
	
	public void addLiveMower(Mower mower) {
		this.liveMowers.add(mower);
	}
	
	public void removeLiveMower(Mower mower) {
		this.liveMowers.remove(mower);
	}
	
	public void addSpareMower(Mower mower) {
		this.spareMowers.add(mower);
	}
	
	public void removeSpareMower(Mower mower) {
		this.spareMowers.remove(mower);
	}
	
	public void addBoom(Booms boom) {
		this.booms.add(boom);
	}
	
	public void removeBoom(Booms boom) {
		this.booms.remove(boom);
	}	
	
	public void removePowerUps(PowerUps powerUps) {
		this.powerUps.remove(powerUps);
	}
	
	public int getMowerNum() {
		return liveMowers.size();
	}
}
	
