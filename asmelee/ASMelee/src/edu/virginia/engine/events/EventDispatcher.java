package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher implements IEventDispatcher {
	HashMap<String, ArrayList<IEventListener>>observers;
	public EventDispatcher()
	{
		observers = new HashMap<String, ArrayList<IEventListener>>();
	}
	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if(observers.get(eventType)==null)
		{
			observers.put(eventType, new ArrayList<IEventListener>());
		}
		observers.get(eventType).add(listener);
	}
	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		if(observers.get(eventType)!=null)
			observers.get(eventType).remove(listener);
	}
	@Override
	public void dispatchEvent(Event event) {
		String eventType = event.getEventType();
		if(observers.get(eventType)==null)
			return;
		ArrayList<IEventListener>listeners = observers.get(eventType);
		event.setSource(this);
		for(IEventListener e:listeners)
		{
			e.handleEvent(event);
		}
	}
	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		return observers.get(eventType).contains(listener);
	}
}
