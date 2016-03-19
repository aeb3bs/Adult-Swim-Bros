package edu.virginia.engine.tweening;

public class TweenTransitions {
	public enum transitiontype{
		lineartrans,
		x2trans
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
		return Double.NaN;
	}
	public double lineartrans(double percentDone)
	{
		return percentDone/100;
	}
	public double x2trans(double percentDone)
	{
		return Math.pow(percentDone, 2)/Math.pow(100, 2);
	}
}
