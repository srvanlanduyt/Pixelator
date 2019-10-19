package entity;

import gameStates.GameStateManager;
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
import main.Game;
import tiles.Tile;

public abstract class Mob extends Entity {

	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	public GameStateManager gsm = Game.gsm;
	public Level tempLevel;
	
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void move(int xa, int ya, boolean vertPrimary) {
		
		if (gsm == null) {
			System.out.println("GSM is null");
			System.exit(0);
		}
		
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
		
		
		//keep diagonal speed the same as vert & horiz speed
		if (xa != 0 && ya != 0) { 
			if (!vertPrimary) {
				move(xa, 0, vertPrimary);
				move(0, ya, vertPrimary);
			} else {
				move(0, ya, vertPrimary);
				move(xa, 0, vertPrimary);
			}
			numSteps--;
			return;
		}
		numSteps++;	
		
		//selecting the direction (up ya--, down ya++, left xa--, right xa++)
		if (!hasCollided(xa, ya, tempLevel)) {
			if (ya < 0) {
				movingDir = 0;
			}
			if (ya > 0) {
				movingDir = 1;
			}
			if (xa < 0) {
				movingDir = 2;
			}
			if (xa > 0) {
				movingDir = 3;
			}
			x += xa * (speed);
			y += ya * (speed);
		}
	}
	
	public abstract boolean hasCollided(int xa, int ya, Level tempLevel);
	
	protected boolean isSolidTile(int xa, int ya, int x, int y) {
			
		if (tempLevel == null) {
			return false;
		}
		//tile currently on
		Tile lastTile = tempLevel.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		//tile going to
		Tile newTile = tempLevel.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		if (!lastTile.equals(newTile) && newTile.isSolid()) {
			return true;
		}
		return false;
	}
	
/*	//not finished..............
	protected boolean wrapAround(int xa, int ya, int x, int y) {
		//tile currently on
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		//tile going to
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y) + ya >> 3);	
		if (!lastTile.equals(newTile) && !newTile.isSolid()) {
			
		}
		return false;
	
	} 
*/

	public String getName() {
		return name;
	}
}

