package edu.virginia.engine.events;

import java.awt.Point;

import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Stewie;
import edu.virginia.main.Main;

public class PlatformManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if(event.getSource() instanceof PhysicsSprite)
		{
			PlatformCollisionEvent cevent = (PlatformCollisionEvent)event;
			
			PhysicsSprite ps = (PhysicsSprite) event.getSource();
			if(ps.getVelocity_y()>=0)
			{
				/*
				 * top collision correction. for instance, if sprite is calculated to land in middle of platform
				 */
				if(event.getSource() instanceof Mario)
				{
					int newY = (int) (cevent.platform.getPosition().getY()-ps.getImage().getHeight()*ps.getScaleY());
					Point p = new Point((int)ps.getPosition().getX(),newY);
					ps.setPosition(p);
					
					Mario m = (Mario)ps;
					ps.setVelocity_y(0);
					ps.setAcceleration_y(-Main.gravity);
					if(m.getAnimationMode()==3)
					{
						m.setAnimationMode(0);
					}
					m.setJumping(false);
				}
				else if(event.getSource() instanceof Stewie)
				{
					/*
					 * This code is to realign stewie if he falls to far into a platform
					 */
					int height = (int) Math.abs(ps.getImage().getHeight() * ps.getScaleY()); 
					int difference = (int) Math.abs(cevent.platform.getPosition().getY()-(ps.getPosition().getY()+height));
					if(difference > 5)
					{
						int newY = (int) (cevent.platform.getPosition().getY()-ps.getImage().getHeight()*ps.getScaleY()+1);
						Point p = new Point((int)ps.getPosition().getX(),newY);
						ps.setPosition(p);
					}
					
					Stewie stew = (Stewie)ps;
					ps.setVelocity_y(0);
					ps.setAcceleration_y(-Main.gravity);
					if(stew.getAnimationMode()==3)
					{
						stew.setAnimationMode(0);
					}
					stew.setJumping(false);
				}
				else
				{
					ps.setVelocity_y(0);
				}
			}
			else
			{
				
			}
		}
	}
}