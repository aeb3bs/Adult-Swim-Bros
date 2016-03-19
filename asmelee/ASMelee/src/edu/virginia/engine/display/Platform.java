package edu.virginia.engine.display;

import java.awt.image.BufferedImage;

/*
 * This class serves as a support for sprites to counteract gravity.
 */

public class Platform extends DisplayObject {
	public Platform(String id) {
		super(id);
		BufferedImage image = DisplayObject.readImage("platform.png");
		this.setImage(image);
	}
}
