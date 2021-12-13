package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import id.ac.its.squealer.tilemap.Background;

public class OptionState extends GameState {
	
	private Background bg;
	private Font font;
	private String[] about = {
			"VOLUME",
			"BACK"
	};
	
	public OptionState(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{	
			bg = new Background("/Backgrounds/aboutbg.jpg", 1);
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
		
		g.setFont(font);
		for(int i = 0; i < about.length; i++) {	
			if(i != about.length-1) {
				g.setColor(Color.WHITE);
				g.drawString(about[i], 126, 100 + i * 15);
			}
			else {
				g.setColor(Color.RED);
				g.drawString(about[i], 135, 200);
			}
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void keyReleased(int k) {}
		
}
