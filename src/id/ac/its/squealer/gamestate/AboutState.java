package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import id.ac.its.squealer.tilemap.Background;

public class AboutState extends GameState {
	
	private Background bg;
	private Font font;
	private String[] about = {
			"ABOUT SQUEALER",
			" ",
			"SQUEALER is a Platformers game",
			"built for an OOP Final Project",
			" ",
			"by",
			"Hemakesha Ramadhani Heriqbaldi - 5025201209",
			"Naufal Faadhilah - 5025201221",
			"Made Rianja Richo Dainino - 5025201236",
			"Andhika Ditya Bagaskara D. - 5025201096",
			"BACK"
	};
	
	public AboutState(GameStateManager gsm) {
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
				g.drawString(about[i], 15, 15 + i * 15);
			}
			else {
				g.setColor(Color.RED);
				g.drawString(about[i], 145, 200);
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
