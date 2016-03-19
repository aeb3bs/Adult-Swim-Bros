package edu.virginia.engine.tweening;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.EventDispatcher;

public class Tween extends EventDispatcher{
	DisplayObject object;
	TweenTransitions transition;
	ArrayList<TweenParam>tweenparams;
	double startTime;
	String id;
	public String getId()
	{
		return id;
	}
	public DisplayObject getObject() {
		return object;
	}
	public void setObject(DisplayObject object) {
		this.object = object;
	}
	public Tween(DisplayObject object, String id)
	{
		this.object=object;
		this.id=id;
		tweenparams = new ArrayList<TweenParam>();
		startTime = Calendar.getInstance().getTimeInMillis();
		TweenJuggler.getInstance().addTween(this);
	}
	public Tween(DisplayObject object, TweenTransitions transition, String id)
	{
		this.object=object;
		this.transition=transition;
		this.id=id;
		tweenparams = new ArrayList<TweenParam>();
		startTime = Calendar.getInstance().getTimeInMillis();
		TweenJuggler.getInstance().addTween(this);
	}
	public void animate(TweenableParam fieldToAnimate, double startVal, double endVal, double time)
	{
		TweenParam temp = new TweenParam(fieldToAnimate, startVal, endVal, time);
		tweenparams.add(temp);
	}
	public void update()
	{
		double deltaT = Calendar.getInstance().getTimeInMillis() - startTime;
		for(TweenParam tp:tweenparams)
		{
			double percentDoneTime = deltaT/tp.getTime()*100;
			if(percentDoneTime>100)
				percentDoneTime = 100;
			double percentDoneValue = this.transition.applyTransition(percentDoneTime);
			double delta = tp.getEndVal() - tp.getStartVal();
			this.setValue(tp.getParam(), tp.getStartVal()+percentDoneValue*delta);
		}
	}
	public boolean isComplete()
	{
		for(TweenParam tp:tweenparams)
		{
			if(this.startTime+tp.getTime()>Calendar.getInstance().getTimeInMillis())
			{
				return false;
			}
		}
		return true;
	}
	public void setValue(TweenableParam param, double value)
	{
		if(param == TweenableParam.ALPHA)
		{
			this.object.setAlpha((float)value);
		}
		if(param == TweenableParam.POSITIONX)
		{
			Point newPoint = new Point((int)value, (int)this.object.getPosition().getY());
			this.object.setPosition(newPoint);
		}
		if(param == TweenableParam.POSITIONY)
		{
			Point newPoint = new Point((int)this.object.getPosition().getX(),(int)value);
			this.object.setPosition(newPoint);
		}
		if(param == TweenableParam.SCALEX)
		{
			this.object.setScaleX(value);
		}
		if(param == TweenableParam.SCALEY)
		{
			this.object.setScaleY(value);
		}
	}
}
