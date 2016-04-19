package edu.virginia.engine.display;

import java.util.ArrayList;
import java.util.Calendar;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.events.CharacterCollisionEvent;
import edu.virginia.engine.events.SpecialTrooperCollisionEvent;
import edu.virginia.engine.events.PlatformCollisionEvent;
import edu.virginia.engine.events.RangedCollisionEvent;
import edu.virginia.engine.events.SpecialStewieCollisionEvent;
import edu.virginia.main.Main;

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
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers) {
		super.update(pressedKeys,controllers);
		ArrayList<DisplayObject>allchildren = Main.getAllchildren();
		boolean character = (this instanceof Character);
		boolean projectile = (this instanceof RangedAttack);
		boolean stewieSpecial = (this instanceof Laser);
		boolean trooperSpecial = (this instanceof LightningTower);
		for(DisplayObject o:allchildren)
		{
			
			if(/*this.getHitboxGlobal() != null && o.getHitboxGlobal() != null &&*/ !this.equals(o) && this.collidesWith(o))
			{	
				if(o instanceof Platform)
				{
					//System.out.println(this.getId() + " hit platform");
					PlatformCollisionEvent e = new PlatformCollisionEvent();
					e.setSource(this);
					e.setPlatform((Platform)o);
					Platform p = (Platform)o;
					if(this.getPosition().getY()<p.getPosition().getY())
						this.dispatchEvent(e);
				}
				else if(character && (o instanceof Character))
				{
					CharacterCollisionEvent e = new CharacterCollisionEvent();
					e.setSource(this);
					Character c = (Character)o;
					e.setCharacter(c);
					this.dispatchEvent(e);
				}
				else if(projectile && (o instanceof Character))
				{
					//System.out.println("collided "+o.getId());
					RangedCollisionEvent e = new RangedCollisionEvent();
					Character c = (Character)o;
					e.setSource(c);
					e.setRangedAttack((RangedAttack) this);
					o.dispatchEvent(e);
				}
				else if(stewieSpecial && (o instanceof Character))
				{
					Laser temp = (Laser)this;
					if(!temp.owner.equals(o))
					{
						//System.out.println(o.getId());
						SpecialStewieCollisionEvent e = new SpecialStewieCollisionEvent();
						Character c = (Character)o;
						e.setSource(c);
						e.setDamager(this);
						o.dispatchEvent(e);
					}
				}
				else if(trooperSpecial && (o instanceof Character))
				{
					LightningTower temp = (LightningTower)this;
					if(!temp.owner.equals(o))
					{
						//System.out.println(o.getId());
						SpecialTrooperCollisionEvent e = new SpecialTrooperCollisionEvent();
						Character c = (Character)o;
						e.setSource(c);
						e.setDamager(this);
						o.dispatchEvent(e);
					}
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
//					   DynamoDBManager.getInstance().pushSpriteProperties(s);
				   }
			}

//		     Runnable r = new MyThread(this);
//		     new Thread(r).start();
			
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
//					   DynamoDBManager.getInstance().pullSpriteProperties(s);
				   }
			}

//			Runnable r = new MyThread(this);
//			new Thread(r).start();

			this.lastUpdated = Calendar.getInstance().getTimeInMillis();
		}
	}
}