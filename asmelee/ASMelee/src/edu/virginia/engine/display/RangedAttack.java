package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

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
		if((myCharacter instanceof Peter))
		{
			this.setImage("fart.png");
		}
		else
		{
			this.setImage("sample_ranged.png");
		}
		ySpeed = 0;
		xSpeed = 1;
		damage = 5;
		knockback = 50;
		int xoffset =(int)(c.getUnscaledWidth()*c.getScaleX());
		if(c.getScaleX() < 0)
		{
			xSpeed *=-1;
			xoffset*=3;
		}
		this.setPosition(new Point((int)c.getGlobalPosition().x+xoffset,(int)c.getGlobalPosition().y));
	}
	public void update(ArrayList<String> pressedKeys)
	{
		super.update(pressedKeys);
		Point p=new Point();
		p.setLocation(position.getX()+xSpeed, position.getY()+ySpeed);
		this.setPosition(p);
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
