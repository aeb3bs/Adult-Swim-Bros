package edu.virginia.engine.events;


import java.awt.Point;

import edu.virginia.engine.display.Character;
import edu.virginia.engine.display.LightningTower;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.engine.tweening.TweenableParam;

public class SpecialTrooperCollisionManager implements IEventListener {
	public int counter = 0;
	@Override
	public void handleEvent(Event event) {
		if (event instanceof SpecialTrooperCollisionEvent){
			counter++;
			System.out.println("StewieSpecial");
			SpecialTrooperCollisionEvent e = (SpecialTrooperCollisionEvent)event;
			Character other = (Character)e.getSource();
			LightningTower ls = (LightningTower)e.target;
			if(counter % ls.ticks ==0)
			{
				other.healthbar.decreaseHealth(ls.damage);

				System.out.println(counter);
				/*
				 * if direction == 0, we are hitting other character from left
				 * if direction == 1, we are hitting other character from right
				 */
				int direction = 0;
				if(ls.owner.getScaleX()>0)
					direction = 0;
				else
					direction = 1;
				//Tween linearTween = new Tween(other, new TweenTransitions(transitiontype.easeOut), "easeOut");
				int originalX = (int) other.getPosition().getX();
				int originalY = (int) other.getPosition().getY();
				
				int newX = 0;
				int newY = 0;
				
				// add code that depends on move
				if(other.healthbar.actualHealth > 0){
					if(direction == 0)
						newX = originalX + ls.knockback;
					if(direction == 1)
						newX = originalX - ls.knockback;
					
					//linearTween.animate(TweenableParam.POSITIONX, originalX, newX, ls.knockback);
					other.setPosition(new Point(newX,originalY));
					other.setVelocity_y(-150.0);
					//System.out.println("Mario health is: " + (other.healthbar.getActualHealth()));
					//other.healthbar.setActualHealth(other.healthbar.getActualHealth()-5);
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