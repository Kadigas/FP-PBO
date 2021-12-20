package id.ac.its.squealer.entity;

public class PlayerSave {
	private static int health = 5;
	private static long time = 0;
	
	public static void init() {
		health = 5;
		time = 0;
	}
	
	public static int getHealth() { return health; }
	public static void setHealth(int i) { health = i; }
	
	public static long getTime() { return time; }
	public static void setTime(long t) { time = t; }
}
