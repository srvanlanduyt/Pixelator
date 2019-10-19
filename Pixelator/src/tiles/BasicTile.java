package tiles;

import gfx.Screen;
import levels.Level;

public class BasicTile extends Tile {
	 
    protected int tileId;
    protected int tileColor;

    public BasicTile(int id, int x, int y, int tileColor, int levelColor, boolean animatedTile) {
            super(id, false, false, levelColor);
            this.tileId = x + y * 32;
            this.tileColor = tileColor;
            this.animatedTile = false;
    }

    public void render(Screen screen, Level level, int x, int y) {
            screen.render(x, y, tileId, tileColor, 0x00, 1);
    }
    
    public void tick() {
    	
    }

    
	public boolean isAnimatedTile() {
		return animatedTile;
	}
}