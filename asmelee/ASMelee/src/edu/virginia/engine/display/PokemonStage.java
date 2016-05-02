package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.main.Main;


public class PokemonStage extends Stage{

	public PokemonStage()
	{
		super("pokemon_stage");
	}
	@Override
	public void setUp() {
		background = new DisplayObjectContainer("background","pokemon_background.jpg");
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		
		Platform p1 = new Platform("Platform1");
		p1.setPosition(new Point(160,375));
		p1.setVisible(false);
		p1.setScaleX(3.1);
		
		Platform p2= new Platform("Platform2");
		p2.setPosition(new Point(300,260));
		p2.setVisible(false);
		p2.setScaleX(.5);
		
		Platform p3= new Platform("Platform2");
		p3.setPosition(new Point(600,260));
		p3.setVisible(false);
		p3.setScaleX(.5);
		
		platforms.add(p1);
		platforms.add(p2);
		platforms.add(p3);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
		
		
		
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
	}
}
