package entity;

import gfx.Screen;
import levels.Level;

public abstract class Entity {

	public boolean solid;
	public int x, y;
	protected Level level;
	
	
	public Entity(Level level) {
		init(level);
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
	
	
	
	
	
	
	
}