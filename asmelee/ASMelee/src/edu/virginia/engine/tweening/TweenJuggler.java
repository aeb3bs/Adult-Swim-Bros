package edu.virginia.engine.tweening;

import java.util.ArrayList;
import java.util.HashSet;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.TweenEvent;
import edu.virginia.engine.events.TweenEventManager;

public class TweenJuggler {
	public static ArrayList<Tween>tweens = new ArrayList<Tween>();
	private static TweenJuggler instance = new TweenJuggler();
	public HashSet<DisplayObject>tweenobjects = new HashSet<DisplayObject>();
	public static TweenEventManager tem = new TweenEventManager();
	public HashSet<DisplayObject>getTweenObjects()
	{
		return tweenobjects;
	}
	public void addTween(Tween tween)
	{
		tweens.add(tween);
		tweenobjects.add(tween.object);
		tween.addEventListener(tem, TweenEvent.TWEEN_COMPLETE_EVENT);
	}
	public void nextFrame()
	{
		for(int index=tweens.size()-1;index>=0;index--)
		{
			Tween temp = tweens.get(index);
			if(temp.isComplete())
			{
				tweens.remove(index);
				tweenobjects.remove(temp.object);
				TweenEvent te = new TweenEvent(temp);
				temp.dispatchEvent(te);
				temp.removeEventListener(tem, TweenEvent.TWEEN_COMPLETE_EVENT);
			}
			else
				temp.update();
		}
	}
	public static TweenJuggler getInstance()
	{
		return instance;
	}
}
