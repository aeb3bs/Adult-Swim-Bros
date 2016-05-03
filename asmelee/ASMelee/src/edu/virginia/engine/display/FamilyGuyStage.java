package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.main.Main;


public class FamilyGuyStage extends Stage{

	public FamilyGuyStage()
	{
		super("familyguy_stage");
	}
	@Override
	public void setUp() {
		background = new DisplayObjectContainer("background","familyguy_background.jpg");
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		
		Platform p1 = new Platform("Ground");
		p1.setPosition(new Point(0,610));
		p1.setVisible(false);
		p1.setScaleX(5.0);
		
		Platform p2= new Platform("Platform1");
		p2.setPosition(new Point(750,490));
		p2.setVisible(false);
		p2.setScaleX(.95);
		
		Platform p3= new Platform("Platform2");
		p3.setPosition(new Point(715,400));
		p3.setVisible(false);
		p3.setScaleX(1.0);
		
		Platform p4= new Platform("Platform3");
		p4.setPosition(new Point(580,365));
		p4.setVisible(false);
		p4.setScaleX(.4);
		
		Platform p5= new Platform("Platform4");
		p5.setPosition(new Point(270,305));
		p5.setVisible(false);
		p5.setScaleX(.3);
		
		Platform p6= new Platform("Platform6");
		p6.setPosition(new Point(358,220));
		p6.setVisible(false);
		p6.setScaleX(.3);
		
		Platform p7= new Platform("Platform6");
		p7.setPosition(new Point(465,305));
		p7.setVisible(false);
		p7.setScaleX(.3);
		
		platforms.add(p1);
		platforms.add(p2);
		platforms.add(p3);
		platforms.add(p4);
		platforms.add(p5);
		platforms.add(p6);
		platforms.add(p7);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
		
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
	}
}
