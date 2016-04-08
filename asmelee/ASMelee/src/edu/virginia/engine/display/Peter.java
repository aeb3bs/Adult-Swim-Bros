package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.main.Main;

public class Peter extends Character {
	boolean specialingdown;
	boolean specialingup;
	public Peter(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.setCurrentFrame(0);
		this.setStartIndex(0);
		this.setEndIndex(7);
		this.setLatency(10);
		this.setAnimationMode(0);
		this.jumping = false;
		this.hitting = false;
		this.specialingdown = false;
		this.specialingup = false;
		this.specialing = false;
		this.shooting = false;
		myControllerIndex = 0;
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage d1 = DisplayObject.readImage("peter_griffin_standing.png");
		images.add(d1);
		BufferedImage d2 = DisplayObject.readImage("peter_griffin_walking_1.png");
		images.add(d2);
		BufferedImage d3 = DisplayObject.readImage("peter_griffin_walking_2.png");
		images.add(d3);
		BufferedImage d4 = DisplayObject.readImage("peter_griffin_walking_3.png");
		images.add(d4);
		BufferedImage d5 = DisplayObject.readImage("peter_griffin_walking_4.png");
		images.add(d5);
		BufferedImage d6 = DisplayObject.readImage("peter_griffin_walking_5.png");
		images.add(d6);
		BufferedImage d7 = DisplayObject.readImage("peter_griffin_walking_6.png");
		images.add(d7);
		BufferedImage d8 = DisplayObject.readImage("peter_griffin_walking_7.png");
		images.add(d8);
		BufferedImage d9 = DisplayObject.readImage("peter_griffin_melee_1.png");
		images.add(d9);
		BufferedImage d10 = DisplayObject.readImage("peter_griffin_melee_2.png");
		images.add(d10);
		BufferedImage d11 = DisplayObject.readImage("peter_griffin_melee_3.png");
		images.add(d11);
		BufferedImage d12 = DisplayObject.readImage("peter_griffin_melee_4.png");
		images.add(d12);
		BufferedImage d13 = DisplayObject.readImage("peter_griffin_melee_5.png");
		images.add(d13);
		BufferedImage d14 = DisplayObject.readImage("peter_griffin_melee_6.png");
		images.add(d14);
		BufferedImage d15 = DisplayObject.readImage("peter_griffin_special_1.png");
		images.add(d15);
		BufferedImage d16 = DisplayObject.readImage("peter_griffin_special_2.png");
		images.add(d16);
		BufferedImage d17 = DisplayObject.readImage("peter_griffin_special_3.png");
		images.add(d17);
		BufferedImage d18 = DisplayObject.readImage("peter_griffin_special_4.png");
		images.add(d18);
		BufferedImage d19 = DisplayObject.readImage("peter_griffin_special_5.png");
		images.add(d19);
		BufferedImage d20 = DisplayObject.readImage("peter_griffin_special_6.png");
		images.add(d20);
		BufferedImage d21 = DisplayObject.readImage("peter_griffin_special_7.png");
		images.add(d21);
		BufferedImage d22 = DisplayObject.readImage("peter_griffin_special_8.png");
		images.add(d22);
		BufferedImage d23 = DisplayObject.readImage("peter_griffin_range_1.png");
		images.add(d23);

		
		
		
		
		this.setImages(images);
	}
	
	public void animate(int mode)
	{
		if(this.getAnimationMode()!=mode)
		{
			this.resetAnimation();
			switch(mode){
				//walking
				case 1: this.setLatency(10);
						this.setSpeed(1.5);
						break;
				//running
				case 2: this.setLatency(5);
						this.setSpeed(3);
						this.setStartIndex(0);
						this.setCurrentFrame(0);
						this.setEndIndex(7);
						break;
				//jumping
				case 3: this.setVelocity_y(-300);
						this.jumping = true;
						this.setStartIndex(4);
						this.setCurrentFrame(4);
						this.setEndIndex(4);
						break;
				//melee attack
				case 4: this.setLatency(10);
						this.hitting = true;
						this.setStartIndex(8);
						this.setCurrentFrame(8);
						this.setEndIndex(13);
						break;
						//ranged attack
				case 5: this.setLatency(100);
						this.setStartIndex(22);
						this.setCurrentFrame(22);
						this.setEndIndex(22);
						BufferedImage currentImage = this.getImage();
						this.setImage(currentImage);
						this.shooting = true;
						break;
				//special_attack
				case 6: this.setLatency(10);
						this.specialingdown = true;
						this.specialing = true;
						//this.shooting=false;// this makes melee act as a reload, temporary until we can wait until animation finishes to do it
						this.setStartIndex(14);
						this.setCurrentFrame(14);
						this.setEndIndex(21);
						break;
			}
			BufferedImage currentImage = this.getImage();
			this.setImage(currentImage);
		}
		this.setAnimationMode(mode);
	}
	
