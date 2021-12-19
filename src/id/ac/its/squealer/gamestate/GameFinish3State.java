package id.ac.its.squealer.gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import id.ac.its.squealer.audio.AudioPlayer;
import id.ac.its.squealer.tilemap.Background;

public class GameFinish3State extends GameState {
	private Background bg;
	private BufferedImage gameTitle, newHighScoreBadge;
	private HashMap<String, AudioPlayer> sfx;
	private boolean highScoreFilePresent = true;
	
	private int currentChoice = 0;
	private String[] options = {
		"Level Select",
		"Quit"
	};
	private String time, highScore;
	
	private Font font;
	
	public GameFinish3State(GameStateManager gsm) {
		this.gsm = gsm;
        
		try 
		{
			gameTitle = ImageIO.read(getClass().getResourceAsStream("/HUD/GameFinishScreen.png"));
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			font = new Font("Arial", Font.PLAIN, 12);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("press", new AudioPlayer("/SFX/menuPressed.mp3"));
		sfx.put("updown", new AudioPlayer("/SFX/jump.mp3"));
		init();
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

        
  
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) 
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.LIGHT_GRAY);
			}
			g.drawString(options[i], 120, 190 + i * 15);
		}
		
		// draw time
		getTime();
		getHighScore();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Your time is:", 120, 120);
		g.drawString(time, 140, 135);
		if(scoreComp() || !highScoreFilePresent) {
			highScoreFilePresent = true;
			highScoreSet();
			try {
				newHighScoreBadge = ImageIO.read(getClass().getResourceAsStream("/Highscore/NewHighScore.png"));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			g.drawImage(newHighScoreBadge, 100, 150, newHighScoreBadge.getWidth(null) / 4, newHighScoreBadge.getHeight(null) / 4, null);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.MENUSTATE);
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
	
	private void getTime() {
		if(!new File("Resource/Highscore/Time.dat").exists())
			storeFileInit("Resource/Highscore/Time.dat");
		try {
			ObjectInputStream infile = new ObjectInputStream(new FileInputStream("Resource/Highscore/Time.dat"));
			this.time = infile.readObject().toString();
			infile.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void getHighScore() {
		if(!new File("Resource/Highscore/Level3.dat").exists()) {
			storeFileInit("Resource/Highscore/Level3.dat");
			highScoreFilePresent = false;
		}
		try {
			ObjectInputStream infile = new ObjectInputStream(new FileInputStream("Resource/Highscore/Level3.dat"));
			this.highScore = infile.readObject().toString();
			infile.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	boolean scoreComp() {
		for(int i = 0; i < time.length(); i++) {
			if(time.charAt(i) == highScore.charAt(i)) continue;
			if(time.charAt(i) < highScore.charAt(i)) return true;
			else return false;
		}
		return false;
	}
	
	private void highScoreSet() {
		try {
			ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream("Resource/Highscore/Level3.dat"));
			outfile.flush();
			outfile.writeChars(time);
			outfile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void storeFileInit(String filename) {
		try {
			ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream(filename));
			outfile.flush();
			outfile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void keyReleased(int k) {}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
