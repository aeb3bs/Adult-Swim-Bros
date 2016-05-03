package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import edu.virginia.engine.controller.GamePad;

public class Goku extends Character{
	Point preSpecialPosition = new Point(0, 0);
	int bombPrep = 40;
	int rCool = bombPrep;
	public Goku(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.setCurrentFrame(0);
		this.setStartIndex(0);
		this.setEndIndex(2);
		this.setLatency(10);
		this.setAnimationMode(0);
		this.jumping = false;
		this.hitting = false;
		this.shooting = false;
		myControllerIndex = -1;
		specialCooldown = 200;
		this.defaultScaleX = 1.2;
		this.defaultScaleY = 1.2;
		this.setScaleX(this.defaultScaleX);
		this.setScaleY(this.defaultScaleY);
		this.rangedCooldown = 110;
		
		//running
		this.animRestart[2]=true;
		
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage d1 = DisplayObject.readImage("gokuStanding.png");
		images.add(d1);
		BufferedImage d2 = DisplayObject.readImage("gokuDash.png");
		images.add(d2);
		BufferedImage d3 = DisplayObject.readImage("gokuDash.png");
		images.add(d3);
		
		BufferedImage d4 = DisplayObject.readImage("gokuMelee1.png");
		images.add(d4);
		BufferedImage d5 = DisplayObject.readImage("gokuMelee2.png");
		images.add(d5);
		BufferedImage d6 = DisplayObject.readImage("gokuMelee3.png");
		images.add(d6);
		BufferedImage d7 = DisplayObject.readImage("gokuRanged1.png");
		images.add(d7);
		BufferedImage d8 = DisplayObject.readImage("gokuRanged2.png");
		images.add(d8);
		BufferedImage d9 = DisplayObject.readImage("gokuRanged3.png");
		images.add(d9);
		BufferedImage d10 = DisplayObject.readImage("gokuRanged4.png");
		images.add(d10);
		BufferedImage d11 = DisplayObject.readImage("gokuRanged5.png");
		images.add(d11);
		BufferedImage d12 = DisplayObject.readImage("gokuRanged6.png");
		images.add(d12);
		BufferedImage d13 = DisplayObject.readImage("gokuRanged7.png");
		images.add(d13);
		BufferedImage d14 = DisplayObject.readImage("gokuRanged8.png");
		images.add(d14);
		BufferedImage d15 = DisplayObject.readImage("gokuSpecial1.png");
		images.add(d15);
		BufferedImage d16 = DisplayObject.readImage("gokuSpecial2.png");
		images.add(d16);
		BufferedImage d17 = DisplayObject.readImage("gokuSpecial3.png");
		images.add(d17);
		BufferedImage d18 = DisplayObject.readImage("gokuJump.png");
		images.add(d18);
		this.setImages(images);
	}
	
	@Override
	public void rangedAttack()
	{
		if(!shooting)
		{
			this.animate(5);
			/*if (rCool == 0){
				new RangedAttack(this);
				rCool = bombPrep;
				//this.setDefaultHitbox();
			}*/
		}
		//else {
			/*rCool -= 1;
			if (rCool == 0){
				new RangedAttack(this);
				rCool = bombPrep;
			}*/
		//}
	}
	public void meleeAttack()
	{
		if(!hitting)
		{
			this.animate(4);	
			//this.setDefaultHitbox();
		}
	}
	public void specialAttack()
	{
		if(!specialing)
		{
			this.animate(6);
		}
	}

	@Override
	public void animate(int mode)
	{
		if(this.getAnimationMode()!=mode)
		{
			this.resetAnimation();
			//System.out.println(this.endIndex);
			switch(mode){
				//walking
				case 1: this.setLatency(10);
						this.setSpeed(1.5);
						this.setEndIndex(0);
						break;
				//running
				case 2: this.setLatency(1);
						this.setSpeed(3);
						this.setCurrentFrame(1);
						this.setStartIndex(1);
						this.setEndIndex(2);
						//this.hitting = false;
						//this.specialing = false;
						break;
				//jumping
				case 3: this.setVelocity_y(-300);
						this.jumping = true;
						this.setStartIndex(17);
						this.setCurrentFrame(17);
						this.setEndIndex(17);
						break;
				//melee attack
				case 4: this.setLatency(10);
						this.hitting = true;
						this.setStartIndex(3);
						this.setCurrentFrame(3);
						this.setEndIndex(5);
						//this.shooting = false;
						break;
				//ranged attack
				case 5: this.setLatency(10);
						this.shooting = true;
						this.setStartIndex(6);
						this.setCurrentFrame(6);
						this.setEndIndex(13);
						break;
				case 6: this.setLatency(30); // special
						preSpecialPosition.setLocation(this.getPosition().getX(), this.getPosition().getY());
						if (!this.faceLeft){
							this.setPosition(new Point(this.getPosition().x - 35, this.getPosition().y));
						}
						else {
							this.setPosition(new Point(this.getPosition().x + 25, this.getPosition().y));
						}
						this.specialing = true;
						this.setStartIndex(14);
						this.setCurrentFrame(14);
						this.setEndIndex(16);
						break;
			}
			BufferedImage currentImage = this.getImage();
			this.setImage(currentImage);
		}
		this.setAnimationMode(mode);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers){
		super.update(pressedKeys,controllers);
		Stack<String>keysPressed = new Stack<String>();
		for(int index=0; index<pressedKeys.size();index++)
			keysPressed.push(pressedKeys.get(index));
		
		/*if (pressedKeys.isEmpty() && !specialing){
			this.setCurrentFrame(2);
			this.setImage(images.get(2));
		}*/
		/*if (shooting){
			rCool -= 1;
			if (rCool == 0){
				new RangedAttack(this);
				rCool = bombPrep;
				shooting = false;
			}
		}*/
		
		GamePad player1 = null;
		try{
		player1 = controllers.get(myControllerIndex);
		}catch( Exception e)
		{}
		if(player1 != null)
		{//handles gamepad input
			if(player1.isButtonPressed(player1.BUTTON_SQUARE) && shooting){
				
				rCool -= 1;
				if (rCool == 0){
					new RangedAttack(this);
					rCool = bombPrep;
					shooting = false;
				}
			}
		
		}
		
		while(!keysPressed.empty() && this.myControllerIndex == -1){
			String key = keysPressed.pop();
			String shift = KeyEvent.getKeyText(KeyEvent.VK_BACK_SLASH);
			if (key.equals(shift) && shooting){
				rCool -= 1;
				if (rCool == 0){
					new RangedAttack(this);
					rCool = bombPrep;
					shooting = false;
				}
			}
			else {
				rCool = bombPrep;
			}
		}

	}
	@Override
	public void resetAnimation()
	{
		if (specialing){
			this.setPosition(preSpecialPosition);	
		}
			super.resetAnimation();
			//this.setCurrentFrame(2);
		

	}
	
	@Override
	public BufferedImage getImage()
	{	
		return super.getImage();
		
	}
	

	
	

}
