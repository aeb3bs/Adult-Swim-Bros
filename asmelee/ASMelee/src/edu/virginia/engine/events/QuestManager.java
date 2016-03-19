package edu.virginia.engine.events;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.Coin;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.lab1test.LabSixGame;

public class QuestManager implements IEventListener {
	@Override
	public void handleEvent(Event event) {
		System.out.println("Quest is complete!");
		if(event.getSource() instanceof Coin)
		{
			Coin temp = (Coin) event.getSource();
			LabSixGame.sm.StopMusic("Theme Song");
			try {
				LabSixGame.sm.PlayMusic("Victory Song", false);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Tween coinLinearTween = new Tween(temp, new TweenTransitions(transitiontype.x2trans), "InitialTweenCoin");
			coinLinearTween.animate(TweenableParam.POSITIONX, 350, 250, 2000);
			coinLinearTween.animate(TweenableParam.POSITIONY, 25, 150, 2000);
			coinLinearTween.animate(TweenableParam.SCALEX, 1.0, 4.0, 2000);
			coinLinearTween.animate(TweenableParam.SCALEY, 1.0, 4.0, 2000);
		}
	}
}
