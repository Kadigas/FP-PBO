package id.ac.its.squealer.gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import tilemap.Background;

public class MenuState extends GameState{

	private Background bg;
	
	private BufferedImage gameTitle;
	
	private int currentChoice = 0;
	private String[] options = {
		"Play",
		"Option",
		"About",
		"Quit"
	};
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{
			gameTitle = ImageIO.read(getClass().getResourceAsStream("/HUD/gameTitle.png"));
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			font = new Font("Arial", Font.PLAIN, 12);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {}
	
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
			g.drawString(options[i], 145, 160 + i * 15);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			// option
		}
		if(currentChoice == 2) {
			gsm.setState(GameStateManager.ABOUTSTATE);
		}
		if(currentChoice == 3) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int k) {}
		
}
