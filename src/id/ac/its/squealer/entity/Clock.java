package id.ac.its.squealer.entity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Clock {
	Timer clock;
	private int second, minute;
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
	}
	
	public void draw(Graphics2D g) {
		string = new StringBuilder();
		g.setFont(new Font("Arial", Font.PLAIN, 14));
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
	}
	
	public void start() {
		clock.start();
	}
}
