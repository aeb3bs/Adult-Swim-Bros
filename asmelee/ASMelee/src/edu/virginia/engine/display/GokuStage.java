package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.main.Main;


public class GokuStage extends Stage{

	public GokuStage()
	{
		super("goku_stage");
	}
	@Override
	public void setUp() {
		background = new DisplayObjectContainer("background","GokuStage.jpg");
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		
		Platform ground = new Platform("Ground");
		ground.setPosition(new Point(0,650));
		ground.setVisible(false);
		ground.setScaleX(10);
		
		platforms.add(ground);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
	}
}
