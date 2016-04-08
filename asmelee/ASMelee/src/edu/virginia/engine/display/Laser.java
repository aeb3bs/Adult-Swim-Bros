package edu.virginia.engine.display;

public class Laser extends Sprite {
	public Stewie owner;
	public int damage;
	public int knockback;
	public int ticks;
	public Laser(String id, String imageFileName, boolean onlineSprite) {
		super(id, imageFileName, onlineSprite);
		// TODO Auto-generated constructor stub
		damage = 1;
		knockback = 15;
		ticks = 5;
	}
}
