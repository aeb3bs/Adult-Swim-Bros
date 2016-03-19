package edu.virginia.engine.tweening;

/*
TweenParam
- TweenParam(TweenableParams paramToTween, double startVal, double endVal, double time);
- TweenableParams getParam();
- double getStartVal();
- double getEndVal();
- double getTweenTime();
 */
public class TweenParam {
	private double startVal;
	private double endVal;
	private double time;
	private TweenableParam param;
	
	
	TweenParam(TweenableParam paramToTween, double startVal, double endVal, double time){
		this.param = paramToTween;
		this.startVal=startVal;
		this.endVal=endVal;
		this.time=time;
	}
	
	public TweenableParam getParam(){
		return param;
	}
	public double getStartVal() {
		return startVal;
	}
	public void setStartVal(double startVal) {
		this.startVal = startVal;
	}
	public double getEndVal() {
		return endVal;
	}
	public void setEndVal(double endVal) {
		this.endVal = endVal;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	
}
