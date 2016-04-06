package edu.virginia.engine.events;



public class SpecialStewieCollisionManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if (event instanceof SpecialStewieCollisionEvent){
			System.out.println("StewieSpecial");
		}
	}
}