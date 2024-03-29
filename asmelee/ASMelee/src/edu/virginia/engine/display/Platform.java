package edu.virginia.engine.display;

import java.awt.image.BufferedImage;

/*
 * This class serves as a support for sprites to counteract gravity.
 */

public class Platform extends DisplayObjectContainer {
	public Platform(String id) {
		super(id);
		BufferedImage image = DisplayObject.readImage("platform.png");
		this.setImage(image);
	}
	public Platform(String id, String imagestr) {
		super(id);
		BufferedImage image = DisplayObject.readImage(imagestr);
		this.setImage(image);
	}
}
