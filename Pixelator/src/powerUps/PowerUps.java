package powerUps;

import gfx.Screen;
import levels.Level;

public abstract class PowerUps {
	
	public boolean solid;
//	public static int x;
//	public static int y;
	protected static Level level;
	public static String name;
	protected static int type;
	
	public static PowerUps[] powerUps = new PowerUps[256];
//	public static PowerUps THREE_WAY_SHOT = new WeaponPU(0, level, name);
//	public static PowerUps HEALTH_INCREASE = new HealthPU(1, level, name);

	public PowerUps(int type, Level level, String name) {//, int x, int y) {
		this.level = level;
		this.name = name;
//		this.x = x;
//		this.y = y;
		this.type = type;
	}
/*
	public int getPowerUpX() {
		return x;
	}
	
	public int getPowerUpY() {
		return y;
	}
*/	
	public int getType() {
		return type;
	}
	
	public abstract boolean hasCollided();
 
	public abstract void tick();

	public abstract void render(Screen screen);
	
}
