package edu.virginia.engine.events;

import edu.virginia.engine.display.Character;
import edu.virginia.engine.display.RangedAttack;

public class RangedCollisionEvent extends Event{
	public static final String RANGED = "Ranged";
	public RangedAttack rangedAttack;
	public RangedCollisionEvent()
	{
		this.setEventType(RANGED);
	}
	public void setRangedAttack(RangedAttack c)
	{
		this.rangedAttack = c;
	}
}
