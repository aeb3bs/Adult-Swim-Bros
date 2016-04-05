package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import edu.virginia.engine.tweening.TweenJuggler;

public class Peter extends Character {
	public Peter(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.setCurrentFrame(0);
		this.setStartIndex(0);
		this.setEndIndex(7);
		this.setLatency(10);
		this.setAnimationMode(0);
		this.jumping = false;
		this.hitting = false;
		
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
						break;
				//jumping
				case 3: this.setVelocity_y(-300);
						this.jumping = true;
				//melee attack
				case 4: this.setLatency(10);
						this.hitting = true;
			}
		}
		this.setAnimationMode(mode);
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
	public void update(ArrayList<String> pressedKeys)
	{	
		super.update(pressedKeys);
		
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
		
		if(this.getAnimationMode()==4)
		{
			BufferedImage image = this.getImage();
			this.setImage(image);
		}
		
		if(onlineSprite)
		{
			return;
		}
		
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
			
			String space = KeyEvent.getKeyText(KeyEvent.VK_SPACE);
			
			String enter = KeyEvent.getKeyText(KeyEvent.VK_ENTER);
			
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
					
					this.setStartIndex(4);
					this.setCurrentFrame(4);
					this.setEndIndex(4);
					
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
		}
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
}
