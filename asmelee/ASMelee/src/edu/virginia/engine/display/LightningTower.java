package edu.virginia.engine.display;

public class LightningTower extends Sprite {
	public Character owner;
	public int damage;
	public int knockback;
	public int ticks;
	public LightningTower(String id, boolean onlineSprite, Character owner) {
		super(id, onlineSprite);
		if(owner instanceof Trooper)
			this.setImage("lightning_tower.png");
		if(owner instanceof Pikachu)
			this.setImage("pikachu_lightningtower.png");
		// TODO Auto-generated constructor stub
		damage = 3;
		knockback = 10;
		ticks = 3;
	}
}
