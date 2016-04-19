package edu.virginia.engine.events;

import edu.virginia.engine.display.Character;
import edu.virginia.engine.display.Laser;
import edu.virginia.engine.display.LightningTower;
import edu.virginia.engine.display.RangedAttack;
import edu.virginia.engine.display.Sprite;

public class SpecialTrooperCollisionEvent extends Event{
	public static final String SPECIALTROOPER = "Lightning Tower";
	public LightningTower target;
	public SpecialTrooperCollisionEvent()
	{
		this.setEventType(SPECIALTROOPER);
	}
	public void setDamager(Sprite sprite)
	{
		target = (LightningTower) sprite;
	}
}
