package edu.virginia.engine.events;

import edu.virginia.engine.display.Character;
import edu.virginia.engine.display.Goku;
import edu.virginia.engine.display.Stewie;
import edu.virginia.engine.display.Trooper;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.engine.tweening.TweenableParam;

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
			// if character c is hitting other
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
				int originalY = (int) other.getPosition().getY();
				
				int newX = 0;
				int newY = 0;
				
				// add code that depends on move
				if(other.healthbar.actualHealth > 0){
					if(direction == 0)
						newX = originalX + 100;
					if(direction == 1)
						newX = originalX - 100;
					
					linearTween.animate(TweenableParam.POSITIONX, originalX, newX, 500);
					//System.out.println("Mario health is: " + (other.healthbar.getActualHealth()-5));
					double damage = 5;
					if(event.getSource() instanceof Trooper)
						damage = 4;
					if(event.getSource() instanceof Stewie)
						damage = 2;
					other.healthbar.setActualHealth(other.healthbar.getActualHealth()-c.meleeDamage);
					
				}
				else
				{
					if(direction == 0)
					{
						newX = originalX + 5000;
					}
					if(direction == 1)
					{
						newX = originalX - 5000;
					}
					
					newY = originalY - 5000;
					
					/*
					 * Clear any other tweens acting upon object
					 */
					TweenJuggler.getInstance().clearTweens(other);
					
					Tween deathTween = new Tween(other, new TweenTransitions(transitiontype.lineartrans), "LinearTween");
					deathTween.animate(TweenableParam.POSITIONX, originalX, newX, 4000);
					deathTween.animate(TweenableParam.POSITIONY, originalY, newY, 4000);
				}
			}
			else if(c.specialing && c instanceof Goku){
				int direction = 0;
				if(c.getPosition().getX()<other.getPosition().getX())
					direction = 0;
				else
					direction = 1;
				Tween linearTween = new Tween(other, new TweenTransitions(transitiontype.easeOut), "easeOut");
				int originalX = (int) other.getPosition().getX();
				int originalY = (int) other.getPosition().getY();
				
				int newX = 0;
				int newY = 0;
				
				// add code that depends on move
				if(other.healthbar.actualHealth > 0){
					if(direction == 0)
						newX = originalX + 130;
					if(direction == 1)
						newX = originalX - 130;
					
					linearTween.animate(TweenableParam.POSITIONX, originalX, newX, 500);
					//System.out.println("Mario health is: " + (other.healthbar.getActualHealth()-5));
					other.healthbar.setActualHealth(other.healthbar.getActualHealth()-2);
				}
				else
				{
					if(direction == 0)
					{
						newX = originalX + 500;
					}
					if(direction == 1)
					{
						newX = originalX - 500;
					}
					
					newY = originalY - 500;
					
					/*
					 * Clear any other tweens acting upon object
					 */
					TweenJuggler.getInstance().clearTweens(other);
					
					Tween deathTween = new Tween(other, new TweenTransitions(transitiontype.lineartrans), "LinearTween");
					deathTween.animate(TweenableParam.POSITIONX, originalY, newX, 1500);
					deathTween.animate(TweenableParam.POSITIONY, originalY, newY, 1500);
				}
			}
		}
	}
}