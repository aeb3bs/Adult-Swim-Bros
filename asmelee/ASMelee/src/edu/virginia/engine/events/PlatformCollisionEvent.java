package edu.virginia.engine.events;

import edu.virginia.engine.display.Platform;

public class PlatformCollisionEvent extends Event {
	public static final String COLLISION = "Collision";
	public Platform platform;
	public PlatformCollisionEvent()
	{
		this.setEventType(COLLISION);
	}
	public void setPlatform(Platform p)
	{
		this.platform = p;
	}
}
