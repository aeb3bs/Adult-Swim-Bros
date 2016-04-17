package edu.virginia.main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.Coin;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Human;
import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.Peter;
import edu.virginia.engine.display.Platform;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Stage;
import edu.virginia.engine.display.Stewie;
import edu.virginia.engine.display.Character;
import edu.virginia.engine.events.CharacterCollisionEvent;
import edu.virginia.engine.events.CharacterCollisionManager;
import edu.virginia.engine.events.CharacterDeathEvent;
import edu.virginia.engine.events.CharacterDeathManager;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.engine.events.PlatformCollisionEvent;
import edu.virginia.engine.events.PlatformManager;
import edu.virginia.engine.events.QuestManager;
import edu.virginia.engine.events.RangedCollisionEvent;
import edu.virginia.engine.events.RangedCollisionManager;
import edu.virginia.engine.events.SpecialStewieCollisionEvent;
import edu.virginia.engine.events.SpecialStewieCollisionManager;
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
	public static final boolean debugMode =false;
	public static final double gravity = 4000.0;
	public static final int GAME_HEIGHT = 500;
	public static final int GAME_WIDTH = 500;
	/* Create a sprite object for our game. We'll use mario */
	//Human ash1 = new Human("Ash1", false);
	Coin coin1 = new Coin("Coin1");
	QuestManager myQuestManager = new QuestManager();
	Mario mario1 = new Mario("Mario1", false);
	Stewie stewie1 = new Stewie("Stewie1", false);
	Peter peter1 = new Peter("Peter1", false);
	
	PlatformManager myPlatformManager = new PlatformManager();
	CharacterCollisionManager myCharacterCollisionManager = new CharacterCollisionManager();
	RangedCollisionManager myRangedCollisionManager = new RangedCollisionManager();
	public static SoundManager sm = new SoundManager();
	public static CharacterDeathManager myCharacterDeathManager = new CharacterDeathManager();
	SpecialStewieCollisionManager mySpecialStewieCollisionManager = new SpecialStewieCollisionManager();
	Stage myStage = new Stage();
	
	/* 
	 * platforms now in stage class
	 */
	
	
	private static ArrayList<DisplayObject>allchildren = new ArrayList<DisplayObject>();
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * */
	public Main(String char1, String char2, String stage) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		super("Lab Two Test Game", GAME_WIDTH, GAME_HEIGHT);
		
//		Scanner scanner = new Scanner(System.in);
//		
//		System.out.println("What is your userId?");
//		int userId = scanner.nextInt();
//		DynamoDBManager.getInstance().setUserId(userId);
//		
//		System.out.println("What is your enemy's id?");
//		int enemyId = scanner.nextInt();
//		DynamoDBManager.getInstance().setEnemyId(enemyId);
		
		//ash1.setPosition(new Point(300,300));
		Character player1 = null,player2 = null;
		switch(char1)
		{
		case "stewie":
			player1 = new Stewie("stewie",false);
			break;
		case "peter":
			player1 = new Peter("peter",false);
		}
		
		switch(char2)
		{
		case "stewie":
			player2 = new Stewie("stewie",false);
			break;
		case "peter":
			player2 = new Peter("peter",false);
		}
		
		player1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		player1.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		player1.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		player1.addEventListener(mySpecialStewieCollisionManager, SpecialStewieCollisionEvent.SPECIALSTEWIE);
		player1.addEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
		player1.myControllerIndex = -1;
		player1.setPivotPoint(new Point(player1.getUnscaledWidth()/2,0));
		player1.setPosition(new Point(100,0));
		player1.setScaleX(.5);
		player1.setScaleY(.5);
		
		player2.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		player2.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		player2.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		player2.addEventListener(mySpecialStewieCollisionManager, SpecialStewieCollisionEvent.SPECIALSTEWIE);
		player2.addEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
		player2.myControllerIndex = 0;
		player2.setPivotPoint(new Point(player1.getUnscaledWidth()/2,0));
		player2.setPosition(new Point(100,0));
		player1.setScaleX(.5);
		player1.setScaleY(.5);
		/*
		coin1.setPosition(new Point(350,25));
		mario1.setPosition(new Point(300,300));
		stewie1.setPosition(new Point(100,0));
		stewie1.setPivotPoint(new Point(stewie1.getUnscaledWidth()/2,0));
		peter1.setPosition(new Point(100,0));
		peter1.setPivotPoint(new Point(peter1.getUnscaledWidth()/2,0));
*/
		switch(stage)
		{
		case "mario_stage":
			myStage = new Stage();
			break;
		}
		myStage.setUp();
		this.addChild(myStage.background);
		this.addChild(myStage);
		this.addChild(player1);
		this.addChild(player2);
		
