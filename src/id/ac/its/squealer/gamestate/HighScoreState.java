package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


import id.ac.its.squealer.audio.AudioPlayer;
import id.ac.its.squealer.entity.HighScore;
import id.ac.its.squealer.tilemap.Background;

public class HighScoreState extends GameState {
	
	private Background bg;
	private Font font1, font2;
	private AudioPlayer bgMusic, sfx;
	private String[] about = {
			"HIGH SCORE",
			"LEVEL 1",
			"LEVEL 2",
			"LEVEL 3",
			"BACK"
	};
	private String[] scores = new String[3];
	private StringBuilder temp;
	private HighScore highScore1, highScore2, highScore3;
	private int[] scoreList = new int[3];
	
	public HighScoreState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{	
			bg = new Background("/Backgrounds/aboutbg.jpg", 1);
			font1 = new Font("Arial", Font.BOLD, 12);
			font2 = new Font("Arial", Font.PLAIN, 12);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		sfx = new AudioPlayer("/SFX/menuPressed.mp3");
		init();
	}
	
	public void init() {
		bgMusic = new AudioPlayer("/Music/highscore.mp3");
		bgMusic.play();
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setFont(font1);
		g.setColor(Color.WHITE);
		g.drawString(about[0], 125, 20);
		g.setFont(font2);
		for(int i = 1; i < about.length; i++) {	
			if(i != about.length-1) {
				g.setColor(Color.WHITE);
				g.drawString(about[i], 15, -10 + i * 60);
			}
			else {
				g.setColor(Color.RED);
				g.drawString(about[i], 145, 220);
			}
		}
		
		// read and draw stored high score
		highScore1 = new HighScore(1);
		highScore2 = new HighScore(2);
		highScore3 = new HighScore(3);
		
		scoreList[0] = highScore1.getHighScore();
		highScore1.highScoreSet(scoreList[0]);
		scoreList[1] = highScore2.getHighScore();
		highScore2.highScoreSet(scoreList[1]);
		scoreList[2] = highScore3.getHighScore();
		highScore3.highScoreSet(scoreList[2]);
		
		g.setColor(Color.WHITE);
		for(int i = 0; i < scores.length; i++) {
			scores[i] = convertToString(scoreList[i]);
			g.drawString(scores[i], 15, 80 + i * 60);
		}
	}
	
	private String convertToString(int input) {
		temp = new StringBuilder();
		int score = input;
		
		if(score == -100) {
			temp.append("-");
			return temp.toString();
		}
		
		if(score < 60) temp.append("00:");
		else if(score < 600) temp.append("0" + (score / 60) + ":");
		else temp.append(score / 60);
		
		if(score % 60 == 0) temp.append("00");
		else if(score % 60 < 10) temp.append("0" + (score % 60));
		else temp.append(score % 60);
		
		return temp.toString();
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			sfx.play();
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
			bgMusic.close();
		}
	}
	
	public void keyReleased(int k) {}
	
}
