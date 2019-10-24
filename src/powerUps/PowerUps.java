package powerUps;

import gfx.Screen;
import levels.Level;

public abstract class PowerUps {
	
	public boolean solid;
	protected static Level level;
	public static String name;
	protected static int type;
	
	public static PowerUps[] powerUps = new PowerUps[256];

	public PowerUps(int type, Level level, String name) {
		this.level = level;
		this.name = name;


		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public abstract boolean hasCollided();
 
	public abstract void tick();

	public abstract void render(Screen screen);

	public abstract void Activate();

	public abstract boolean isUsed();
	
}
