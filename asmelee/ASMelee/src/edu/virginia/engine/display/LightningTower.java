package edu.virginia.engine.display;

public class LightningTower extends Sprite {
	public Trooper owner;
	public int damage;
	public int knockback;
	public int ticks;
	public LightningTower(String id, boolean onlineSprite) {
		super(id, "lightning_tower.png", onlineSprite);
		// TODO Auto-generated constructor stub
		damage = 3;
		knockback = 10;
		ticks = 3;
	}
}
