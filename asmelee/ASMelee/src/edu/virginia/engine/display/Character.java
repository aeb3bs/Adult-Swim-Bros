package edu.virginia.engine.display;

import java.awt.image.BufferedImage;

public class Character extends PhysicsSprite {
	boolean jumping;
	boolean hitting;
	int animationMode;
	
	public Character(String id, boolean onlineSprite) {
		super(id, onlineSprite);
	}
	
	@Override
	public void resetAnimation()
	{
		super.resetAnimation();
		this.hitting = false;
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
