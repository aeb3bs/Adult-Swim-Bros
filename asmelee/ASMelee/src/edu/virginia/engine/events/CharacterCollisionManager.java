package edu.virginia.engine.events;

import java.awt.Point;

import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Stewie;
import edu.virginia.main.Main;
import edu.virginia.engine.display.Character;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;

public class CharacterCollisionManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if(event.getSource() instanceof Character && event instanceof CharacterCollisionEvent)
		{
			CharacterCollisionEvent ce = (CharacterCollisionEvent)event;
			Character c = (Character) event.getSource();
			Character other = ce.character;
			
			/*
			 * Add all the hitting code in this if statement
			 */
			if(c.getHitting())
			{
				/*
				 * if direction == 0, we are hitting other character from left
				 * if direction == 1, we are hitting other character from right
				 */
				int direction = 0;
				if(c.getPosition().getX()<other.getPosition().getX())
					direction = 0;
				else
					direction = 1;
				Tween linearTween = new Tween(other, new TweenTransitions(transitiontype.easeOut), "easeOut");
				int originalX = (int) other.getPosition().getX();
				
				int newX = 0;
				if(direction == 0)
					newX = originalX + 100;
				if(direction == 1)
					newX = originalX - 100;
				linearTween.animate(TweenableParam.POSITIONX, originalX, newX, 500);
			}
		}
	}
}