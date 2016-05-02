package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.main.Main;


public class StarWarsStage extends Stage{

	public StarWarsStage()
	{
		super("starwars_stage");
	}
	@Override
	public void setUp() {
		background = new DisplayObjectContainer("background","star_wars_background.jpg");
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		
		Platform p1 = new Platform("Platform1");
		p1.setPosition(new Point(75,135));
		p1.setVisible(false);
		p1.setScaleX(1.2);
		
		Platform p2= new Platform("Platform2");
		p2.setPosition(new Point(180,375));
		p2.setVisible(false);
		p2.setScaleX(1.2);
		
		Platform p3= new Platform("Platform2");
		p3.setPosition(new Point(685,460));
		p3.setVisible(false);
		p3.setScaleX(.3);
		
		Platform p4= new Platform("Platform2");
		p4.setPosition(new Point(840,350));
		p4.setVisible(false);
		p4.setScaleX(.3);
		
		Platform p5= new Platform("Platform2");
		p5.setPosition(new Point(840,590));
		p5.setVisible(false);
		p5.setScaleX(.3);
		
		Platform p6= new Platform("Platform2");
		p6.setPosition(new Point(555,505));
		p6.setVisible(false);
		p6.setScaleX(.3);
		
		Platform p7= new Platform("Platform2");
		p7.setPosition(new Point(555,265));
		p7.setVisible(false);
		p7.setScaleX(.3);
		
		Platform ground = new Platform("Ground");
		ground.setPosition(new Point(0,650));
		ground.setVisible(false);
		ground.setScaleX(5);
		
		platforms.add(p1);
		platforms.add(p2);
		platforms.add(p3);
		platforms.add(p4);
		platforms.add(p5);
		platforms.add(p6);
		platforms.add(p7);
		platforms.add(ground);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
	}
}
