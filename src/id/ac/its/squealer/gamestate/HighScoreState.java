package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.*;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import id.ac.its.squealer.audio.AudioPlayer;
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
	private String[] scores;
	
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
		
		// read stored high score
		//getHighScore();  there is a bug here
		
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
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			sfx.play();
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
			bgMusic.close();
		}
	}
	
	public void keyReleased(int k) {}
	
	private void getHighScore() {
		scores = new String[3];
		try 
		{
			ObjectInputStream level1File=new ObjectInputStream(new FileInputStream("/Resource/Highscore/Level1.txt"));
			ObjectInputStream level2File=new ObjectInputStream(new FileInputStream("/Resource/Highscore/Level2.txt"));
			ObjectInputStream level3File=new ObjectInputStream(new FileInputStream("/Resource/Highscore/Level3.txt"));
			
			scores[0] = level1File.readObject().toString();
			scores[1] = level2File.readObject().toString();
			scores[2] = level3File.readObject().toString();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(scores);
	}
	
}
