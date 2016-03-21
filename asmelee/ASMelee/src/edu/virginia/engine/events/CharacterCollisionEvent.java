package edu.virginia.engine.events;

import edu.virginia.engine.display.Platform;
import edu.virginia.engine.display.Character;

public class CharacterCollisionEvent extends Event {
	public static final String MELEE = "Melee";
	public Character character;
	public CharacterCollisionEvent()
	{
		this.setEventType(MELEE);
	}
	public void setCharacter(Character c)
	{
		this.character = c;
	}
}
