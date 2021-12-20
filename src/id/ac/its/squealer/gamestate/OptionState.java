package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import id.ac.its.squealer.audio.AudioPlayer;
import id.ac.its.squealer.tilemap.Background;

public class OptionState extends GameState {
	
	private Background bg;
	private Font font, font1;
	private AudioPlayer bgMusic;
	private HashMap<String, AudioPlayer> sfx;
	private int currentChoice = 1;
	
	private String[] about = {
			"VOLUME",
			"VOLUME UP",
			"VOLUME DOWN",
			"MUTE",
			"BACK"
	};
	
	public OptionState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{	
			bg = new Background("/Backgrounds/aboutbg.jpg", 1);
			font1 = new Font("Arial", Font.BOLD, 20);
			font = new Font("Arial", Font.PLAIN, 12);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("press", new AudioPlayer("/SFX/menuPressed.mp3"));
		sfx.put("updown", new AudioPlayer("/SFX/upDown.mp3"));
		init();
	}
	
	public void init() {
		bgMusic = new AudioPlayer("/Music/about.mp3");
		bgMusic.bgplay();
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
		g.drawString(about[0], 100, 30);
		
		g.setFont(font);
		for(int i = 1; i < about.length; i++) {
			if(i == currentChoice) 
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(about[i], 100, 40 + i * 40);
		}
	}
	
	private void select() {
		if(currentChoice == 1) {
			bgMusic.volumeUp();
		}
		if(currentChoice == 2) {
			bgMusic.volumeDown();
		}
		if(currentChoice == 3) {
			bgMusic.volumeMute();
		}
		if(currentChoice == 4) {
			gsm.setState(GameStateManager.MENUSTATE);
			bgMusic.close();
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			sfx.get("press").play();
			select();
		}
		if(k == KeyEvent.VK_UP) {
			sfx.get("updown").play();
			currentChoice--;
			if(currentChoice == 0) {
				currentChoice = about.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			sfx.get("updown").play();
			currentChoice++;
			if(currentChoice == about.length) {
				currentChoice = 1;
			}
		}
	}
	
	public void keyReleased(int k) {}
		
}
