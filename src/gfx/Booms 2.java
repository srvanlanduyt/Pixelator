package gfx;

public abstract class Booms {
	
	public long born;
	public long die;
	public int x;
	public int y;
	public boolean isAlive;
	
	public Booms(long born, long die, int x, int y, boolean isAlive) {
		this.born = born;
		this.die = die;
		this.x = x;
		this.y = y;
		this.isAlive = true;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);

}
