package edu.virginia.engine.events;

import java.awt.Point;

import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.lab1test.LabSixGame;

public class PlatformManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if(event.getSource() instanceof PhysicsSprite)
		{
			CollisionEvent cevent = (CollisionEvent)event;
			
			PhysicsSprite ps = (PhysicsSprite) event.getSource();
			if(ps.getVelocity_y()>=0)
			{
				/*
				 * top collision correction. for instance, if sprite is calculated to land in middle of platform
				 */
				int newY = (int) (cevent.platform.getPosition().getY()-ps.getImage().getHeight()+1);
				Point p = new Point((int)ps.getPosition().getX(),newY);
				ps.setPosition(p);
				
				if(event.getSource() instanceof Mario)
				{
					Mario m = (Mario)ps;
					ps.setVelocity_y(0);
					ps.setAcceleration_y(-LabSixGame.gravity);
					if(m.getAnimationMode()==3)
					{
						m.setAnimationMode(0);
					}
					m.setJumping(false);
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