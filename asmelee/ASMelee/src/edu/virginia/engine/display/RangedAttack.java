package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;

public class RangedAttack extends AnimatedSprite{

	public Character myCharacter;
	public int xSpeed;
	public int ySpeed;
	public int damage;
	public int knockback;
	public RangedAttack(Character c) //creates a ranged attack in front of the character that moves at the set speed
	{
		super(c.getId()+"_projectile",false);
		myCharacter = c;
		c.getParent().addChild(this);

		ySpeed = 0;
		xSpeed = 1;
		damage = 5;
		knockback = 50;

		int xoffset =(int)(c.getUnscaledWidth()*c.getScaleX());
		int yoffset = 10;
		

		if(myCharacter instanceof Peter)
		{
			this.setImage("fart.png");
		}
		else if(myCharacter instanceof Stewie)
		{
			this.setImage("lightning_bolt.png");
		}

		else if(myCharacter instanceof Trooper)
		{
			this.setImage("laser_missile.png");
			xoffset += 25;
		}
		else if(myCharacter instanceof Pikachu)
		{
			this.setImage("pikachu_thundershock.PNG");
			this.setScaleX(.5);
			this.setScaleY(.5);
			if(myCharacter.getScaleX()<0)
			{
				this.setScaleX(this.getScaleX()*-1);
				xoffset += 40;
			}
		}
		else if(myCharacter instanceof Goku){
			this.setImage("gokuProjectile.png");
			damage = 15;
			//while(this.getCurrentFrame() < 13); // don't throw ranged until end of animation
		}
		else if(myCharacter instanceof Naruto && myCharacter.shooting){
			this.setImage("NarutoProjectile.png");
			xSpeed = 3;
		}
		else if((myCharacter instanceof Naruto && myCharacter.specialing)){
			this.setImage("NarutoBall1.png");
			xSpeed = 0;
			damage = 15;
			yoffset = -20;
		}
		else
		{
			this.setImage("sample_ranged.png");
		}

		if(c.getScaleX() < 0)
		{
			xSpeed *=-1;
			//xoffset*=2;
		}
		System.out.println(c.getScaleX() + " "+ xoffset);
		this.setPosition(new Point((int)c.getGlobalPosition().x+xoffset,(int)c.getGlobalPosition().y+yoffset));
	}
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers)
	{
		super.update(pressedKeys,controllers);
		Point p=new Point();
		p.setLocation(position.getX()+xSpeed, position.getY()+ySpeed);
		this.setPosition(p);
		
		if(this.myCharacter instanceof Peter)
		{
			this.setScaleX(this.getScaleX()+.006);
			this.setScaleY(this.getScaleX()+.006);
			if(this.getAlpha() <= 0.01)
				this.destroy(null);
			if (this.visible)
				this.setAlpha(this.getAlpha()-.004);
		}
		//System.out.println("updated");
	}
	public void destroy(DisplayObject o)
	{
		this.setVisible(false);
		myCharacter.getParent().removeChild(this);
		if(o instanceof Character)
		{
			Character otherChar=(Character)o;
			otherChar.healthbar.decreaseHealth(damage);
			//if(myCharacter instanceof Naruto) { // Naruto can use special again
				//myCharacter.stopSpecial = false;
			//}
		}
	}
	public void setDamage(int dam)
	{
		damage = dam;
	}
	public void setKnockback(int knock)
	{
		knockback = knock;
	}
	public void setYSpeed(int ys)
	{
		ySpeed = ys;
	}
	public void setXSpeed(int xs)
	{
		xSpeed = xs;
	}
}
