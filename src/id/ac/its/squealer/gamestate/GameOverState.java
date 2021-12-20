package id.ac.its.squealer.gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;
import id.ac.its.squealer.audio.AudioPlayer;
import id.ac.its.squealer.tilemap.Background;


public class GameOverState extends GameState{
	private Background bg;
	private BufferedImage gameTitle;
	private AudioPlayer bgMusic;
	private HashMap<String, AudioPlayer> sfx;
	
	private int currentChoice = 0;
	private String[] options = {
		"RESTART",
		"QUIT"
	};
	
	private Font font;
	
	public GameOverState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{
			gameTitle = ImageIO.read(getClass().getResourceAsStream("/HUD/GameOverScreenV2.png"));
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
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
		bgMusic = new AudioPlayer("/SFX/gameOver.mp3");
		bgMusic.bgplay();
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
         g.drawImage(gameTitle, 0, 30,
                 gameTitle.getWidth(null) / 4, gameTitle.getHeight(null) / 4, null);

        
      
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) 
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.LIGHT_GRAY);
			}
			g.drawString(options[i], 110, 190 + i * 15);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			if(gsm.currentLevel == 1)
				gsm.setState(GameStateManager.LEVEL1STATE);
			else if(gsm.currentLevel == 2)
				gsm.setState(GameStateManager.LEVEL2STATE);
			else if(gsm.currentLevel == 3)
				gsm.setState(GameStateManager.LEVEL3STATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			bgMusic.close();
			sfx.get("press").play();
			select();
		}
		if(k == KeyEvent.VK_UP) {
			sfx.get("updown").play();
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			sfx.get("updown").play();
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int k) {}
}
