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
		this.setPosition(new Point(0,0));
		background = new DisplayObjectContainer("background","mario_background_1.png");
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
		//this.getParent().addChild(background);
		//this.setImage(DisplayObject.readImage("mario_background_1.png"));
		//background = new DisplayObjectContainer("Background1");
		ArrayList<Platform>platforms = new ArrayList<Platform>();
		Platform p1 = new Platform("Platform1");
		Platform p2 = new Platform("Platform2");
		Platform p3 = new Platform("Platform3");
		Platform p4 = new Platform("Platform4");
		Platform ground = new Platform("Ground");
		

		p1.setPosition(new Point(15,240));
		p2.setPosition(new Point(275,180));
		p3.setPosition(new Point(15,120));
		p4.setPosition(new Point(275,60));

		p4.setPosition(new Point(275,0));
		ground.setPosition(new Point(0,325));

		ground.setScaleX(2.5);
		ground.setVisible(true);
		//ground.setDefaultHitbox();
		
		platforms.add(p1);
		platforms.add(p2);
		platforms.add(p3);
		platforms.add(p4);
		platforms.add(ground);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
	}
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		super.update(pressedKeys, controllers);
	}
}