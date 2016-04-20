package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.main.Main;

public class Stage extends DisplayObjectContainer{

	public String stageName;
	public DisplayObjectContainer background;
	public enum stagetype{
		mariobackground,
		starwarsbackground
	}
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
	public void setUp(stagetype s)
	{
		this.setPosition(new Point(0,0));
		if(s == stagetype.mariobackground)
		{
			background = new DisplayObjectContainer("background","mario_background_1.png");
			
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
		if(s == stagetype.starwarsbackground)
		{
			background = new DisplayObjectContainer("background","star_wars_background.jpg");
			ArrayList<Platform>platforms = new ArrayList<Platform>();
			
			Platform p1 = new Platform("Platform1");
			p1.setPosition(new Point(20,80));
			p1.setVisible(false);
			p1.setScaleX(.7);
			
			Platform p2= new Platform("Platform2");
			p2.setPosition(new Point(85,240));
			p2.setVisible(false);
			p2.setScaleX(.7);
			
			Platform p3= new Platform("Platform2");
			p3.setPosition(new Point(260,165));
			p3.setVisible(false);
			p3.setScaleX(.3);
			
			Platform p4= new Platform("Platform2");
			p4.setPosition(new Point(400,220));
			p4.setVisible(false);
			p4.setScaleX(.3);
			
			Platform p5= new Platform("Platform2");
			p5.setPosition(new Point(330,285));
			p5.setVisible(false);
			p5.setScaleX(.3);
			
			Platform ground = new Platform("Ground");
			ground.setPosition(new Point(0,400));
			ground.setVisible(false);
			ground.setScaleX(2.5);
			
			platforms.add(p1);
			platforms.add(p2);
			platforms.add(p3);
			platforms.add(p4);
			platforms.add(p5);
			platforms.add(ground);
			
			for(Platform p:platforms)
			{
				this.addChild(p);
			}
		}
		background.setScaleX(Main.GAME_WIDTH*1.0/background.getUnscaledWidth());
		background.setScaleY(Main.GAME_HEIGHT*1.0/background.getUnscaledHeight());
	}
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		super.update(pressedKeys, controllers);
	}
}
