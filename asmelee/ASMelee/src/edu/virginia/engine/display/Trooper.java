package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.engine.tweening.TweenableParam;

public class Trooper extends Character {
	public Trooper(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.setCurrentFrame(0);
		this.setStartIndex(0);
		this.setEndIndex(8);
		this.setLatency(10);
		this.setAnimationMode(0);
		this.jumping = false;
		this.hitting = false;
		this.shooting = false;
		myControllerIndex = 0;
		specialCooldown = 100;
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage d1 = DisplayObject.readImage("clone_trooper_walking_1.png");
		images.add(d1);
		BufferedImage d2 = DisplayObject.readImage("clone_trooper_walking_2.png");
		images.add(d2);
		BufferedImage d3 = DisplayObject.readImage("clone_trooper_walking_3.png");
		images.add(d3);
		BufferedImage d4 = DisplayObject.readImage("clone_trooper_walking_4.png");
		images.add(d4);
		BufferedImage d5 = DisplayObject.readImage("clone_trooper_walking_5.png");
		images.add(d5);
		BufferedImage d6 = DisplayObject.readImage("clone_trooper_walking_6.png");
		images.add(d6);
		BufferedImage d7 = DisplayObject.readImage("clone_trooper_walking_7.png");
		images.add(d7);
		BufferedImage d8 = DisplayObject.readImage("clone_trooper_walking_8.png");
		images.add(d8);
		BufferedImage d9 = DisplayObject.readImage("clone_trooper_walking_9.png");
		images.add(d9);
		BufferedImage d10 = DisplayObject.readImage("clone_trooper_range_1.png");
		images.add(d10);
		BufferedImage d11 = DisplayObject.readImage("clone_trooper_range_2.png");
		images.add(d11);
		BufferedImage d12 = DisplayObject.readImage("clone_trooper_range_3.png");
		images.add(d12);
		BufferedImage d13 = DisplayObject.readImage("clone_trooper_range_4.png");
		images.add(d13);
		BufferedImage d14 = DisplayObject.readImage("clone_trooper_range_5.png");
		images.add(d14);
		BufferedImage d15 = DisplayObject.readImage("clone_trooper_range_6.png");
		images.add(d15);
		BufferedImage d16 = DisplayObject.readImage("clone_trooper_range_7.png");
		images.add(d16);
		BufferedImage d17 = DisplayObject.readImage("clone_trooper_special_1.png");
		images.add(d17);
		BufferedImage d18 = DisplayObject.readImage("clone_trooper_special_2.png");
		images.add(d18);
		BufferedImage d19 = DisplayObject.readImage("clone_trooper_special_3.png");
		images.add(d19);
		BufferedImage d20 = DisplayObject.readImage("clone_trooper_special_4.png");
		images.add(d20);
		BufferedImage d21 = DisplayObject.readImage("clone_trooper_jumping_1.png");
		images.add(d21);
		BufferedImage d22 = DisplayObject.readImage("clone_trooper_jumping_2.png");
		images.add(d22);
		BufferedImage d23 = DisplayObject.readImage("clone_trooper_melee_1.png");
		images.add(d23);
		BufferedImage d24 = DisplayObject.readImage("clone_trooper_melee_2.png");
		images.add(d24);
		BufferedImage d25 = DisplayObject.readImage("clone_trooper_melee_3.png");
		images.add(d25);
		BufferedImage d26 = DisplayObject.readImage("clone_trooper_melee_4.png");
		images.add(d26);
		BufferedImage d27 = DisplayObject.readImage("clone_trooper_melee_5.png");
		images.add(d27);
		BufferedImage d28 = DisplayObject.readImage("clone_trooper_melee_6.png");
		images.add(d28);
		BufferedImage d29 = DisplayObject.readImage("clone_trooper_melee_7.png");
		images.add(d29);
		BufferedImage d30 = DisplayObject.readImage("clone_trooper_melee_8.png");
		images.add(d30);
		BufferedImage d31 = DisplayObject.readImage("clone_trooper_melee_9.png");
		images.add(d31);
		BufferedImage d32 = DisplayObject.readImage("clone_trooper_melee_10.png");
		images.add(d32);
		BufferedImage d33 = DisplayObject.readImage("clone_trooper_melee_11.png");
		images.add(d33);
		BufferedImage d34 = DisplayObject.readImage("clone_trooper_melee_12.png");
		images.add(d34);
		BufferedImage d35 = DisplayObject.readImage("clone_trooper_melee_13.png");
		images.add(d35);

		
		this.setImages(images);
	}
	@Override
	public void animate(int mode)
	{
		if(this.getAnimationMode()!=mode)
		{
			this.resetAnimation();
			switch(mode){
				//walking
				case 1: this.setLatency(10);
						this.setSpeed(1.5);
						this.setEndIndex(8);
						break;
				//running
				case 2: this.setLatency(5);
						this.setSpeed(3);
						this.setEndIndex(8);
						break;
				//jumping
				case 3: this.setVelocity_y(-300);
						this.jumping = true;
						this.setStartIndex(20);
						this.setCurrentFrame(20);
						this.setEndIndex(21);
						break;
				//melee attack
				case 4: this.setLatency(10);
						this.hitting = true;
						this.setStartIndex(22);
						this.setCurrentFrame(22);
						this.setEndIndex(34);
						//this.shooting = false;
						break;
				//ranged attack
				case 5: this.setLatency(10);
						this.shooting = true;
						this.setStartIndex(9);
						this.setCurrentFrame(9);
						this.setEndIndex(15);
						break;
				case 6: this.setLatency(30);
						this.specialing = true;
						this.setStartIndex(16);
						this.setCurrentFrame(16);
						this.setEndIndex(19);
						break;
			}
			BufferedImage currentImage = this.getImage();
			this.setImage(currentImage);
		}
		this.setAnimationMode(mode);
	}
	@Override
	public void rangedAttack()
	{
		if(!shooting)
		{
			this.animate(5);
			new RangedAttack(this);
			//this.setDefaultHitbox();
		}
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
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers)
	{
		super.update(pressedKeys, controllers);
		if(this.currentFrame==19)
		{
			LightningTower lightning_tower = new LightningTower("lightning_tower", false);
			lightning_tower.owner = this;
			
			lightning_tower.setScaleX(.25);
			lightning_tower.setScaleY(lightning_tower.getScaleY()*-1);
			
			int yoffset = (int) (this.getUnscaledHeight()*this.getScaleY());
			int xoffset = (int) (this.getUnscaledWidth()*this.getScaleX())+30;
			
			if(this.getScaleX()<0)
				xoffset+=14;
			
			Point p = new Point((int)this.getPosition().getX()+xoffset, (int)this.getPosition().getY()+yoffset);
			lightning_tower.setPosition(p);
			Tween linearTween = new Tween(lightning_tower, new TweenTransitions(transitiontype.lineartrans), "FadeOutLightning");
			linearTween.animate(TweenableParam.ALPHA, 1.0, 0.0,1500);
			
			this.getParent().addChild(lightning_tower);
		}
	}
}
