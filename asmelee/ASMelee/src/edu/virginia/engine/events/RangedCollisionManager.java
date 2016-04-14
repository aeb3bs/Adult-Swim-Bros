package edu.virginia.engine.events;

import java.awt.Point;

import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.RangedAttack;
import edu.virginia.engine.display.Stewie;
import edu.virginia.main.Main;
import edu.virginia.engine.display.Character;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;

public class RangedCollisionManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if(event.getSource() instanceof Character && event instanceof RangedCollisionEvent)
		{
			RangedCollisionEvent ce = (RangedCollisionEvent)event;
			Character other = (Character) event.getSource();
			//System.out.println("event thrown ");
			 RangedAttack ra= ce.rangedAttack;
			if(other.equals(ra.myCharacter))
				return;

			//System.out.println("event continued ");
			/*
			 * Add all the hitting code in this if statement
			 */
			// if character c is hitting other
		
			/*
			 * if direction == 0, we are hitting other character from left
			 * if direction == 1, we are hitting other character from right
			 */
			int direction = 0;
			if(ra.xSpeed >0)
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
					newX = originalX + ra.knockback;
				if(direction == 1)
					newX = originalX - ra.knockback;
				
				linearTween.animate(TweenableParam.POSITIONX, originalX, newX, 500);
				//System.out.println("Mario health is: " + (other.healthbar.getActualHealth()-5));
				ra.destroy(other);//destroys the object and deals damage to other
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