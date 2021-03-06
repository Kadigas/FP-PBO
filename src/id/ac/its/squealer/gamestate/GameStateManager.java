package id.ac.its.squealer.gamestate;

//import java.util.ArrayList;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	public int currentLevel = 0;
	
	public static final int MENUSTATE = 0;
	public static final int LEVELSELECTSTATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int ABOUTSTATE = 3;
	public static final int LEVEL1STATE = 4;
	public static final int LEVEL2STATE = 5;
	public static final int LEVEL3STATE = 6;
	public static final int HIGHSCORESTATE = 7;
	public static final int GAMEOVERSTATE = 8;
	public static final int GAMEFINISHSTATE = 9;
	public static final int GAMEFINISH2STATE = 10;
	public static final int GAMEFINISH3STATE = 11;
	public static final int NUMGAMESTATES = 12;
	
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
		if(state == LEVEL1STATE) {
			currentLevel = 1;
			gameStates[state] = new Level1State(this);
		}
		if(state == LEVEL2STATE) {
			currentLevel = 2;
			gameStates[state] = new Level2State(this);
		}
		if(state == LEVEL3STATE) {
			currentLevel = 3;
			gameStates[state] = new Level3State(this);
		}
		if(state == HIGHSCORESTATE)
			gameStates[state] = new HighScoreState(this);
		if(state == GAMEOVERSTATE)
			gameStates[state] = new GameOverState(this);
		if(state == GAMEFINISHSTATE)
			gameStates[state] = new GameFinishState(this);
		if(state == GAMEFINISH2STATE)
			gameStates[state] = new GameFinish2State(this);
		if(state == GAMEFINISH3STATE)
			gameStates[state] = new GameFinish3State(this);
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
