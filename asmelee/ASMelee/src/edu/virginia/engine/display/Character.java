package edu.virginia.engine.display;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.tweening.TweenJuggler;

public abstract class Character extends PhysicsSprite {
	boolean jumping;
	boolean hitting;
	boolean specialing;
	boolean shooting;
	int animationMode;
	public HealthBar healthbar;
	protected double moveThreshold = .5;
	protected int myControllerIndex = -1;
	protected int rangedCooldown = 80;
	protected int meleeCooldown = 30;
	protected int specialCooldown = 50;
	protected int rCurCool =0;
	protected int mCurCool =0;
	protected int sCurCool =0;
	
	public Character(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		healthbar = new HealthBar ("healthbar", false, 0, -10);
		this.addChild(healthbar);
	}
	
	public int getAnimationMode() {
		return animationMode;
	}

	public void setAnimationMode(int animationMode) {
		this.animationMode = animationMode;
	}
	
	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	@Override
	public void resetAnimation()
	{
		//special=true;
		super.resetAnimation();
		this.hitting = false;
		this.shooting = false;
		this.specialing = false;
		this.setStartIndex(0);
		this.setCurrentFrame(0);
		this.setEndIndex(0);
		BufferedImage image = this.getImage();
		this.setImage(image);
		this.setAnimationMode(0);
		this.setDefaultHitbox();
	}
	
	public boolean getJumping()
	{
		return jumping;
	}
	
	public boolean getHitting()
	{
		return hitting;
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers)
	{	
		super.update(pressedKeys,controllers);		
		GamePad player1 = null;
		try{
		player1 = controllers.get(myControllerIndex);
		}catch( Exception e)
		{}
		// decrementing cooldowns
		rCurCool -=1;
		sCurCool -=1;
		mCurCool -=1;
		//System.out.println(rCurCool);
		if(TweenJuggler.getInstance().tweenobjects.contains(this)==true)
		{
			return;
		}
		
		if(this.getAnimationMode()==0)
		{
			this.setStartIndex(0);
			this.setCurrentFrame(0);
			this.setEndIndex(3);
			BufferedImage image = this.getImage();
			this.setImage(image);
		}
		
		if(4<=this.getAnimationMode())
		{
			BufferedImage image = this.getImage();
			this.setImage(image);
		}
		
		if(onlineSprite)
		{
			return;
		}
		//holds them in place for special attack andupdates with time
		if(specialing)
		{
			updateSpecial(specialCooldown - sCurCool);
			return;
		}
		
		Stack<String>keysPressed = new Stack<String>();
		for(int index=0; index<pressedKeys.size();index++)
			keysPressed.push(pressedKeys.get(index));
		
		if(player1 != null)
		{//handles gamepad input
			if(player1.isButtonPressed(player1.BUTTON_SQUARE))
				if(rCurCool <=0){
					rangedAttack();
					rCurCool = rangedCooldown;
				}
			if(player1.isButtonPressed(player1.BUTTON_CIRCLE))
				if(mCurCool <=0){
					meleeAttack();
					mCurCool = meleeCooldown;
				}
			if(player1.isButtonPressed(player1.BUTTON_TRIANGLE))
				if(sCurCool <=0){
					specialAttack();
					sCurCool = specialCooldown;
				}
			if(player1.isButtonPressed(player1.BUTTON_CROSS))
				jump();
			if(player1.getLeftStickXAxis() > moveThreshold)
				move(false);
			if(player1.getLeftStickXAxis() < -moveThreshold)
				move(true);
				
		}
		while(!keysPressed.empty() && myControllerIndex == -1)
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
			String shift = KeyEvent.getKeyText(KeyEvent.VK_SHIFT);
			
			if(key.equals(right) || key.equals(keyd))
			{	
				move(false);
			}
			else if(key.equals(left) || key.equals(keya))
			{
				move(true);
			}
			else if(key.equals(keyw))
			{
				jump();
			}
			else if(key.equals(enter))
			{
				if(mCurCool <=0){
					meleeAttack();
					mCurCool = meleeCooldown;
				}
			}
			else if(key.equals(space)){ // Special Attack
				if(sCurCool <=0)
				{
					specialAttack();
					sCurCool = specialCooldown;
				}
			
			}
			else if(key.equals(shift))// Ranged Attack
			{
				if(rCurCool <=0){
					this.rangedAttack();
					rCurCool = rangedCooldown;
				}
			}

		}
	}

	public abstract void animate(int mode);
	public abstract void rangedAttack();
	public abstract void meleeAttack();
	public abstract void specialAttack();
	//not needed, but if special is time dependent is useful
	public void updateSpecial(int framesPassed)
	{
		
	}
	public void jump()
	{
		if(!jumping)
		{
			this.animate(3);	
			BufferedImage currentImage = this.getImage();
			this.setImage(currentImage);
		}
	}
	public void move(boolean leftKey)
	{
		int dir = (leftKey)?-1:1;
		this.animate(2);
		
		//need to change direction instead of movement
		if(this.getStartIndex() != 0 || (dir*this.getScaleX() < 0 /*&& leftKey == false*/))
		{
			this.setStartIndex(0);
			this.setCurrentFrame(0);
			if(dir*this.getScaleX()<0)
			{
				int width = (int) (this.getUnscaledWidth()*Math.abs(this.getScaleX()));
				Point rescaledPosition = new Point((int)this.getPosition().getX()-width*dir, (int)this.getPosition().getY());
				this.setPosition(rescaledPosition);
				this.setScaleX(.5*dir);//@TODO this shouldn't be a set number it should be by character
			}
		}
		else
		{
			//update position
			this.setPosition(new Point(this.getPosition().x+=this.getSpeed()*dir,this.getPosition().y));
			
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
