package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.main.Main;


public class NarutoStage extends Stage{

	public NarutoStage()
	{
		super("naruto_stage");
	}
	@Override
	public void setUp() {
		background = new DisplayObjectContainer("background", "NarutoStage.png");
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		
		Platform p1 = new Platform("Platform1", "WoodenPlatform.png");
		p1.setPosition(new Point(100, 470));
		//p1.setVisible(false);
		p1.setScaleX(.5);
		
		Platform p2= new Platform("Platform2", "WoodenPlatform.png");
		p2.setPosition(new Point(560, 470));
		//p2.setVisible(false);
		p2.setScaleX(.5);
		
		Platform p3= new Platform("Platform2", "WoodenPlatform.png");
		p3.setPosition(new Point(330,340));
		//p3.setVisible(false);
		p3.setScaleX(.5);
		

		Platform ground = new Platform("Ground");
		ground.setPosition(new Point(0,600));
		//ground.setVisible(false);
		ground.setScaleX(5);
		
		platforms.add(p1);
		platforms.add(p2);
		platforms.add(p3);
		platforms.add(ground);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
	}
}
