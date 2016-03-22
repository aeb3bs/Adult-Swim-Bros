package edu.virginia.main;

import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.Coin;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.HealthBar;
import edu.virginia.engine.display.Human;
import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.Platform;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Stewie;
import edu.virginia.engine.events.CharacterCollisionEvent;
import edu.virginia.engine.events.CharacterCollisionManager;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.engine.events.PlatformCollisionEvent;
import edu.virginia.engine.events.PlatformManager;
import edu.virginia.engine.events.QuestManager;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenTransitions;
import edu.virginia.engine.tweening.TweenTransitions.transitiontype;
import edu.virginia.engine.tweening.TweenableParam;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class Main extends Game{
	public static final double gravity = 4000.0;
	/* Create a sprite object for our game. We'll use mario */
	Human ash1 = new Human("Ash1", false);
	Coin coin1 = new Coin("Coin1");
	QuestManager myQuestManager = new QuestManager();
	Mario mario1 = new Mario("Mario1", false);
	Stewie stewie1 = new Stewie("Stewie1", false);
	DisplayObjectContainer mario_background = new DisplayObjectContainer("Background1");
	PlatformManager myPlatformManager = new PlatformManager();
	CharacterCollisionManager myCharacterCollisionManager = new CharacterCollisionManager();
	public static SoundManager sm = new SoundManager();
	
	/* 
	 * platforms
	 */
	ArrayList<Platform>platforms = new ArrayList<Platform>();
	Platform p1 = new Platform("Platform1");
	Platform p2 = new Platform("Platform2");
	Platform p3 = new Platform("Platform3");
	Platform p4 = new Platform("Platform4");
	Platform ground = new Platform("Ground");
	
	private static ArrayList<DisplayObject>allchildren = new ArrayList<DisplayObject>();
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * */
	public Main() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		super("Lab Two Test Game", 500, 425);
		
//		Scanner scanner = new Scanner(System.in);
//		
//		System.out.println("What is your userId?");
//		int userId = scanner.nextInt();
//		DynamoDBManager.getInstance().setUserId(userId);
//		
//		System.out.println("What is your enemy's id?");
//		int enemyId = scanner.nextInt();
//		DynamoDBManager.getInstance().setEnemyId(enemyId);
		
		ash1.setPosition(new Point(300,300));
		coin1.setPosition(new Point(350,25));
		mario1.setPosition(new Point(300,300));
		stewie1.setPosition(new Point(100,0));
		mario_background.setPosition(new Point(0,0));
		p1.setPosition(new Point(15,240));
		p2.setPosition(new Point(275,180));
		p3.setPosition(new Point(15,120));
		p4.setPosition(new Point(275,60));
		ground.setPosition(new Point(0,325));
		
		ground.setScaleX(2.5);
		ground.setVisible(false);
		ground.setDefaultHitbox();
		
		platforms.add(p1);
		platforms.add(p2);
		platforms.add(p3);
		platforms.add(p4);
		platforms.add(ground);

		mario_background.setImage(DisplayObject.readImage("mario_background_1.png"));
		
		this.addChild(mario_background);
		this.addChild(mario1);
		this.addChild(stewie1);
		
		//healthBar
		HealthBar healthBarMario = new HealthBar ("healthbarMario", false, 0, -10);
		
		mario1.addChild(healthBarMario);
		
		for(Platform p:platforms)
		{
			this.addChild(p);
		}
		this.addChild(coin1);
		
		
		sm = new SoundManager();
		sm.LoadMusic("Theme Song", "mario_theme_song.wav");
		sm.LoadMusic("Victory Song", "mario_victory.wav");
		sm.PlayMusic("Theme Song", true);
		
		Tween linearTween = new Tween(mario1, new TweenTransitions(transitiontype.lineartrans), "LinearTween");
		linearTween.animate(TweenableParam.ALPHA, 0.0, 1.0, 2500);
		linearTween.animate(TweenableParam.SCALEX, 0.0, 1.0, 1500);
		linearTween.animate(TweenableParam.POSITIONX, -200, 300, 1500);
		
		Tween x2Tween = new Tween(mario1, new TweenTransitions(transitiontype.x2trans), "x2Tween");
		x2Tween.animate(TweenableParam.POSITIONY, 0, 300, 1500);
		
		coin1.addEventListener(myQuestManager, PickedUpEvent.COIN_PICKED_UP);
		mario1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		stewie1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		stewie1.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		
		stewie1.setScaleX(.5);
		stewie1.setScaleY(.5);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		
		//if ash is near coin, throw event
		if(mario1 != null && coin1 != null)
		{
			Point p1 = mario1.getPosition();
			Point p2 = coin1.getPosition();
			if(p1.distance(p2)<15)
			{
				PickedUpEvent e = new PickedUpEvent();
				coin1.dispatchEvent(e);
				coin1.removeEventListener(myQuestManager, PickedUpEvent.COIN_PICKED_UP);
			}
		}
		
		TweenJuggler.getInstance().nextFrame();
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
	}
	
	@Override
	public void addChild(DisplayObject o)
	{
		super.addChild(o);
		getAllchildren().add(o);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 * */
	public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Main game = new Main();
		game.start();

	}

	public static ArrayList<DisplayObject> getAllchildren() {
		return allchildren;
	}

	public static void setAllchildren(ArrayList<DisplayObject> allchildren) {
		Main.allchildren = allchildren;
	}
}
