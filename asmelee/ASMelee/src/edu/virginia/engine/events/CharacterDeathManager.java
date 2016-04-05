package edu.virginia.engine.events;



public class CharacterDeathManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if (event instanceof CharacterDeathEvent){
			CharacterDeathEvent deathEvent = (CharacterDeathEvent) event;
			if (deathEvent.numCharactersLeft > 1){
				System.out.println("Character is dead!");
			}
			else { // numCharactersLeft = 1 -> game is over
				//Evan B: in future, perhaps add Tween to character death here
				System.out.println("END GAME!");
				
			}
		}
	}
}