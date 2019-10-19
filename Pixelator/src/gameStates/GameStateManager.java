package gameStates;

import java.util.ArrayList;

import entity.Player;
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
import main.InputHandler;

public class GameStateManager {

	public static ArrayList<State> gameStates;
	private int currentState;
	private InputHandler input;
	public Player player;
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int LEVEL2STATE = 2;
	public static final int LEVEL3STATE = 3;
	public static final int LEVEL4STATE = 4;
	public static final int LEVEL5STATE = 5;
	public static final int LEVEL6STATE = 6;
	public static final int LEVEL7STATE = 7;
	public static final int LEVEL8STATE = 8;
	public static final int LEVEL9STATE = 9;
	public static final int LEVEL10STATE = 10;
	public static final int INFOSTATE = 11;
	
	public GameStateManager() {
		gameStates = new ArrayList<State>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new Level1State(this));
		gameStates.add(new Level2State(this));
		gameStates.add(new Level3State(this));
		gameStates.add(new Level4State(this));
		gameStates.add(new Level5State(this));
		gameStates.add(new Level6State(this));
		gameStates.add(new Level7State(this));
		gameStates.add(new Level8State(this));
		gameStates.add(new Level9State(this));
		gameStates.add(new Level10State(this));
		gameStates.add(new InfoState(this));
	}
	
	public void setState(int state, Player player) {
		currentState = state;
		gameStates.get(currentState).init(player);
	}
	
	public void update() {
		gameStates.get(currentState).update(Game.input);
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}

	public int getState() {
		return currentState;
	}
	
	public int getGameStateCount() {
		return gameStates.size();
	}
}