package edu.virginia.engine.events;

import edu.virginia.engine.display.Character;


public class SpecialStewieCollisionEvent extends Event{
	public static final String SPECIALSTEWIE = "SpecialStewie";

	public SpecialStewieCollisionEvent()
	{
		this.setEventType(SPECIALSTEWIE);
	}

}
