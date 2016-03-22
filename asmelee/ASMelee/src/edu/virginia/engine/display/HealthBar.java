package edu.virginia.engine.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class HealthBar extends Sprite{
	public int X;
	public int Y;
	public int width = 50;
	public int height = 8;
	public double actualHealth = 100;
	public double visibleHealth = 100;
	Sprite outline;
	public Sprite greenHealthBar;
	public Sprite redHealthBar;
	
	public Double getActualHealth() {
		return actualHealth;
	}
	public Double getVisibleHealth() {
		return visibleHealth;
	}
	public void setActualHealth(double actualHealth) {
		this.actualHealth = actualHealth;
	}
	public void setVisibleHealth(double visibleHealth) {
		this.visibleHealth = visibleHealth;
	}
	
	
	public HealthBar(String id, boolean online){
		super(id, online);
		
	}
	public HealthBar(String id, boolean online, int x, int y){
		super(id, online);
		this.X = x;
		this.Y = y;
		
		outline = new Sprite("outline", "HealthBarOutline.png", false);
		greenHealthBar = new Sprite("outline", "GreenHealthBar.png", false);
		redHealthBar = new Sprite("outline", "RedHealthBar.png", false);
		outline.setPosition(new Point(0, -20));
		outline.setScaleX(.95);
		greenHealthBar.setPosition(new Point(3, -18));
		redHealthBar.setPosition(new Point(3, -18));
		this.addChild(outline);
		this.addChild(redHealthBar);
		this.addChild(greenHealthBar);
		
	}
	@Override
	public void draw(Graphics g){
		super.draw(g);
		/*Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(this.X, this.Y, this.width, this.height);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.green);
		g2d.fillRect(this.X, this.Y, this.width, this.height);*/
		
	}
	/*@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		//this.greenHealthBar.setScaleX(this.actualHealth/100);
		//this.redHealthBar.setScaleX(this.visibleHealth/100);
	}*/

}
