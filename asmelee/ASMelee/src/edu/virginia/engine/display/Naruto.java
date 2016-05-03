package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import edu.virginia.engine.controller.GamePad;

public class Naruto extends Character{
	int rangedPrep = 30;
	int rCool = rangedPrep;
	int specialPrep = 40;
	int sCool = specialPrep;
	
	public Naruto(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.setCurrentFrame(1);
		this.setStartIndex(1);
		this.setEndIndex(6);
		this.setLatency(10);
		this.setAnimationMode(0);
		this.jumping = false;
		this.hitting = false;
		this.shooting = false;
		myControllerIndex = -1;
		specialCooldown = 100;
		this.defaultScaleX = 1;
		this.defaultScaleY = 1;
		this.setScaleX(this.defaultScaleX);
		this.setScaleY(this.defaultScaleY);
		
		//running
		this.animRestart[2]=true;
		
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage d1 = DisplayObject.readImage("NarutoStanding.png");
		images.add(d1);
		BufferedImage d2 = DisplayObject.readImage("NarutoRun1.png");
		images.add(d2);
		BufferedImage d3 = DisplayObject.readImage("NarutoRun2.png");
		images.add(d3);
		BufferedImage d4 = DisplayObject.readImage("NarutoRun3.png");
		images.add(d4);
		BufferedImage d5 = DisplayObject.readImage("NarutoRun4.png");
		images.add(d5);
		BufferedImage d6 = DisplayObject.readImage("NarutoRun5.png");
		images.add(d6);
		BufferedImage d7 = DisplayObject.readImage("NarutoRun6.png");
		images.add(d7);
		BufferedImage d8 = DisplayObject.readImage("NarutoMelee1.png");
		images.add(d8);
		BufferedImage d9 = DisplayObject.readImage("NarutoMelee2.png");
		images.add(d9);
		BufferedImage d10 = DisplayObject.readImage("NarutoMelee3.png");
		images.add(d10);
		BufferedImage d11 = DisplayObject.readImage("NarutoJump.png");
		images.add(d11);
		BufferedImage d12 = DisplayObject.readImage("NarutoRanged1.png");
		images.add(d12);
		BufferedImage d13 = DisplayObject.readImage("NarutoRanged2.png");
		images.add(d13);
		BufferedImage d14 = DisplayObject.readImage("NarutoRanged3.png");
		images.add(d14);
		BufferedImage d15 = DisplayObject.readImage("NarutoRanged4.png");
		images.add(d15);
		BufferedImage d16 = DisplayObject.readImage("NarutoRanged5.png");
		images.add(d16);
		BufferedImage d17 = DisplayObject.readImage("NarutoRanged6.png");
		images.add(d17);
		BufferedImage d18 = DisplayObject.readImage("NarutoSpecial1.png");
		images.add(d18);
		BufferedImage d19 = DisplayObject.readImage("NarutoSpecial2.png");
		images.add(d19);
		BufferedImage d20 = DisplayObject.readImage("NarutoSpecial3.png");
		images.add(d20);
		BufferedImage d21 = DisplayObject.readImage("NarutoSpecial4.png");
		images.add(d21);
		BufferedImage d22 = DisplayObject.readImage("NarutoSpecial5.png");
		images.add(d22);
		BufferedImage d23 = DisplayObject.readImage("NarutoSpecial6.png");
		images.add(d23);
		BufferedImage d24 = DisplayObject.readImage("NarutoSpecial7.png");
		images.add(d24);
		BufferedImage d25 = DisplayObject.readImage("NarutoSpecial8.png");
		images.add(d25);
		BufferedImage d26 = DisplayObject.readImage("NarutoBall1.png");
		images.add(d26);
		BufferedImage d27 = DisplayObject.readImage("NarutoBall2.png");
		images.add(d27);
		BufferedImage d28 = DisplayObject.readImage("NarutoBall3.png");
		images.add(d28);
		BufferedImage d29 = DisplayObject.readImage("NarutoBall4.png");
		images.add(d29);

		this.setImages(images);
	}
	
	@Override
	public void rangedAttack()
	{
		if(!shooting)
		{
			this.animate(5);
				//new RangedAttack(this);
				//this.setDefaultHitbox();
		}
		/*rCool -= 1;
		if (rCool == 0){
			new RangedAttack(this);
			rCool = rangedPrep;
		}*/

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
		/*sCool -= 1;
		if (sCool == 0){
			new RangedAttack(this);
			sCool = specialPrep;
		}*/
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
						this.setCurrentFrame(25);
						this.setStartIndex(25);
						this.setEndIndex(28);
						break;
				//running
				case 2: this.setLatency(10);
						this.setSpeed(3);
						this.setCurrentFrame(1);
						this.setStartIndex(1);
						this.setEndIndex(6);
						this.hitting = false;
						this.specialing = false;
						break;
				//jumping
				case 3: this.setVelocity_y(-300);
						this.jumping = true;
						this.setStartIndex(10);
						this.setCurrentFrame(10);
						this.setEndIndex(10);
						break;
				//melee attack
				case 4: this.setLatency(10);
						this.hitting = true;
						this.setStartIndex(7);
						this.setCurrentFrame(7);
						this.setEndIndex(9);
						//this.shooting = false;
						break;
				//ranged attack
				case 5: this.setLatency(10);
						this.shooting = true;
						this.setStartIndex(11);
						this.setCurrentFrame(11);
						this.setEndIndex(16);
						break;
				case 6: this.setLatency(10); // special
						this.specialing = true;
						this.setStartIndex(17);
						this.setCurrentFrame(17);
						this.setEndIndex(24);
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
					rCool = rangedPrep;
					shooting = false;
				}

				anyControllerButton =true;
			}

			if(player1.isButtonPressed(player1.BUTTON_TRIANGLE) && specialing) {
				sCool -= 1;
				if (sCool == 0){
					new RangedAttack(this);
					this.stopSpecial = true;
					sCool = specialPrep;
					specialing = false;
				}
				anyControllerButton =true;
			}
			
			
		}

		
		if (!specialing && ((!anyControllerButton && myControllerIndex != -1) || (pressedKeys.isEmpty() &&  myControllerIndex == -1))){
			this.setCurrentFrame(0);
			this.setImage(images.get(0));
		}
		while(!keysPressed.empty() && myControllerIndex == -1){
			String key = keysPressed.pop();
			String shift = KeyEvent.getKeyText(KeyEvent.VK_BACK_SLASH);
			String space = KeyEvent.getKeyText(KeyEvent.VK_SPACE);
			if (key.equals(shift) && shooting){
				rCool -= 1;
				if (rCool == 0){
					new RangedAttack(this);
					rCool = rangedPrep;
					shooting = false;
				}
			}
			else if (key.equals(space) && specialing){
				sCool -= 1;
				if (sCool == 0){
					new NarutoSpecialAttack(this);
					specialCount++;
					if (specialCount == 3){
						this.stopSpecial = true;
					}
					sCool = specialPrep;
					specialing = false;
				}
			}
			else {
				rCool = rangedPrep;
				sCool = specialPrep;
			}
			
		}

		/*if (specialing){
			sCool -= 1;
			if (sCool == 0){
				new RangedAttack(this);
				sCool = specialPrep;
				specialing = false;
			}
		}*/


	}
	@Override
	public void resetAnimation()
	{
			super.resetAnimation();
			//this.setCurrentFrame(2);
		

	}
	
	@Override
	public BufferedImage getImage()
	{	
		return super.getImage();
		
	}
	

	
	

}
