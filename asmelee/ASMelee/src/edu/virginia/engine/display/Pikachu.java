package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.engine.tweening.TweenableParam;

public class Pikachu extends Character {
	public Pikachu(String id, boolean onlineSprite) {
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
		
		//running
				this.animRestart[2]=true;
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage d1 = DisplayObject.readImage("pikachu_walking_1.png");
		images.add(d1);
		BufferedImage d2 = DisplayObject.readImage("pikachu_walking_2.png");
		images.add(d2);
		BufferedImage d3 = DisplayObject.readImage("pikachu_walking_3.png");
		images.add(d3);
		BufferedImage d4 = DisplayObject.readImage("pikachu_walking_4.png");
		images.add(d4);
		BufferedImage d5 = DisplayObject.readImage("pikachu_melee_1.png");
		images.add(d5);
		BufferedImage d6 = DisplayObject.readImage("pikachu_melee_2.png");
		images.add(d6);
		BufferedImage d7 = DisplayObject.readImage("pikachu_melee_3.png");
		images.add(d7);
		BufferedImage d8 = DisplayObject.readImage("pikachu_melee_4.png");
		images.add(d8);
		BufferedImage d9 = DisplayObject.readImage("pikachu_range_1.png");
		images.add(d9);
		BufferedImage d10 = DisplayObject.readImage("pikachu_range_2.png");
		images.add(d10);
		BufferedImage d11 = DisplayObject.readImage("pikachu_range_3.png");
		images.add(d11);
		BufferedImage d12 = DisplayObject.readImage("pikachu_special_1.png");
		images.add(d12);
		BufferedImage d13 = DisplayObject.readImage("pikachu_special_2.png");
		images.add(d13);
		BufferedImage d14 = DisplayObject.readImage("pikachu_special_3.png");
		images.add(d14);
		BufferedImage d15 = DisplayObject.readImage("pikachu_jumping_1.png");
		images.add(d15);
		BufferedImage d16 = DisplayObject.readImage("pikachu_jumping_2.png");
		images.add(d16);
		BufferedImage d17 = DisplayObject.readImage("pikachu_jumping_3.png");
		images.add(d17);
		BufferedImage d18 = DisplayObject.readImage("pikachu_jumping_4.png");
		images.add(d18);
		
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
						this.setStartIndex(0);
						this.setCurrentFrame(0);
						this.setEndIndex(3);
						break;
				//running
				case 2: this.setLatency(5);
						this.setSpeed(3);
						this.setStartIndex(0);
						this.setCurrentFrame(0);
						this.setEndIndex(3);
						this.hitting = false;
						break;
				//jumping
				case 3: this.setVelocity_y(-300);
						this.jumping = true;
						this.setStartIndex(14);
						this.setCurrentFrame(14);
						this.setEndIndex(17);
						break;
				//melee attack
				case 4: this.setLatency(10);
						this.hitting = true;
						this.setStartIndex(4);
						this.setCurrentFrame(4);
						this.setEndIndex(7);
						//this.shooting = false;
						break;
				//ranged attack
				case 5: this.setLatency(10);
						this.shooting = true;
						this.setStartIndex(8);
						this.setCurrentFrame(8);
						this.setEndIndex(10);
						break;
				case 6: this.setLatency(30);
						this.specialing = true;
						this.setStartIndex(11);
						this.setCurrentFrame(11);
						this.setEndIndex(13);
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
		if(this.currentFrame==11 && this.specialing)
		{
			LightningTower lightning_tower = new LightningTower("lightning_tower", false, this);
			lightning_tower.owner = this;
			
			lightning_tower.setScaleX(.4);
			lightning_tower.setScaleY(lightning_tower.getScaleY()*-1*2.0);
			
			int yoffset = (int) (this.getUnscaledHeight()*this.getScaleY());
			int xoffset = (int) (this.getUnscaledWidth()*this.getScaleX());
			
			if(this.getScaleX()<0)
			{
				xoffset+=74;
				yoffset-=30;
			}
			else
			{
				xoffset-=40;
				yoffset-=30;
			}
			
			Point p = new Point((int)this.getPosition().getX()+xoffset, (int)this.getPosition().getY()+yoffset);
			lightning_tower.setPosition(p);
			Tween linearTween = new Tween(lightning_tower, new TweenTransitions(transitiontype.lineartrans), "FadeOutLightning");
			linearTween.animate(TweenableParam.ALPHA, 1.0, 0.0,1500);
			
			this.getParent().addChild(lightning_tower);
		}
	}
}
