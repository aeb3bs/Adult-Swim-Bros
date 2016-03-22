package edu.virginia.engine.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class HealthBar extends Sprite{
	public int X;
	public int Y;
	public int width = 50;
	public int height = 8;
	private int actualHealth;
	private int visibleHealth;
	
	public int getActualHealth() {
		return actualHealth;
	}
	public int getVisibleHealth() {
		return visibleHealth;
	}
	public void setActualHealth(int actualHealth) {
		this.actualHealth = actualHealth;
	}
	public void setVisibleHealth(int visibleHealth) {
		this.visibleHealth = visibleHealth;
	}
	
	
	public HealthBar(String id, boolean online){
		super(id, online);
		
	}
	public HealthBar(String id, boolean online, int x, int y){
		super(id, online);
		this.X = x;
		this.Y = y;
		
		Sprite outline = new Sprite("outline", "HealthBarOutline.png", false);
		outline.setPosition(new Point(0, 20));
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

}
