package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import id.ac.its.squealer.audio.AudioPlayer;
import id.ac.its.squealer.tilemap.Background;

public class LevelSelectState extends GameState {
	
	private Background bg;
	private Font font;
	private int currentChoice = 0;
	private AudioPlayer bgMusic;
	private HashMap<String, AudioPlayer> sfx;
	private BufferedImage level0Image;
	private BufferedImage level1Image;
	private BufferedImage level2Image;
	private BufferedImage level3Image;
	
	private String[] level = {
			"1",
			"2",
			"3",
			"HIGH SCORE",
			"BACK"
	};
	
	public LevelSelectState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{	
			bg = new Background("/Backgrounds/bgLVSelect.png", 1);
			
			level0Image = ImageIO.read(getClass().getResourceAsStream("/HUD/levelUnselect.png"));
			level1Image = ImageIO.read(getClass().getResourceAsStream("/HUD/level1Select.png"));
			level2Image = ImageIO.read(getClass().getResourceAsStream("/HUD/level2Select.png"));
			level3Image = ImageIO.read(getClass().getResourceAsStream("/HUD/level3Select.png"));
			
			font = new Font("Arial", Font.BOLD, 12);
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
		bgMusic.bgplay();
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		if(currentChoice == 0)
			g.drawImage(level1Image, 0, 0,
	                 level1Image.getWidth(null), level1Image.getHeight(null), null);
		if(currentChoice == 1)
			g.drawImage(level2Image, 0, 0,
	                 level2Image.getWidth(null), level1Image.getHeight(null), null);
		if(currentChoice == 2)
			g.drawImage(level3Image, 0, 0,
	                 level3Image.getWidth(null), level1Image.getHeight(null), null);
		if(currentChoice > 2)
			g.drawImage(level0Image, 0, 0,
	                 level0Image.getWidth(null), level1Image.getHeight(null), null);
		
		g.setFont(font);
		for(int i = 0; i < level.length; i++) {
			if(i == currentChoice) 
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			
			if(i == level.length-2)
				g.drawString(level[i], 30, 230);
			if(i == level.length-1)
				g.drawString(level[i], 250, 230);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			sfx.get("press").play();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			sfx.get("press").play();
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		if(currentChoice == 2) {
			sfx.get("press").play();
			gsm.setState(GameStateManager.LEVEL3STATE);
		}
		if(currentChoice == 3) {
			sfx.get("press").play();
			gsm.setState(GameStateManager.HIGHSCORESTATE);
		}
		if(currentChoice == 4) {
			sfx.get("press").play();
			gsm.setState(GameStateManager.MENUSTATE);
		}
		bgMusic.close();
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			sfx.get("press").play();
			select();
		}
		if(k == KeyEvent.VK_LEFT) {
			sfx.get("updown").play();
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = level.length - 1;
			}
		}
		if(k == KeyEvent.VK_RIGHT) {
			sfx.get("updown").play();
			currentChoice++;
			if(currentChoice == level.length) {
				currentChoice = 0;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			sfx.get("updown").play();
			if(currentChoice == 0 || currentChoice == 1) {
				currentChoice = 3;
			}
			else
				currentChoice = 4;
		}
		if(k == KeyEvent.VK_UP) {
			sfx.get("updown").play();
			if(currentChoice == 3) {
				currentChoice = 0;
			}
			else
				currentChoice = 2;
		}
		draw(null);
	}
	
	public void keyReleased(int k) {}
}
