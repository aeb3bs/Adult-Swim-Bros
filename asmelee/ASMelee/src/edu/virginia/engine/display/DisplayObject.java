package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.main.Main;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher {

	/* All DisplayObject have a unique id */
	public final double friction = .01;
	public final double gravity = -.15;
	public final double drag = .01;
	
	private String id;
	protected boolean visible = true;
	protected Point position = new Point(0,0);
	private Point pivotPoint = new Point(0,0);
	protected double scaleX = (float) 1.0;
	protected double scaleY = (float) 1.0;
	private double rotation = 0;
	private float alpha = 1;
	protected BoundingPolygon bbox;
	protected BoundingPolygon originalBbox;
	private AffineTransform pointTransform;
	private Event myCollisionEvent;
	private DisplayObjectContainer parent;
	
	public boolean hasPhysics = false;
	public boolean isOverlay = false;
	protected double[] accel = new double[2];
	protected double[] velocity = new double[2];
	protected double[] moves = new double[2];

	/* The image that is displayed by this object */
	protected BufferedImage displayImage;
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		pointTransform.translate(-this.position.getX(),-this.position.getY());
		this.position = position;
		pointTransform.translate(this.position.getX(),this.position.getY());
	}

	public Point getPivotPoint() {
		return pivotPoint;
	}

	public void setPivotPoint(Point pivotPoint) {
		Point oldPivot = this.getPivotPoint();
		pointTransform.translate(-oldPivot.getX(),-oldPivot.getY());
		pointTransform.rotate(-this.rotation);
		pointTransform.scale(1.0/this.scaleX, 1.0/this.scaleY);
		this.pivotPoint = pivotPoint;
		pointTransform.scale(this.scaleX, this.scaleY);
		pointTransform.rotate(this.rotation);
		pointTransform.translate(oldPivot.getX(),oldPivot.getY());
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double d) {
		//this.position.x += (int) (this.pivotPoint.x - this.pivotPoint.x*scaleX/this.scaleX);
		//this.pivotPoint.x = (int) (this.pivotPoint.x*scaleX/this.scaleX);
		pointTransform.translate(pivotPoint.getX(),pivotPoint.getY());
		pointTransform.scale(1.0/this.scaleX, 1.0);
		this.scaleX = d;
		pointTransform.scale(d, 1.0);
		pointTransform.translate(-pivotPoint.getX(),-pivotPoint.getY());
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		//this.pivotPoint.y = (int) (this.pivotPoint.y*scaleY/this.scaleY);
		//Point pos = new Point(position);
		//Point pivPos = new Point(pivotPoint);
		//pos.translate(0, (int) (scaleY*pivPos.y));
		//pivPos.translate((int) (scaleY*(pivPos.y)),0);
		pointTransform.translate(pivotPoint.getX(),pivotPoint.getY());
		pointTransform.scale(1.0, 1.0/this.scaleY);
		this.scaleY = scaleY;
		pointTransform.scale(1.0, scaleY);
		pointTransform.translate(-pivotPoint.getX(),-pivotPoint.getY());
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		pointTransform.rotate(-this.rotation,pivotPoint.getX(),pivotPoint.getY());
		this.rotation = rotation;
		pointTransform.rotate(rotation,pivotPoint.getX(),pivotPoint.getY());
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(double d) {
		this.alpha = (float) d;
	}
	
	

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		pointTransform =new AffineTransform();
		initBbox();
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		pointTransform =new AffineTransform();
		this.setImage(fileName);
		initBbox();
	}
	public void updateBbox()
	{
		bbox.clearPoints();
		Point p = new Point();
		for(int i = 0;i<originalBbox.getNumPoints(); i++)
		{
			pointTransform.transform(
					originalBbox.getPoint(i), p);
			bbox.addNextPoint(p.x, p.y);
		}
	}
	
	public void initBbox()
	{
		originalBbox = new BoundingPolygon();
		bbox = new BoundingPolygon();
		Point p = new Point();//getPosition();
		originalBbox.addNextPoint(p.getX(), p.getY());
		originalBbox.addNextPoint(p.getX(),p.getY() + this.getUnscaledHeight()/**this.getScaleY()*/);
		originalBbox.addNextPoint(p.getX() + this.getUnscaledWidth()/**this.getScaleX()*/,
				p.getY() + this.getUnscaledHeight()/**this.getScaleY()*/);
		originalBbox.addNextPoint(p.getX() + this.getUnscaledWidth()/**this.getScaleX()*/,
				p.getY());
		updateBbox();
		/*System.out.println(this.id);
		for(int i = 0;i<4;i++)
			System.out.print(bbox.getPoint(i) + " ");
		System.out.println(this.getUnscaledHeight() +" "+ this.getUnscaledWidth());*/
	}
	public void setCollisionEvent(Event e)
	{
		myCollisionEvent = e;
	}
	public Event getCollisionEvent()
	{
		return myCollisionEvent;
	}
	public boolean collidesWith(DisplayObject other)
	{
		if(isOverlay || other.isOverlay)
			return false;
		
		if(this.bbox.collides(other.bbox) && other.bbox.collides(this.bbox)) {
			//myCollisionEvent.setSource(other);
			//myCollisionEvent.setFlagString("both");
			//this.dispatchEvent(myCollisionEvent);
			//myCollisionEvent.setFlagString("");
			//System.out.println("both");
			return true;
		}else if(this.bbox.collides(other.bbox) ){
			//myCollisionEvent.setSource(other);
			//this.dispatchEvent(myCollisionEvent);
			//System.out.println("this");
			return true;
		}
		else if (other.bbox.collides(this.bbox)){
			//myCollisionEvent.setSource(this);
			//this.dispatchEvent(myCollisionEvent);
			//System.out.println(other.getId() + " collidesWith " + getId());
			return true;
		}
		return false;
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
			parentPosition.x += this.getPosition().x * this.getParent().getScaleX();
			parentPosition.y += this.getPosition().y * this.getParent().getScaleY();
			return parentPosition;
		}
	}
	public DisplayObjectContainer getParent() {
		return parent;
	}

	public void setParent(DisplayObjectContainer parent) {
		this.parent = parent;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public BoundingPolygon getBbox()
	{
		updateBbox();
		return bbox;
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
		initBbox();
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
		//if(image == null) return;
		displayImage = image;
		initBbox();
	}
	


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers){
		hasPhysics = false;
		if(hasPhysics)
		{
			
			Point p =new Point(this.position);
			double x, y;
			x = p.getX();
			y = p.getY();
			
			//accel[1] -=gravity;
			velocity[0] += accel[0] +((velocity[0] > 0)?(-1):1)*((drag*4)*velocity[0]*velocity[0] + friction);
			velocity[1] += (accel[1] - gravity)+((velocity[1] > 0)?(-1):1)*(drag*velocity[1]*velocity[1] + friction);
			//System.out.println(velocity[1] + " "+(accel[1] - gravity) + " " +((velocity[1] > 0)?(-1):1)*drag*velocity[1]*velocity[1]);
			
			x += velocity[0] + ((velocity[0] > 0)?(-1):1)*friction;
			y += velocity[1] + ((velocity[1] > 0)?(-1):1)*friction;
			p.setLocation(x, y);
			this.setPosition(p);
			updateBbox();
			//System.out.println("P: "+p.y /*+ " " +(velocity[0]-((velocity[0] > 0)?(-1):1)*friction)*/);
		}
		updateBbox();
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null && this.visible) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			int rule = AlphaComposite.SRC_OVER;
	        Composite comp = AlphaComposite.getInstance(rule , this.alpha );
	        g2d.setComposite(comp );
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);

			g2d.setColor(new Color(50,0,0));
					
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}
	public void drawBoundingBoxes(Graphics g)
	{
		//System.out.println("really drawing bounding box for: " + this.id);
		//if(this.visible == false)
		//	return;
		Graphics2D g2d = (Graphics2D) g;
		/*g2d.drawRect(0,0, originalBbox.getPoint(2).x - originalBbox.getPoint(0).x,
        		originalBbox.getPoint(2).y - originalBbox.getPoint(0).y);*/
		//g2d.drawRect(bbox.getPoint(0).x, bbox.getPoint(0).y, bbox.getPoint(2).x - bbox.getPoint(0).x,
        //		bbox.getPoint(2).y - bbox.getPoint(0).y);
		g2d.setColor(new Color(0,255,0));
		g2d.drawPolygon(bbox.getXs(), bbox.getYs(), bbox.getNumPoints());
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(position.x,position.y);
		g2d.translate(pivotPoint.x, pivotPoint.y);
		//Would still like to rotate around scaled pivot point maybe
		g2d.rotate(rotation);
		g2d.scale(scaleX, scaleY);
		g2d.translate(-pivotPoint.x, -pivotPoint.y);
		pointTransform = g2d.getTransform();
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.translate(pivotPoint.x, pivotPoint.y);
		g2d.scale(1.0/scaleX, 1.0/scaleY);
		g2d.rotate(-rotation);//, pivotPoint.x, pivotPoint.y);

		g2d.translate(-pivotPoint.x, -pivotPoint.y);
		g2d.translate(-position.x,-position.y);
		
	}

}