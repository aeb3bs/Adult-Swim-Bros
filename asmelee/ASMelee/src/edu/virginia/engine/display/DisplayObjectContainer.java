package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject {

	protected ArrayList<DisplayObject>children;
	
	public DisplayObjectContainer(String id) {
		super(id);
		children = new ArrayList<DisplayObject>();
	}
	
	public DisplayObjectContainer(String id, String fileName) {
		super(id, fileName);
		children = new ArrayList<DisplayObject>();
	}

	public ArrayList<DisplayObject> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<DisplayObject> children) {
		this.children = children;
		for(DisplayObject o:children)
		{
			o.setParent(this);
		}
	}
	
	public void addChild(DisplayObject o)
	{
		children.add(o);
		o.setParent(this);
	}
	
	public void addChildAtIndex(int index, DisplayObject o)
	{
		children.add(index, o);
		o.setParent(this);
	}
	
	//Removes object if found in children. If not, does nothing.
	public void removeChild(DisplayObject o)
	{
		children.remove(o);
	}
	
	public void removeByIndex(int index)
	{
		this.children.remove(index);
	}
	
	public void removeAll()
	{
		this.children.clear();
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (visible) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			if(this.displayImage != null)
				g2d.drawImage(displayImage, 0, 0,
						(int) (getUnscaledWidth()),
						(int) (getUnscaledHeight()), null);
			
			
			for(DisplayObject o:this.children)
			{
				o.draw(g2d);
			}
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}
	
	@Override
	protected void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		for(DisplayObject o: this.children)
		{
			o.update(pressedKeys);
		}
	}
	
	public boolean contains(DisplayObject o)
	{
		return this.children.contains(o);
	}
	
	public DisplayObject getChildById(String id)
	{
		for(DisplayObject o: this.children)
		{
			if(o.getId().equals(id))
			{
				return o;
			}
		}
		return null;
	}
	
	public DisplayObject getChildByIndex(int index)
	{
		if(index>this.children.size()-1 || index<0)
			return null;
		else
			return this.children.get(index);
	}
}
