package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.*;

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
		scores = new String[3];
		scores[0] = getHighScore("Resource/Highscore/Level1.dat");
		scores[1] = getHighScore("Resource/Highscore/Level2.dat");
		scores[2] = getHighScore("Resource/Highscore/Level3.dat");
		
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
		g.setColor(Color.WHITE);
		for(int i = 0; i < scores.length; i++) {
			if(scores[i] != null)
				g.drawString(scores[i], 15, 50 + i * 60);
			else 
				g.drawString("-", 15, 80 + i * 60);
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
	
	private String getHighScore(String filename) {
		String temp;
		if(!new File(filename).exists())
			storeFileInit(filename);
		try {
			System.out.println("test");
			ObjectInputStream infile = new ObjectInputStream(new FileInputStream(filename));
			temp = infile.readObject().toString();
			System.out.println(temp);
			infile.close();
			return temp;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void storeFileInit(String filename) {
		try {
			ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream(filename));
			outfile.flush();
			outfile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
