package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.w3c.dom.css.Rect;

import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.main.Main;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher {
	
	/* used for accessing parent */
	private DisplayObjectContainer parent;

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	protected BufferedImage displayImage;
	
	/* Properties */
	
	//should be true iff this display object is meant to be drawn
	boolean visible;
	//should describe the x,y position where this object will be drawn
	Point position;
	//The point, relative to the upper left corner of the image that is the origin of this object
	Point pivotPoint;
	//scales the image up or down. 1.0 would be actual size.
	double scaleX;
	double scaleY;
	//defines the amount (in radians, your choice) to rotate this object
	double rotation;
	//defines how transparent to draw this object.
	float alpha;
	
	Rectangle hitbox;
	ArrayList<Rectangle> hitboxes;
	HashMap<BufferedImage, ArrayList<Rectangle>> boxMap = new HashMap<BufferedImage, ArrayList<Rectangle>>();
	public boolean collidesWith(DisplayObject other)
	{
		if(boxMap.size() == 0 || other.boxMap.size() ==0)
			return this.getHitboxGlobal().intersects(other.getHitboxGlobal());//This is sorta just a bandaid, may need to re-eval later
		hitboxes = boxMap.get(this.displayImage);
		other.hitboxes = other.boxMap.get(other.displayImage);
		for(Rectangle h: hitboxes)
			for(Rectangle g: other.hitboxes)
				if(this.getHitboxGlobal(h).intersects(other.getHitboxGlobal(h)))
					return true;
		return false;
	}
	
	public void setDefaultHitbox()
	{
		Rectangle hitbox = new Rectangle();
		hitbox.x = (int) this.getPosition().getX();
		hitbox.y = (int) this.getPosition().getY();
		this.hitbox = hitbox;
		
		hitbox.height = (int) (this.getDisplayImage().getHeight()*Math.abs(this.getScaleY()));
		hitbox.width = (int) (this.getDisplayImage().getWidth()*Math.abs(this.getScaleX()));
	}
	
	public Point getGlobalPosition()
	{
		if(this.getParent() == null)
		{
			return this.getPosition();
		}
		else
		{
			Point parentPosition = new Point();
			parentPosition.x = (int) this.getParent().getGlobalPosition().getX();
			parentPosition.y = (int) this.getParent().getGlobalPosition().getY();
			parentPosition.x += this.getPosition().x;
			parentPosition.y += this.getPosition().y;
			return parentPosition;
		}
	}
	
	public Rectangle getHitbox()
	{
		return this.hitbox;
	}
	public Rectangle getHitboxGlobal()
	{
		return this.getHitboxGlobal(hitbox);
	}
	public Rectangle getHitboxGlobal(Rectangle curHitbox)
	{
		Rectangle hitbox = curHitbox;
		if(hitbox != null)
		{
			hitbox.x = (int) this.getGlobalPosition().getX();
			hitbox.y = (int) this.getGlobalPosition().getY();
			return hitbox;
		}
		else
		{
			return null;
		}
	}

	public boolean isVisible() {
		return visible;
	}
	
	public DisplayObjectContainer getParent() {
		return parent;
	}

	public void setParent(DisplayObjectContainer parent) {
		this.parent = parent;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPivotPoint() {
		return pivotPoint;
	}

	public void setPivotPoint(Point pivotPoint) {
		this.pivotPoint = pivotPoint;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
		this.setDefaultHitbox();
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
		this.setDefaultHitbox();
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setDisplayImage(BufferedImage displayImage) {
		this.displayImage = displayImage;
	}

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setPosition(new Point(0,0));
//		this.setDefaultHitbox();
		this.setId(id);
		setDefaults();
	}

	public DisplayObject(String id, String fileName) {
		this.setPosition(new Point(0,0));
//		this.setDefaultHitbox();
		this.setId(id);
		this.setImage(fileName);
		setDefaults();
		if(hitbox == null)
			this.setDefaultHitbox();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setDefaults(){
		visible=true;
		//should describe the x,y position where this object will be drawn
		position=new Point(0,0);
		//The point, relative to the upper left corner of the image that is the origin of this object
		pivotPoint=new Point(0,0);
		//scales the image up or down. 1.0 would be actual size.
		scaleX=1.0;
		scaleY=1.0;
		//defines the amount (in radians, your choice) to rotate this object
		rotation=0.0;
		//defines how transparent to draw this object.
		alpha=(float) 1.0;
		this.parent = null;
	}

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
		ArrayList<Rectangle> allHboxes = new ArrayList<Rectangle>();
		try {
			URL url = Main.class.getResource("/"+imageName.subSequence(0, imageName.length() -3) + "txt");
			String file = (url.getPath());
			System.out.println(url);
			Scanner sc = new Scanner(new File(file));
			
			while(sc.hasNextLine())
			{
				String[] hbox = sc.nextLine().split(" ");
				Rectangle tmp = new Rectangle(
						Integer.parseInt(hbox[0]),
						Integer.parseInt(hbox[1]),
						Integer.parseInt(hbox[2]),
						Integer.parseInt(hbox[3]));
				allHboxes.add(tmp);
			}
			
		} catch (Exception  e) {
			//System.out.println("[Error in DisplayObject.java:readImage] Could not read image hitboxes " + imageName);
			//e.printStackTrace();
		}
		if(hitbox == null){
			this.setDefaultHitbox();
			allHboxes.add(hitbox);
			
		}
		boxMap.put(displayImage, allHboxes);
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public static BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			URL url = Main.class.getResource("/"+imageName);
			String file = (url.getPath());
			System.out.println(url);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
		
		if(hitbox == null || !boxMap.containsKey(image))
			this.setDefaultHitbox();
		else
			hitboxes = boxMap.get(image);
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<String> pressedKeys) {
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null && visible) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
			
			/*
			 *  This line when we need to draw the hitboxes and debug
			 *  DEBUG HITBOX
			 */
//			g2d.drawRect(this.getHitboxGlobal().x, this.getHitboxGlobal().y, this.hitbox.width, this.hitbox.height);
		}
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		
		g2d.rotate(rotation, pivotPoint.x, pivotPoint.y);
		g2d.translate(position.getX(), position.getY());
		g2d.scale(scaleX, scaleY);
		if(this.parent == null || (this.parent instanceof Game))
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2d.scale(1.0/scaleX, 1.0/scaleY);
		g2d.translate(-position.getX(), -position.getY());
		g2d.rotate(-rotation, pivotPoint.x, pivotPoint.y);
	}
}