package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import id.ac.its.squealer.audio.AudioPlayer;
import id.ac.its.squealer.tilemap.Background;

public class LevelSelectState extends GameState {
	
	private Background bg;
	private Font font;
	private int currentChoice = 0;
	private AudioPlayer bgMusic;
	private HashMap<String, AudioPlayer> sfx;
	
	private String[] level = {
			"1",
			"2",
			"3",
			"BACK"
	};
	
	public LevelSelectState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{	
			bg = new Background("/Backgrounds/aboutbg.jpg", 1);
			font = new Font("Arial", Font.PLAIN, 24);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("press", new AudioPlayer("/SFX/menuPressed.mp3"));
		sfx.put("updown", new AudioPlayer("/SFX/jump.mp3"));
		init();
	}
	
	public void init() {
		bgMusic = new AudioPlayer("/Music/select.mp3");
		bgMusic.play();
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		
		g.setFont(font);
		for(int i = 0; i < level.length; i++) {
			if(i == currentChoice) 
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.LIGHT_GRAY);
			}
			
			if(i < level.length-1)
				g.drawString(level[i], 145, 50 + i * 70);
			else
				g.drawString(level[i], 250, 230);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		if(currentChoice == 2) {
			gsm.setState(GameStateManager.LEVEL3STATE);
		}
		if(currentChoice == 3) {
			System.exit(0);
		}
		bgMusic.close();
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			sfx.get("press").play();
			select();
		}
		if(k == KeyEvent.VK_UP) {
			sfx.get("updown").play();
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = level.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			sfx.get("updown").play();
			currentChoice++;
			if(currentChoice == level.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int k) {}
		
}
