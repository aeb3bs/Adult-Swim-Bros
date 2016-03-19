package edu.virginia.engine.events;

import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.engine.tweening.TweenableParam;

public class TweenEventManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		if(event.getSource() instanceof Tween)
		{
			Tween temp = (Tween) event.getSource();
			if(temp.getId().equals("InitialTweenCoin"))
			{
				Tween tween = new Tween(temp.getObject(), new TweenTransitions(transitiontype.lineartrans), "CoinFadeOut");
				tween.animate(TweenableParam.ALPHA, 1.0, 0.0, 2000);
			}
		}
	}
}
