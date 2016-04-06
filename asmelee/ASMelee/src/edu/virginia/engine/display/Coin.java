package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.events.IEventDispatcher;

public class Coin extends AnimatedSprite {
	public Coin(String id) {
		
		super(id, false);
		this.setCurrentFrame(0);
		this.setStartIndex(0);
		this.setEndIndex(3);
		this.setLatency(10);
		
		ArrayList<BufferedImage>images = new ArrayList<BufferedImage>();
		BufferedImage s1 = DisplayObject.readImage("coin_spinning_1.png");
		images.add(s1);
		BufferedImage s2 = DisplayObject.readImage("coin_spinning_2.png");
		images.add(s2);
		BufferedImage s3 = DisplayObject.readImage("coin_spinning_3.png");
		images.add(s3);
		BufferedImage s4 = DisplayObject.readImage("coin_spinning_4.png");
		images.add(s4);
		
		this.setImages(images);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		BufferedImage currentImage = this.getImage();
		this.setImage(currentImage);
	}
}
