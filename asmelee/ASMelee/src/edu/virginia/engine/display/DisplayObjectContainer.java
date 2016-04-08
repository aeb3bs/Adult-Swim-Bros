package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.main.Main;

public class DisplayObjectContainer extends DisplayObject {

	protected ArrayList<DisplayObjectContainer>children;
	
	public DisplayObjectContainer(String id) {
		super(id);
		children = new ArrayList<DisplayObjectContainer>();
	}
	
	public DisplayObjectContainer(String id, String fileName) {
		super(id, fileName);
		children = new ArrayList<DisplayObjectContainer>();
	}

	public ArrayList<DisplayObjectContainer> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<DisplayObjectContainer> children) {
		this.children = children;
		for(DisplayObject o:children)
		{
			o.setParent(this);
		}
	}
	
	public void addChild(DisplayObjectContainer o)
	{
		children.add(o);
		o.setParent(this);
		Main.getAllchildren().add(o);
	}
	
	public void addChildAtIndex(int index, DisplayObjectContainer o)
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
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		super.update(pressedKeys, controllers);
		ArrayList<DisplayObjectContainer> tmp = new ArrayList<DisplayObjectContainer>();
		tmp.addAll(this.children);
		for(DisplayObjectContainer o: tmp)
		{
			o.update(pressedKeys,controllers);
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
