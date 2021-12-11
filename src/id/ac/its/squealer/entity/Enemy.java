package id.ac.its.squealer.entity;

import id.ac.its.squealer.tilemap.TileMap;

public class Enemy extends MapObject{
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
	
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm)
	{
		super(tm);
		remove = false;
	}
	
	
	public boolean isDead() {
		return dead;
	}
	public int getDamage() {
		return damage;
	}
	public boolean shouldRemove(){
		return remove;
	}
	
	
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		
		flinching = true;
		flinchTimer=System.nanoTime();
		
	}
	
	public void update() {
		
	}
}
