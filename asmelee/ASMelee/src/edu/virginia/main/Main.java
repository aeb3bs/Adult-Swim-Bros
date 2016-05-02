package edu.virginia.main;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.Character;
import edu.virginia.engine.display.CharacterSelect;
import edu.virginia.engine.display.Coin;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.FamilyGuyStage;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.GameOverPanel;
import edu.virginia.engine.display.Goku;
import edu.virginia.engine.display.GokuStage;
import edu.virginia.engine.display.Mario;
import edu.virginia.engine.display.Naruto;
import edu.virginia.engine.display.NarutoStage;
import edu.virginia.engine.display.Peter;
import edu.virginia.engine.display.Pikachu;
import edu.virginia.engine.display.PokemonStage;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Stage;
import edu.virginia.engine.display.StarWarsStage;
import edu.virginia.engine.display.Stewie;
import edu.virginia.engine.display.Trooper;
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
import edu.virginia.engine.events.SpecialTrooperCollisionEvent;
import edu.virginia.engine.events.SpecialTrooperCollisionManager;
import edu.virginia.engine.tweening.TweenJuggler;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class Main extends Game{
	public static final boolean debugMode =false;
	public static final double gravity = 4000.0;
	public static final int GAME_HEIGHT = 800;
	public static final int GAME_WIDTH = 1000;
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
	SpecialTrooperCollisionManager mySpecialTrooperCollisionManager = new SpecialTrooperCollisionManager();
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
	public Main(JFrame gameFrame, String char1, String char2, String stage) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		super(gameFrame, "Lab Two Test Game", GAME_WIDTH, GAME_HEIGHT);
	}
	public void addPlayers(String char1, String char2, String stage)
	{

		Character player1 = null,player2 = null;
		switch(char1)
		{
		case "Stewie":
			player1 = new Stewie("stewie",false);
			break;
		case "Peter":
			player1 = new Peter("peter",false);
			break;
		case "Trooper":
			player1 = new Trooper("trooper",false);
			break;
		case "Pikachu":
			player1 = new Pikachu("pikachu",false);
			break;
		case "Goku":
			player1 = new Goku("goku",false);
			break;
		case "Naruto":
			player1 = new Naruto("naruto", false);
			break;
		}
		
		switch(char2)
		{
		case "Stewie":
			player2 = new Stewie("stewie",false);
			break;
		case "Peter":
			player2 = new Peter("peter",false);
			break;
		case "Trooper":
			player2 = new Trooper("trooper",false);
			break;
		case "Pikachu":
			player2 = new Pikachu("pikachu",false);
			break;
		case "Goku":
			player2 = new Goku("goku",false);
			break;
		case "Naruto":
			player2 = new Naruto("naruto", false);
			break;
		}
		
		player1.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		player1.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		player1.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		player1.addEventListener(mySpecialStewieCollisionManager, SpecialStewieCollisionEvent.SPECIALSTEWIE);
		player1.addEventListener(mySpecialTrooperCollisionManager, SpecialTrooperCollisionEvent.SPECIALTROOPER);
		player1.addEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
		player1.myControllerIndex = 0;
		player1.setPivotPoint(new Point(player1.getUnscaledWidth()/2,0));
		player1.setPosition(new Point(100,0));
		player1.setScaleX(player1.defaultScaleX);
		player1.setScaleY(player1.defaultScaleY);

		
		player2.addEventListener(myPlatformManager, PlatformCollisionEvent.COLLISION);
		player2.addEventListener(myCharacterCollisionManager, CharacterCollisionEvent.MELEE);
		player2.addEventListener(myRangedCollisionManager, RangedCollisionEvent.RANGED);
		player2.addEventListener(mySpecialStewieCollisionManager, SpecialStewieCollisionEvent.SPECIALSTEWIE);
		player2.addEventListener(mySpecialTrooperCollisionManager, SpecialTrooperCollisionEvent.SPECIALTROOPER);
		player2.addEventListener(myCharacterDeathManager, CharacterDeathEvent.DEATH);
		player2.myControllerIndex = -1;
		player2.setPivotPoint(new Point(player1.getUnscaledWidth()/2,0));
		player2.setPosition(new Point(100,0));
		player2.setScaleX(player2.defaultScaleX);
		player2.setScaleY(player2.defaultScaleY);

		switch(stage)
		{
		case "Mario World":
			myStage = new Stage();
			break;
		case "Star Wars":
			myStage = new StarWarsStage();
			break;
		case "Pokemon":
			myStage = new PokemonStage();
			player1.setPosition(new Point(300,260));
			player2.setPosition(new Point(600,260));
			break;
		case "Naruto":
			myStage = new NarutoStage();
			break;
		case "Goku":
			myStage = new GokuStage();
			break;
		case "FamilyGuy":
			myStage = new FamilyGuyStage();
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
		

	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys,ArrayList<GamePad> controllers){
		super.update(pressedKeys,controllers);
		
		//If all but one dead, return to character select screen
		int allDead =0;
		String winner = "";
		for(DisplayObject d: this.getChildren())
		{
			if(d instanceof Character && ((Character)d).alive){
					allDead++;
					winner = d.getId();
			}
		}
		if(allDead == 1)
		{
			this.removeAll();
			//Do something here to show game is over
			exitGame(winner);
		}
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
//		Main game = new Main("peter", "trooper", "mario_stage");
//		game.start();
		
		String player1, player2, stage;
		String gameId = "Adult Swim Smash";
		
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle(gameId);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setBounds(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
		mainFrame.setLayout(new CardLayout());
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JPanel cards = new JPanel();
		cards.setLayout(new CardLayout());
		mainFrame.getContentPane().add(cards);
		Main game = new Main(mainFrame,"pikachu", "peter", "mario_stage");
		cards.add(new GameOverPanel(Main.GAME_WIDTH,Main.GAME_HEIGHT));
		cards.add(new CharacterSelect(game, Main.GAME_WIDTH,Main.GAME_HEIGHT),"CharacterSelect");
		mainFrame.setVisible(true);
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "CharacterSelect");
		//game.start();
		
		//code to generate code for new character constructor
//		for(int index=1; index<=4;index++)
//		{
//			System.out.println("BufferedImage d"+(index+14)+" = DisplayObject.readImage(\"pikachu_jumping_"+index+"" +
//					".png\");\nimages.add(d"+(index+14)+");");
//		}
	}

	public static ArrayList<DisplayObject> getAllchildren() {
		return allchildren;
	}

	public static void setAllchildren(ArrayList<DisplayObject> allchildren) {
		Main.allchildren = allchildren;
	}
}
