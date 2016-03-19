package edu.virginia.engine.display;

import java.util.ArrayList;
import java.util.Calendar;

import edu.virginia.engine.dynamodbmanager.DynamoDBManager;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.lab1test.LabSixGame;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Sprite extends DisplayObjectContainer {
	//False: Sprite is being controlled by local player
	//True: Sprite is being controlled by online player
	boolean onlineSprite;
	long lastUpdated;
	final long deltaT = 100;
	public Sprite(String id, boolean onlineSprite) {
		super(id);
		this.onlineSprite=onlineSprite;
		this.lastUpdated = Calendar.getInstance().getTimeInMillis();
	}

	public Sprite(String id, String imageFileName, boolean onlineSprite) {
		super(id, imageFileName);
		this.onlineSprite=onlineSprite;
		this.lastUpdated = Calendar.getInstance().getTimeInMillis();
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		ArrayList<DisplayObject>allchildren = LabSixGame.getAllchildren();
		for(DisplayObject o:allchildren)
		{
			if(!this.equals(o) && this.collidesWith(o))
			{
				CollisionEvent e = new CollisionEvent();
				if(o instanceof Platform)
				{
					e.setSource(this);
					e.setPlatform((Platform)o);
					Platform p = (Platform)o;
					if(this.getPosition().getY()<p.getPosition().getY())
						this.dispatchEvent(e);
				}
			}
		}
		
		//update onlinesprite properties
		if(!onlineSprite && (Calendar.getInstance().getTimeInMillis()-this.lastUpdated>=deltaT))
		{
			class MyThread implements Runnable {
				Sprite s;
				   public MyThread(Object parameter) {
				       this.s=(Sprite)parameter;
				   }

				   public void run() {
					   DynamoDBManager.getInstance().pushSpriteProperties(s);
				   }
			}

		     Runnable r = new MyThread(this);
		     new Thread(r).start();
			
			this.lastUpdated = Calendar.getInstance().getTimeInMillis();
		}
		else if((Calendar.getInstance().getTimeInMillis()-this.lastUpdated>=deltaT))
		{
			class MyThread implements Runnable {
				Sprite s;
				   public MyThread(Object parameter) {
				       this.s=(Sprite)parameter;
				   }

				   public void run() {
					   DynamoDBManager.getInstance().pullSpriteProperties(s);
				   }
			}

		     Runnable r = new MyThread(this);
		     new Thread(r).start();
			this.lastUpdated = Calendar.getInstance().getTimeInMillis();
		}
	}
}