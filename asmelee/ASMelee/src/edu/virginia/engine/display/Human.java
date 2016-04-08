package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.events.PickedUpEvent;

public class Human extends AnimatedSprite {
	int animationMode;
	public Human(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.setCurrentFrame(0);
		this.setStartIndex(0);
		this.setEndIndex(2);
		this.setLatency(10);
		this.setAnimationMode(0);
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage d1 = DisplayObject.readImage("ash_walking_down_1.png");
		images.add(d1);
		BufferedImage d2 = DisplayObject.readImage("ash_walking_down_2.png");
		images.add(d2);
		BufferedImage d3 = DisplayObject.readImage("ash_walking_down_3.png");
		images.add(d3);
		BufferedImage u1 = DisplayObject.readImage("ash_walking_up_1.png");
		images.add(u1);
		BufferedImage u2 = DisplayObject.readImage("ash_walking_up_2.png");
		images.add(u2);
		BufferedImage u3 = DisplayObject.readImage("ash_walking_up_3.png");
		images.add(u3);
		BufferedImage h1 = DisplayObject.readImage("ash_walking_horizontal_1.png");
		images.add(h1);
		BufferedImage h2 = DisplayObject.readImage("ash_walking_horizontal_2.png");
		images.add(h2);
		BufferedImage h3 = DisplayObject.readImage("ash_walking_horizontal_3.png");
		images.add(h3);
		
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
			}
		}
		this.setAnimationMode(mode);
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
				
				this.setEndIndex(8);
				//need to change direction instead of movement
				if(this.getStartIndex() != 6 || this.getScaleX() < 0)
				{
					this.setStartIndex(6);
					this.setCurrentFrame(6);
					if(this.getScaleX()<0)
					{
						Point rescaledPosition = new Point((int)this.getPosition().getX()-this.getUnscaledWidth(), (int)this.getPosition().getY());
						this.setPosition(rescaledPosition);
						this.setScaleX(1.0);
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
				
				this.setEndIndex(8);
				//need to change direction instead of movement
				if(this.getStartIndex() != 6 || this.getScaleX() > 0)
				{
					this.setStartIndex(6);
					this.setCurrentFrame(6);
					if(this.getScaleX()>0)
					{
						Point rescaledPosition = new Point((int)this.getPosition().getX()+this.getUnscaledWidth(), (int)this.getPosition().getY());
						this.setPosition(rescaledPosition);
						this.setScaleX(-1.0);
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
			else if(key.equals(up) || key.equals(keyw))
			{
				if(key.equals(up))
				{
					//walk
					this.animate(1);
				}
				else if(key.equals(keyw))
				{
					//run
					this.animate(2);
				}
				
				this.setEndIndex(5);
				//need to change direction instead of movement
				if(this.getStartIndex() != 3)
				{
					this.setStartIndex(3);
					this.setCurrentFrame(3);
					if(this.getScaleX()<0)
					{
						Point rescaledPosition = new Point((int)this.getPosition().getX()-this.getUnscaledWidth(), (int)this.getPosition().getY());
						this.setPosition(rescaledPosition);
						this.setScaleX(1.0);
					}
				}
				else
				{
					//update position
					this.setPosition(new Point(this.getPosition().x,this.getPosition().y-=this.getSpeed()));
					
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
			else if(key.equals(down) || key.equals(keys))
			{
				if(key.equals(down))
				{
					//walk
					this.animate(1);
				}
				else if(key.equals(keys))
				{
					//run
					this.animate(2);
				}
				
				this.setEndIndex(2);
				//need to change direction instead of movement
				if(this.getStartIndex() != 0)
				{
					this.setStartIndex(0);
					this.setCurrentFrame(0);
					if(this.getScaleX()<0)
					{
						Point rescaledPosition = new Point((int)this.getPosition().getX()-this.getUnscaledWidth(), (int)this.getPosition().getY());
						this.setPosition(rescaledPosition);
						this.setScaleX(1.0);
					}
				}
				else
				{
					//update position
					this.setPosition(new Point(this.getPosition().x,this.getPosition().y+=this.getSpeed()));
					
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
		}
	}
}
