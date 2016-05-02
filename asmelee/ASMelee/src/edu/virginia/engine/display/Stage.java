package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.main.Main;

public class Stage extends DisplayObjectContainer{

	public String stageName;
	public DisplayObjectContainer background;
	public Stage()
	{
		super("DefaultStage");
		this.isOverlay = true;
	}
	public Stage(String name)
	{
		super(name);
		stageName = name;
		this.isOverlay = true;
	}
	public void setUp()
{		
		background = new DisplayObjectContainer("background","mario_background_1.png");
		
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		Platform p1 = new Platform("Platform1");
		Platform p2 = new Platform("Platform2");
		Platform p3 = new Platform("Platform3");
		Platform p4 = new Platform("Platform4");
		Platform p5 = new Platform("Platform5");
		Platform p6 = new Platform("Platform6");
		Platform p7 = new Platform("Platform7");
		Platform ground = new Platform("Ground");

		p1.setPosition(new Point(75,300));
		p1.setScaleX(4);
		p2.setPosition(new Point(200,180));
		p3.setPosition(new Point(575,180));
		p4.setPosition(new Point(800,440));
		p5.setPosition(new Point(75,440));
		
		p6.setPosition(new Point(650,570));
		p7.setPosition(new Point(300,570));

		ground.setPosition(new Point(0,675));

		ground.setScaleX(5);
		ground.setVisible(false);
		//ground.setDefaultHitbox();
		
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
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		super.update(pressedKeys, controllers);
	}
}
