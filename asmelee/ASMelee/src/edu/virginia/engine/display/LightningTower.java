package edu.virginia.engine.display;

public class LightningTower extends Sprite {
	public Character owner;
	public int damage;
	public int knockback;
	public int ticks;
	public LightningTower(String id, boolean onlineSprite, Character owner) {
		super(id, onlineSprite);
		damage = 3;
		knockback = 10;
		ticks = 3;
		if(owner instanceof Trooper) {
			this.setImage("lightning_tower.png");
			damage = 3;
			knockback = 50;
		}
		if(owner instanceof Pikachu) {
			this.setImage("pikachu_lightningtower.png");
			damage = 6;
			knockback = 30;
		}
		// TODO Auto-generated constructor stub
		
	}
}
