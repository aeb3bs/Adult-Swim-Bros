package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimatedSprite extends Sprite {
	
	ArrayList<BufferedImage>images;
	HashMap<BufferedImage,Integer>imagemap;
	int currentFrame;
	int startIndex;
	int endIndex;
	int latency;
	double speed;
	
	public int getLatency() {
		return latency;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/*
	 * Parameters -
	 * width: width of each individual sprite
	 * height: height of each individual sprite
	 * rows: # of rows in the sprite sheet
	 * cols: # of cols in the sprite sheet
	 * filename: name of sprite sheet
	 */
	public ArrayList<BufferedImage>readSpriteSheet(int width, int height, int rows, int cols, String filename)
	{
		ArrayList<BufferedImage>spritesheetarray = new ArrayList<BufferedImage>();
		BufferedImage spritesheet = DisplayObject.readImage(filename);
		for(int r=0;r<rows;r++)
		{
			for(int c=0;c<cols;c++)
			{
				BufferedImage image = spritesheet.getSubimage(width*c, height*r, width, height);
				spritesheetarray.add(image);
			}
		}
		return spritesheetarray;
	}

	public AnimatedSprite(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		this.imagemap = new HashMap<BufferedImage, Integer>();
	}
	
	public AnimatedSprite(String id, ArrayList<BufferedImage>images, boolean onlineSprite) {
		super(id, onlineSprite);
		this.images = images;
		this.imagemap = new HashMap<BufferedImage, Integer>();
		latency = 1;
	}

	public HashMap<BufferedImage, Integer> getImagemap() {
		return imagemap;
	}

	public void setImagemap(HashMap<BufferedImage, Integer> imagemap) {
		this.imagemap = imagemap;
	}

	public double getSpeed() {
		return speed;
	}

	public void setLatency(int latency) {
		this.latency = latency;
		if(images != null && imagemap != null)
		{
			for(int index=0;index<images.size();index++)
			{
				imagemap.put(images.get(0), latency);
			}
		}
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<BufferedImage> images) {
		this.images = images;
		this.setImage(this.images.get(0));
		currentFrame = 0;
		startIndex = 0;
		endIndex = images.size()-1;
		
		for(int index=0;index<this.getImages().size();index++)
		{
			this.getImagemap().put(this.getImages().get(index), this.getLatency());
		}
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	public BufferedImage getImage() {
		BufferedImage currentImage = this.images.get(currentFrame);
		
		//next image
		if(this.imagemap.get(currentImage)<=0)
		{
			currentFrame++;
			if(this.getCurrentFrame()>this.getEndIndex())
				this.resetAnimation();
			currentImage = this.images.get(currentFrame);
		}
		
		this.getImagemap().put(currentImage, this.getImagemap().get(currentImage)-1);
		
		return currentImage;
	}
	
	public void resetAnimation() {
		//check if walk sequence is to repeat
		this.setCurrentFrame(this.getStartIndex());
		for(int index=0;index<this.getImages().size();index++)
		{
			this.getImagemap().put(this.getImages().get(index), this.getLatency());
		}
	}
}
