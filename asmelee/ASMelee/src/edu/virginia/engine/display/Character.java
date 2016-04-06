package edu.virginia.engine.display;

import java.awt.image.BufferedImage;

public class Character extends PhysicsSprite {
	boolean jumping;
	boolean hitting;
	boolean specialing;
	boolean shooting;
	int animationMode;
	public HealthBar healthbar;
	
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
		super.resetAnimation();
		this.hitting = false;
		this.shooting = false;
		this.setStartIndex(0);
		this.setCurrentFrame(0);
		this.setEndIndex(0);
		BufferedImage image = this.getImage();
		this.setImage(image);
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
}
