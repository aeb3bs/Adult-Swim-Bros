package edu.virginia.engine.events;

import edu.virginia.engine.tweening.Tween;

public class TweenEvent extends Event {
	public static final String TWEEN_COMPLETE_EVENT = "TweenComplete";
	Tween tween;
	public TweenEvent(Tween tween)
	{
		this.setEventType(TWEEN_COMPLETE_EVENT);
		this.tween = tween;
	}
}