//		sm = new SoundManager();
//		sm.LoadMusic("Theme Song", "mario_theme_song.wav");
//		sm.LoadMusic("Victory Song", "mario_victory.wav");
//		sm.PlayMusic("Theme Song", true);
		
		/*
		Tween linearTween = new Tween(mario1, new TweenTransitions(transitiontype.lineartrans), "LinearTween");
		linearTween.animate(TweenableParam.ALPHA, 0.0, 1.0, 2500);
		linearTween.animate(TweenableParam.SCALEX, 0.0, 1.0, 1500);
		linearTween.animate(TweenableParam.POSITIONX, -200, 300, 1500);
		
		Tween x2Tween = new Tween(mario1, new TweenTransitions(transitiontype.x2trans), "x2Tween");
		x2Tween.animate(TweenableParam.POSITIONY, 0, 300, 1500);
		
		coin1.addEventListener(myQuestManager, PickedUpEvent.COIN_PICKED_UP);
		mario1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		mario1.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		stewie1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		stewie1.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		stewie1.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		stewie1.addEventListener(mySpecialStewieCollisionManager, SpecialStewieCollisionEvent.SPECIALSTEWIE);
		peter1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		peter1.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		peter1.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		peter1.addEventListener(mySpecialStewieCollisionManager, SpecialStewieCollisionEvent.SPECIALSTEWIE);
		
		//mario1 reacts to death
		stewie1.addEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
		peter1.addEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
		
		stewie1.setScaleX(.5);
		stewie1.setScaleY(.5);
		peter1.setScaleX(.5);
		peter1.setScaleY(.5);
		*/
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers){
		super.update(pressedKeys,controllers);
		
//		if(mario1 != null && mario1.healthbar != null)
//		{
//			Double actualHealth = mario1.healthbar.getActualHealth();
//			Double visibleHealth = mario1.healthbar.getVisibleHealth();
//			
//		
//			if(actualHealth != null && visibleHealth != null)
//			{
//				//Evan Edit: in future need to iterate through list of characters
//				if (actualHealth < visibleHealth){
//					mario1.healthbar.setVisibleHealth(visibleHealth-.5);
//				}
//				else if (actualHealth != visibleHealth){
//					// make sure we don't go over
//					mario1.healthbar.setVisibleHealth(actualHealth);
//				}
//				else {
//					
//				}
//				if (mario1.healthbar.actualHealth > 0){
//					mario1.healthbar.greenHealthBar.setScaleX(mario1.healthbar.actualHealth/100);
//					mario1.healthbar.redHealthBar.setScaleX(mario1.healthbar.visibleHealth/100);
//				}
//				else {
//					// pure bug avoidance: setScaleX cannot be set to 0
//					mario1.healthbar.greenHealthBar.setScaleX(.01);
//					mario1.healthbar.redHealthBar.setScaleX(.01);
//					//dispatch mario's death -> 1 character left
//					mario1.dispatchEvent(new CharacterDeathEvent(1));
//					mario1.removeEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
//				}
//			}
//
//		}
		
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
		
		/*for(DisplayObjectContainer d: children)
		{
			d.update(pressedKeys);
		}*/
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
//		for(DisplayObject o: Main.getAllchildren())
//			if(o.getHitbox()!=null)
//				g.drawRect(o.getHitboxGlobal().x, o.getHitboxGlobal().y, o.getHitbox().width, o.getHitbox().height);
		this.drawBoundingBoxes(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 * */
	public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Main game = new Main("stewie", "peter", "mario_stage");
		game.start();
		
		//code to generate code for new character constructor
//		for(int index=15; index<23;index++)
//		{
//			System.out.println("BufferedImage d"+index+" = DisplayObject.readImage(\"peter_griffin_special_"+(index-14)+"" +
//					".png\");\nimages.add(d"+index+");");
//		}
	}

	public static ArrayList<DisplayObject> getAllchildren() {
		return allchildren;
	}

	public static void setAllchildren(ArrayList<DisplayObject> allchildren) {
		Main.allchildren = allchildren;
	}
}
