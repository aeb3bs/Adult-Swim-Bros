package edu.virginia.engine.events;

import edu.virginia.engine.display.Character;
import edu.virginia.engine.display.Laser;
import edu.virginia.engine.display.Sprite;


public class SpecialStewieCollisionEvent extends Event{
	public static final String SPECIALSTEWIE = "SpecialStewie";
	public Laser target;
	public SpecialStewieCollisionEvent()
	{
		this.setEventType(SPECIALSTEWIE);
	}
	public void setDamager(Sprite sprite)
	{
		target = (Laser) sprite;
	}

}