	@Override
	public BufferedImage getImage()
	{
		if(this.currentFrame == 21 && specialingdown)
		{
			specialingup = true;
			specialingdown = false;
			this.setLatency(this.getLatency());
			
			ArrayList<Character>characters = new ArrayList<>();
			for(DisplayObject o:Main.getAllchildren())
			{
				if(o instanceof Character && !o.equals(this))
				{
					characters.add((Character)o);
				}
			}
			for(int index=characters.size()-1;index>=0;index--)
			{
				Character temp = characters.get(index);
				if(!(Math.abs(temp.position.y-this.position.y)<=50))
				{
					characters.remove(index);
				}
			}
			Character closestChar = null;
			int minX = Integer.MAX_VALUE;
			for(int index=0;index<characters.size();index++)
			{
				int delta = Math.abs(this.position.x-characters.get(index).position.x);
				if(delta<minX)
				{
					closestChar = characters.get(index);
					minX = delta;
				}
			}
			
			if(closestChar != null)
			{
				int width = (int) (closestChar.getUnscaledWidth() * closestChar.scaleX);
				
				if(closestChar.scaleX>0)
				{
					this.position.x = closestChar.position.x + width - 70;
					this.scaleX = .5;
				}
				else
				{
					this.position.x = closestChar.position.x + width + 70;
					this.scaleX = -.5;
				}
					
			}
			
			return getImages().get(21);
		}
		else if(this.currentFrame == 14 && specialingup)
		{
			animate(1);
			specialingup = false;
			this.setCurrentFrame(0);
			this.setStartIndex(0);
			this.setEndIndex(7);
			this.setAnimationMode(0);
			this.resetAnimation();
			this.specialing = false;
		}
		else if(specialingup)
		{
			BufferedImage currentImage = this.images.get(currentFrame);
			
			//next image
			if(this.imagemap.get(currentImage)<=0)
			{
				currentFrame--;
				if(this.getCurrentFrame()>this.getEndIndex())
					this.resetAnimation();
				currentImage = this.images.get(currentFrame);
			}
			
			this.getImagemap().put(currentImage, this.getImagemap().get(currentImage)-1);
			
			return currentImage;
		}
		return super.getImage();
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public int getAnimationMode() {
		return animationMode;
	}

	public void setAnimationMode(int animationMode) {
		this.animationMode = animationMode;
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers)
	{	
		super.update(pressedKeys,controllers);
		
		if(TweenJuggler.getInstance().tweenobjects.contains(this)==true)
		{
			return;
		}
		
		if(this.getAnimationMode()==0)
		{
			this.setStartIndex(0);
			this.setCurrentFrame(0);
			this.setEndIndex(7);
			BufferedImage image = this.getImage();
			this.setImage(image);
		}
			
		
		if(this.getAnimationMode()==4 || this.getAnimationMode()==6)
		{
			BufferedImage image = this.getImage();
			this.setImage(image);
		}
		
		if(onlineSprite)
		{
			return;
		}
		
		if(specialing)//need to make this work in char update to
		{
			return;
		}
		
/*
		Stack<String>keysPressed = new Stack<String>();
		for(int index=0; index<pressedKeys.size();index++)
			keysPressed.push(pressedKeys.get(index));
		
		while(!keysPressed.empty())
		{	
			String key = keysPressed.pop();
			
			String right = KeyEvent.getKeyText(KeyEvent.VK_RIGHT);
			String left = KeyEvent.getKeyText(KeyEvent.VK_LEFT);
			String up = KeyEvent.getKeyText(KeyEvent.VK_UP);
			String down = KeyEvent.getKeyText(KeyEvent.VK_DOWN);
			
			String keyd = KeyEvent.getKeyText(KeyEvent.VK_D);
			String keys = KeyEvent.getKeyText(KeyEvent.VK_S);
			String keya = KeyEvent.getKeyText(KeyEvent.VK_A);
			String keyw = KeyEvent.getKeyText(KeyEvent.VK_W);
			
			//jump
			String space = KeyEvent.getKeyText(KeyEvent.VK_SPACE);
			//melee
			String enter = KeyEvent.getKeyText(KeyEvent.VK_ENTER);
			//special
			String backspace = KeyEvent.getKeyText(KeyEvent.VK_BACK_SPACE);
			String shift = KeyEvent.getKeyText(KeyEvent.VK_SHIFT);
			
			if(key.equals(right) || key.equals(keyd))
			{	
				if(key.equals(right))
				{
					//walk
					this.animate(1);
				}
				else if(key.equals(keyd))
				{
					//run
					this.animate(2);
				}
				
				this.setEndIndex(7);
				//need to change direction instead of movement
				if(this.getStartIndex() != 0 || this.getScaleX() < 0)
				{
					this.setStartIndex(0);
					this.setCurrentFrame(0);
					if(this.getScaleX()<0)
					{
						int width = (int) (this.getUnscaledWidth()*Math.abs(this.getScaleX()));
						Point rescaledPosition = new Point((int)this.getPosition().getX()-width, (int)this.getPosition().getY());
						this.setPosition(rescaledPosition);
						this.setScaleX(.5);
					}
				}
				else
				{
					//update position
					this.setPosition(new Point(this.getPosition().x+=this.getSpeed(),this.getPosition().y));
					
					//check if walk sequence is to repeat
					if(this.getCurrentFrame()>this.getEndIndex())
					{
						this.setCurrentFrame(this.getStartIndex());
						for(int index=0;index<this.getImages().size();index++)
						{
							this.getImagemap().put(this.getImages().get(index), this.getLatency());
						}
					}
					
					//change image
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
				}
			}
			else if(key.equals(left) || key.equals(keya))
			{
				if(key.equals(left))
				{
					//walk
					this.animate(1);
				}
				else if(key.equals(keya))
				{
					//run
					this.animate(2);
				}
				
				this.setEndIndex(7);
				//need to change direction instead of movement
				if(this.getStartIndex() != 0 || this.getScaleX() > 0)
				{
					this.setStartIndex(0);
					this.setCurrentFrame(0);
					if(this.getScaleX()>0)
					{
						int width = (int) (this.getUnscaledWidth()*Math.abs(this.getScaleX()));
						Point rescaledPosition = new Point((int)this.getPosition().getX()+width, (int)this.getPosition().getY());
						this.setPosition(rescaledPosition);
						this.setScaleX(-.5);
					}
				}
				else
				{
					//update position
					this.setPosition(new Point(this.getPosition().x-=this.getSpeed(),this.getPosition().y));
					
					//change image
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
				}
			}
			else if(key.equals(space))
			{
				if(!jumping)
				{
					this.animate(3);
					
					
					
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
				}
			}
			else if(key.equals(enter))
			{
				if(!hitting)
				{
					this.animate(4);
					
					this.setStartIndex(8);
					this.setCurrentFrame(8);
					this.setEndIndex(13);
					
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
					
					this.setDefaultHitbox();
				}
			}
			else if(key.equals(backspace))
			{
				if(!specialingdown && !specialingup)
				{
					this.animate(6);
					
					this.setStartIndex(14);
					this.setCurrentFrame(14);
					this.setEndIndex(21);
					
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
					
					this.setDefaultHitbox();
				}
			}
			else if(key.equals(shift))// Ranged Attack
			{
				if(!shooting)
				{
					/*this.animate(4);
					
					this.setStartIndex(5);
					this.setCurrentFrame(5);
					this.setEndIndex(7);
					
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
					
					this.animate(5);
					new RangedAttack(this);
					
					this.setStartIndex(22);
					this.setCurrentFrame(22);
					this.setEndIndex(22);
					
					BufferedImage currentImage = this.getImage();
					this.setImage(currentImage);
					
					//this.setDefaultHitbox();
				}
			}
		}*/
	}
	
	@Override 
	public Rectangle getHitboxGlobal()
	{
		Rectangle h = super.getHitboxGlobal();
		if(this.getScaleX()<0)
		{
			h.x -= h.width;
		}
		return h;
	}

	@Override
	public void rangedAttack() {
		if(!shooting)
		{
			/*this.animate(4);
			
			this.setStartIndex(5);
			this.setCurrentFrame(5);
			this.setEndIndex(7);
			
			BufferedImage currentImage = this.getImage();
			this.setImage(currentImage);*/
			
			this.animate(5);
			new RangedAttack(this);
			//this.setDefaultHitbox();
		}
	}

	@Override
	public void meleeAttack() {
		if(!hitting)
		{
			this.animate(4);

			this.setDefaultHitbox();
		}		
	}

	@Override
	public void specialAttack() {
		if(!specialing)
		{
			this.animate(6);
			
			this.setDefaultHitbox();
		}
		
	}
}
