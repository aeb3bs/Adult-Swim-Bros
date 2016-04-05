package edu.virginia.engine.events;



public class CharacterDeathEvent extends Event {
	public static final String DEATH = "Death";
	public int numCharactersLeft;

	public CharacterDeathEvent(int numCharsLeft)
	{
		this.numCharactersLeft = numCharsLeft;
		this.setEventType(DEATH);
	}

}