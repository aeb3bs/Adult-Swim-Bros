package edu.virginia.engine.tweening;

public class TweenTransitions {
	public enum transitiontype{
		lineartrans,
		x2trans,
		easeOut
}
	private transitiontype tt;
	public TweenTransitions(transitiontype t)
	{
		this.tt = t;
	}
	public double applyTransition(double percentDone)
	{
		if(this.tt==transitiontype.lineartrans)
		{
			return lineartrans(percentDone);
		}
		if(this.tt==transitiontype.x2trans)
		{
			return x2trans(percentDone);
		}
		if(this.tt==transitiontype.easeOut){
			return easeOut(percentDone);
		}
		return Double.NaN;
	}
	public double lineartrans(double percentDone)
	{
		//System.out.println(percentDone/100);
		return percentDone/100;
	}
	public double x2trans(double percentDone)
	{
		return Math.pow(percentDone, 2)/Math.pow(100, 2);
	}
	public double easeOut(double percentDone){
		
		//System.out.println(1 - Math.pow(1-percentDone/100, 2));
		return 1 - Math.pow(1-percentDone/100, 2);
	}
}
