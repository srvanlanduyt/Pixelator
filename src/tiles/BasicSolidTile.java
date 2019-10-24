package tiles;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor, boolean animatedTile) {
		super(id, x, y, tileColor, levelColor, animatedTile);
		this.solid = true;
		this.animatedTile = false;
	}
}
