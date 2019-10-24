package powerUps;

import entity.Entity;
import entity.Mower;
import entity.Player;
import gfx.Colors;
import gfx.Screen;
import levels.Level;

public class WeaponPU extends PowerUps {
	private int scale = 1;
	public boolean collision = false;
	private int color = Colors.get(555, 555, 1955, 5500);
	private boolean active;
	private boolean used;
	private int x;
	private int y;
	
	
	public WeaponPU(int type, Level level, String name, int x, int y) {
		super(type, level, name);
		this.x = x;
		this.y = y;
	}


	public boolean hasCollided() {
		//Player VS PUPs collision
		for (Entity e : level.entities) {
			if ((x + 2 < e.x + 8 && x + 14 > e.x + 8) && (y + 2 < e.y + 8 && y + 14 > e.y + 8)) {
				collision = true;
			} else {
				collision = false;
			}
		}
		return collision;
	}
	

	public void tick() {	
		hasCollided();
		isActive();
	}


	private boolean isActive() {
		return active;
	}


	public void render(Screen screen) {

	}



	public void Activate() {
	}

	public boolean isUsed() {
		return used;
	}
}
