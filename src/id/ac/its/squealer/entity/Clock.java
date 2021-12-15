package id.ac.its.squealer.entity;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Clock {
	Timer clock;
	private BufferedImage image;
	private int second, minute;
	private Font font;
	private StringBuilder string;
	
	public Clock() {
		second = minute = 0;
		clock = new Timer(1000, new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					second++;
					if(second == 60) {
						second = 0;
						minute++;
					}
				}
			}
		);
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(
					"/Clock/clock.png"
				)
			);
			
			font = new Font("Arial", Font.PLAIN, 14);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		string = new StringBuilder();
		g.setFont(font);
		g.setColor(Color.WHITE);
		if(minute < 10)
			string.append("0" + minute);
		else
			string.append(minute);
		
		string.append(":");
		
		if(second < 10)
			string.append("0" + second);
		else
			string.append(second);
		
		g.drawString(string.toString(), 280, 25);
		g.drawImage(image, 260, 12,
                image.getWidth(null) / 14, image.getHeight(null) / 14, null);
	}
	
	public void start() {
		clock.start();
	}
	
	public void stop() {
		clock.stop();
	}
}
