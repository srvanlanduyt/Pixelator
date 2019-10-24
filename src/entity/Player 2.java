package entity;

import java.util.Stack;

import gfx.Colors;
import gfx.Screen;
import levels.Level;
import main.InputHandler;
import powerUps.WeaponPU;

public class Player extends Mob{
	
	public int health;
	public int score = 0;
	private InputHandler input;
	private int color = Colors.get(-1, -1, 555, 1);
	private int scale = 1;
	public int maxViewX;
	public int minViewX;
	public int maxViewY;
	public int minViewY;
	public boolean isShooting;
	public boolean isStrafing;
	public int lastKnownDirection;
	private int amount;
	Stack<Integer> keyOrder = new Stack<Integer>();

	//constructor for new player
	public Player(Level level, int x, int y, int health, int score, InputHandler input) {		
		super(level, "player1", x, y, 1);
		this.health = health;
		this.input = input;
		this.score = score;
	}
	
	public int getPlayerDirection() {
		return movingDir;
	}

	public int getPlayerX() {
		return x;
	}

	public int getPlayerY() {
		return y;
	}
	
	
	
	public int getPlayerScore() {
		return score;
	}
	
	public int getPlayerHealth() {
		return health;
	}
	
	public void setPlayerHealth(int amount) {
		health = this.amount;
	}
	
	public int getLastKnownDirection() {
		return lastKnownDirection;
	}

/*
	//THE MATH AIN'T RIGHT... but, it is close
	public int getMinViewX() {
		if (x <= 188) {
			minViewX = 8;
		} else if (x > 188 && x < 332) {
			minViewX = x - 180;
		} else if (x >= 332) {
			minViewX = 152;
		}
		return minViewX;
	}
	
	public int getMaxViewX() {
		if (x >= 332) {
			maxViewX = 496;
		} else if (x < 332 && x > 188) {
			maxViewX = x + 164;
		} else if (x <= 188)
			maxViewX = 360;
		return maxViewX;
	}	
	
	public int getMinViewY() {
		if (y <= 151) {
			minViewY = 8;
		} else if (y > 151 && y < 377) {
			minViewY = y - 135;
		} else if (y >= 377) {
			minViewY = 235;
		}
		return minViewY;
	}
	
	public int getMaxViewY() {
		if (y >= 377) {
			maxViewY = 496;
		} else if (y < 377 && y > 135) {
			maxViewY = y + 135;
		} else if (y <= 135)
			maxViewY = 272;
		return maxViewY;
	}
*/


	public void tick() {
		
		if (isMoving && movingDir == 2) {
			movingDir = 2;
		}
		
		if (isMoving && movingDir == 3) {
			movingDir = 3;
		}
		
		//setting direction values
		int xa = 0; 
		int ya = 0;
		isShooting = false;
		isStrafing = false;
		boolean vertPrimary = false;
		
		if (input == null) {
			System.out.println("input is null");
			System.exit(0);
		}
		
		if (input.shoot.isPressed()) {
			isShooting = true;
		}
		
		if (input.strafe.isPressed()) {
			isStrafing = true;
		}
		
		//up = 0
		if (input.up.isPressed()) {
			if (!keyOrder.contains(0)) {
				keyOrder.push(0);
			}
			ya--;
			if (isStrafing == false) {
				lastKnownDirection = 0;
			}	
		} else {
			if (keyOrder.contains(0)) {
				int index = keyOrder.indexOf(0);
				keyOrder.remove(index);
			}
		}

		//down = 1 
		if (input.down.isPressed()) {
			if (!keyOrder.contains(1)) {
				keyOrder.push(1);
			}
			ya++;
			if (isStrafing == false) {
				lastKnownDirection = 1;
			}
		} else {
			if (keyOrder.contains(1)) {
				int index = keyOrder.indexOf(1);
				keyOrder.remove(index);
			}
		}
		
		//left = 2
		if (input.left.isPressed()) {
			if (!keyOrder.contains(2)) {
				keyOrder.push(2);
			}
			xa--;
			if (isStrafing == false) {
				lastKnownDirection = 2;
			}
		} else {
			if (keyOrder.contains(2)) {
				int index = keyOrder.indexOf(2);
				keyOrder.remove(index);
			}
		}
		
		//right = 3
		if (input.right.isPressed()) {
			if (!keyOrder.contains(3)) {
				keyOrder.push(3);
			}
			xa++;
			if (isStrafing == false) {
				lastKnownDirection = 3;
			}
		} else {
			if (keyOrder.contains(3)) {
				int index = keyOrder.indexOf(3);
				keyOrder.remove(index);
			}
		}

		
		if (xa != 0 || ya != 0) {
			int firstPress = keyOrder.peek();
			if (firstPress == 0 || firstPress == 1) {
				vertPrimary = true;
			}
			move(xa, ya, vertPrimary); 
			isMoving = true;	
		} else {
			isMoving = false;
			keyOrder.empty();
		}
	}

	//render the Player
	public void render(Screen screen) {

		int xTile = 0;
		int yTile = 28;
		int holdoverInt; 
		int walkingSpeed = 4;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		//selecting tiles of player image
		if (!isStrafing) {
			xTile = 0;
			if (movingDir == 0) {
			} else if (movingDir == 1) {
				xTile += 2;
				
			} else if (movingDir == 2) {
				flipTop = 1;
				flipBottom = 1;
				if (((numSteps >> walkingSpeed) & 1) % 2 == 0) {
					xTile += 4;
				} else {
					xTile += 6;
				}
			} else if (movingDir == 3) {
				flipTop = 0;
				flipBottom = 0;
				if (((numSteps >> walkingSpeed) & 1) % 2 == 0) {
				xTile += 4;
				} else {
					xTile += 6;
				}
			}
		} else if (isStrafing) {
			if (lastKnownDirection == 0) {
				xTile = 0;
			} else if (lastKnownDirection == 1) {
				xTile += 2;
			} else if (lastKnownDirection == 2) {
				flipTop = 1;
				flipBottom = 1;
				if (((numSteps >> walkingSpeed) & 1) % 2 == 0) {
					xTile += 4;
				} else {
					xTile += 6;
				}
			} else if (lastKnownDirection == 3) {
				flipTop = 0;
				flipBottom = 0;
				if (((numSteps >> walkingSpeed) & 1) % 2 == 0) {
				xTile += 4;
				} else {
					xTile += 6;
				}
			}
		}
			
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
	}
	
	//see if the player has collided with a solid tile
	public boolean hasCollided(int xa, int ya, Level tempLevel) {
		int xMin = 0; 
		int xMax = 7;
		int yMin = 0;
		int yMax = 7;

		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}	
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		
		return false;
	}
}	
