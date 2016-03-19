package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;

import edu.virginia.lab1test.LabSixGame;


public class PhysicsSprite extends AnimatedSprite {

	double mass;
	double velocity_x;
	double velocity_y;
	double acceleration_x;
	double acceleration_y;
	long timeOfLastUpdate;
	final double deltaTime = 10;//it will update every 10 milliseconds
	
	
	public PhysicsSprite(String id, boolean onlineSprite) {
		super(id, onlineSprite);
		timeOfLastUpdate = Calendar.getInstance().getTimeInMillis();
		velocity_x=0;
		velocity_y=0;
		acceleration_x=0;
		acceleration_y=0;
		mass = 10;
	}
	
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getVelocity_x() {
		return velocity_x;
	}

	public void setVelocity_x(double velocity_x) {
		this.velocity_x = velocity_x;
	}

	public double getVelocity_y() {
		return velocity_y;
	}

	public void setVelocity_y(double velocity_y) {
		this.velocity_y = velocity_y;
	}

	public double getAcceleration_x() {
		return acceleration_x;
	}

	public void setAcceleration_x(double acceleration_x) {
		this.acceleration_x = acceleration_x;
	}

	public double getAcceleration_y() {
		return acceleration_y;
	}

	public void setAcceleration_y(double acceleration_y) {
		this.acceleration_y = acceleration_y;
	}

	public long getTimeOfLastUpdate() {
		return timeOfLastUpdate;
	}

	public void setTimeOfLastUpdate(long timeOfLastUpdate) {
		this.timeOfLastUpdate = timeOfLastUpdate;
	}

	public double getDeltaTime() {
		return deltaTime;
	}

	@Override
	public void update(ArrayList<String> pressedKeys)
	{
		super.update(pressedKeys);
		
		double deltaT = Calendar.getInstance().getTimeInMillis()-timeOfLastUpdate;
		if(deltaT>deltaTime)
		{
			deltaT /= 1000;
			
			//update position and velocity due to gravity
			int newX = (int) (this.getPosition().getX()+velocity_x*deltaT+(acceleration_x)/mass/2*Math.pow(deltaT, 2));
			int newY = (int) (this.getPosition().getY()+velocity_y*deltaT+(LabSixGame.gravity+acceleration_y)/mass/2*Math.pow(deltaT, 2));
			
			Point newPosition = new Point(newX, newY);
			this.setPosition(newPosition);
			
			velocity_y = velocity_y + (LabSixGame.gravity+acceleration_y)/mass * (deltaT);
			velocity_x = velocity_x + (acceleration_x)/mass * (deltaT);
			
			acceleration_y = 0;
			
			timeOfLastUpdate = Calendar.getInstance().getTimeInMillis();
		}
	}
}
