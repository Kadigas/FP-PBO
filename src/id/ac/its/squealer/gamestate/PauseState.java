package id.ac.its.squealer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PauseState {
	private BufferedImage pauseScreen;
	private String[] notification = {
			"Paused",
			"Press ESC to resume",
			"or",
			"Press ENTER to back to level selection"
	};
	private Font font1, font2;
	
	public PauseState() {
		try {
			pauseScreen = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/pause.png"));
			font1 = new Font("Arial", Font.PLAIN, 36);
			font2 = new Font("Arial", Font.PLAIN, 11);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawPause(Graphics2D g) {
		g.drawImage(pauseScreen, 0, 0,
                pauseScreen.getWidth(null), pauseScreen.getHeight(null), null);
		g.setFont(font1);
		g.setColor(Color.WHITE);
		g.drawString(notification[0], 100, 110);
		g.setFont(font2);
		for(int i = 1; i < notification.length; i++) {
			if(i == 2)
				g.drawString(notification[i], 155, 140 + (i-1) * 15);
			else
				g.drawString(notification[i], 108 - (i-1) * 20, 140 + (i-1) * 15);
		}
	}
}
