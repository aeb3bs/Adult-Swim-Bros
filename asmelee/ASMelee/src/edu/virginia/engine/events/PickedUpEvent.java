package edu.virginia.engine.events;

public class PickedUpEvent extends Event {
	public static final String COIN_PICKED_UP = "PickedUpString";
	public PickedUpEvent()
	{
		this.setEventType(COIN_PICKED_UP);
	}
}
