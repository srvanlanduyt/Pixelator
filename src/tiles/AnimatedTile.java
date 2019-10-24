package tiles;

public class AnimatedTile extends BasicTile {
	
	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;
	private boolean animatedTile;

	public AnimatedTile(int id, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay, boolean animatedTile) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, levelColor, animatedTile);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
		this.animatedTile = true;
	}
	
	public void tick() {
		if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
		}
	}
	
	public boolean isAnimatedTile() {
		return animatedTile;
	}
	

}