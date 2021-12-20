package id.ac.its.squealer.gamestate;

import id.ac.its.squealer.tilemap.*;
import id.ac.its.squealer.entity.*;
import id.ac.its.squealer.entity.enemies.*;
import id.ac.its.squealer.main.GamePanel;
import id.ac.its.squealer.audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Level2State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private PauseState pauseState;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	private Clock clock;
	
	private AudioPlayer bgMusic, sfx;
	
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	private boolean eventPortal;
	private boolean flash;
	private boolean eventBossDead; 
	
	private static boolean pause = false;
	
	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/level2tileset.gif");
		tileMap.loadMap("/Maps/level2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/bgLevel2.png", 0.1);
		
		pauseState = new PauseState();
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		clock = new Clock();
		clock.start();
		
		bgMusic = new AudioPlayer("/Music/level2.mp3");
		bgMusic.bgplay();
		sfx = new AudioPlayer("/SFX/menuPressed.mp3");
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		FarmerMelee s;
		Point[] points = new Point[] {
			new Point(700, 100),
			new Point(2800, 300)
		};
		for(int i = 0; i < points.length; i++) {
			s = new FarmerMelee(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}
	
	public void update() {
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		
		//When player loses all health or drop below maximum height (e.g. to a hole)
		if(player.getHealth() == 0 || player.gety() > 220) {
			eventDead = blockInput = true;
		}
		if(eventDead) eventDead();
		
		//When player Pass the x coordinate of the level area, the Player will win
		if(player.getx() > 3100) {
			eventFinish = blockInput = true;
		}
		if(eventFinish) eventFinish();
				
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				clock.reduceTime(2);
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
		// draw clock
		clock.draw(g);
		
		if(pause)
			pauseState.drawPause(g);
				
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
		if(k == KeyEvent.VK_ESCAPE) {
			sfx.play();
			if(!pause) {
				pause = true;
				clock.stop();
			}
			else {
				pause = false;
				clock.start();
			}
		}
		if(k == KeyEvent.VK_ENTER && pause) {
			pause = false;
			sfx.play();
			bgMusic.close();
			gsm.setState(GameStateManager.LEVELSELECTSTATE);
		}
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
	
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			bgMusic.close();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getHealth() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
			}
		}
	}
	
	
	
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
			clock.stop();
			gsm.setState(GameStateManager.GAMEFINISH2STATE);
			bgMusic.close();
		}
	}
}



