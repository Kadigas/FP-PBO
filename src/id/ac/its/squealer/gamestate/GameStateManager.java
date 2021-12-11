package id.ac.its.squealer.gamestate;

//import java.util.ArrayList;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int LEVELSELECTSTATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int ABOUTSTATE = 3;
	public static final int LEVEL1STATE = 4;
	public static final int LEVEL2STATE = 5;
	public static final int LEVEL3STATE = 6;
	public static final int NUMGAMESTATES = 7;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;

		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == OPTIONSTATE)
			gameStates[state] = new OptionState(this);
		if(state == LEVELSELECTSTATE)
			gameStates[state] = new LevelSelectState(this);
		if(state == ABOUTSTATE)
			gameStates[state] = new AboutState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} 
		catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}
