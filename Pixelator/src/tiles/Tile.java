package tiles;

import java.util.Random;

import gameStates.GameStateManager;
import gfx.Colors;
import gfx.Screen;
import levels.Level;

public abstract class Tile {
	
	static Random r = new Random();
	
    private static int randColor1 = -1;
    private static int randColor2 = randomTileColor(r);
    private static int randColor3 = randomTileColor(r);
    private static int randColor4 = randomTileColor(r);
	
    public static Tile[] tiles = new Tile[256];
    public static Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(randColor1, randColor2, randColor3, randColor4), 0xFF000000, false);
    public static Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(-1, randColor2, randColor3, randColor4), 0xFF555555, false);
    public static Tile GRASS = new BasicTile(2, 2, 0, Colors.get(randColor1, randColor2, randColor3, randColor4), 0xFF00FF00, false);
    public static Tile ANIMATEDGRASS = new AnimatedTile(3, new int[][] {{0, 4}, {1, 4}, {2, 4}, {1, 4}}, Colors.get(255, 53, 7, 199), 0xFF000000, 100, true);

    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColor;
    protected boolean animatedTile;
    

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
            this.id = (byte) id;
//            if (tiles[id] != null) {
//                    throw new RuntimeException("Duplicate tile id on" + id);
                    
 //           }
            this.solid = isSolid;
            this.emitter = isEmitter;
            this.levelColor = levelColor;
            tiles[id] = this;
    }

	public static int randomTileColor(Random r) {
		int low = 50;
		int high = 1900;
		if (r == null) {
			System.out.println("r is null");
			System.exit(0);
		}
		return r.nextInt(high - low) + low;
	}
    
    public byte getId() {
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public static void levelChangeColor() {
    	randColor1 = -1;
    	randColor2 = randomTileColor(r);
    	randColor3 = randomTileColor(r);
    	randColor4 = randomTileColor(r);
    	
    	
    	tiles[0] = new BasicSolidTile(0, 0, 0, Colors.get(randColor1, randColor2, randColor3, randColor4), 0xFF000000, false);
//  	System.out.println("VOID Colors: " + randColor1 + " " + randColor2 + " " + randColor3 + " " + randColor4);
        
    	randColor1 = -1;
    	randColor2 = randomTileColor(r);
    	randColor3 = randomTileColor(r);
    	randColor4 = randomTileColor(r);
    	
    	tiles[1] = new BasicSolidTile(1, 1, 0, Colors.get(-1, randColor2, randColor3, randColor4), 0xFF555555, false);
//    	System.out.println("STONE Colors: " + randColor1 + " " + randColor2 + " " + randColor3 + " " + randColor4);

    	randColor1 = -1;
    	randColor2 = randomTileColor(r);
    	randColor3 = randomTileColor(r);
    	randColor4 = randomTileColor(r);
    	
        tiles[2] = new BasicTile(2, 2, 0, Colors.get(randColor1, randColor2, randColor3, randColor4), 0xFF00FF00, false);
//    	System.out.println("GRASS Colors: " + randColor1 + " " + randColor2 + " " + randColor3 + " " + randColor4);
        
    	randColor1 = -1;
    	randColor2 = randomTileColor(r);
    	randColor3 = randomTileColor(r);
    	randColor4 = randomTileColor(r);
    	
        tiles[3] = new AnimatedTile(3, new int[][] {{0, 4}, {1, 4}, {2, 4}, {1, 4}}, Colors.get(255, randColor2, randColor3, randColor4), 0xFF000000, 100, true);
//      System.out.println("ANIMATED_GRASS Colors: " + randColor1 + " " + randColor2 + " " + randColor3 + " " + randColor4);
    }
    
    public boolean isEmitter() {
        return emitter;
    }

    public int getLevelColor() {
    	return levelColor;
    }
    
    public abstract void tick();
    
	public int randomColorInt(Random r) {
		int low = 1;
		int high = 254;
		if (r == null) {
			System.out.println("r is null");
			System.exit(0);
		}
		return r.nextInt(high - low) + low;
	}
    
    public abstract void render(Screen screen, Level level, int x, int y);

	public abstract boolean isAnimatedTile();
}

