package edu.virginia.engine.display;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.virginia.main.Main;

public class CharacterSelect extends JPanel implements ActionListener{

	String[] characters = {"Pikachu", "Peter", "Stewie", "Trooper", "Naruto", "Goku"};
	String[] stages = {"Mario World", "Star Wars", "Pokemon", "Goku", "Naruto", "FamilyGuy"};
	String[] charFileNames = {"pikachu_walking_1.png", "peter_griffin_walking_1.png", "stewie_walking_1.png","clone_trooper_walking_1.png",
								"NarutoStanding.png", "gokuStanding.png"};
	String[] stageFileNames = {"mario_background_1.png", "star_wars_background.jpg", "pokemon_background.jpg", "GokuStage.jpg", "NarutoStage.png", "familyguy_background.jpg"};
	JButton p1Select[] = new JButton[characters.length];
	JButton p2Select[] = new JButton[characters.length];
	JButton p3Select[] = new JButton[characters.length];
	JButton stageSelect[] = new JButton[stages.length];
	JButton startButton;
	JTextField p1Selection,p2Selection,p3Selection,stageSelection;
	JTextField p1Choices,p2Choices,p3Choices,stageChoices;
	String player1 = "Peter", player2 = "Stewie",player3 = null,stage = "Mario World";
	Main myGame;
	public CharacterSelect(Main game, int width, int height)
	{
		this.setBounds(0,0,width, height);
		myGame = game;
		GridLayout layout = new GridLayout(0,7);
		this.setLayout(layout);
		//layout.setAutoCreateGaps(true);
		//layout.setAutoCreateContainerGaps(true);
		
		//SequentialGroup sGroup = layout.createSequentialGroup();

		p1Choices = new JTextField("Player 1:");
		add(p1Choices);
       
		for(int a=0;a<characters.length;a++)
		{
			ImageIcon water = new ImageIcon("resources/"+charFileNames[a]);
			p1Select[a] = new JButton(water);
			p1Select[a].setText(characters[a]);
			p1Select[a].addActionListener(this);
			add(p1Select[a]);
		}
        p1Selection = new JTextField("Player 1 Choice");
        
        p2Choices = new JTextField("Player 2:");
        add(p2Choices);
       
        for(int a=0;a<characters.length;a++)
		{
        	ImageIcon water = new ImageIcon("resources/"+charFileNames[a]);
			p2Select[a] = new JButton(water);
			p2Select[a].setText(characters[a]);
			p2Select[a].addActionListener(this);
			add(p2Select[a]);
		}
        p2Selection = new JTextField("Player 2 choice");
        
        p3Choices = new JTextField("Player 3:");
        add(p3Choices);
       
        for(int a=0;a<characters.length;a++)
		{
        	ImageIcon water = new ImageIcon("resources/"+charFileNames[a]);
			p3Select[a] = new JButton(water);
			p3Select[a].setText(characters[a]);
			p3Select[a].addActionListener(this);
			add(p3Select[a]);
		}
        p3Selection = new JTextField("Player 3 choice");

        stageChoices = new JTextField("Stage:");
        add(stageChoices);
        for(int a=0;a<stages.length;a++)
		{
        	ImageIcon water = new ImageIcon("resources/"+stageFileNames[a]);
			stageSelect[a] = new JButton(water);
			stageSelect[a].setText(stages[a]);
			stageSelect[a].addActionListener(this);
			add(stageSelect[a]);
		}
        stageSelection = new JTextField("   Stage    ");
        /*
        layout.setHorizontalGroup(
        		   layout.createSequentialGroup()
        		      .addCom
        		      .addComponent(c2)
        		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		           .addComponent(c3)
        		           .addComponent(c4))
        		);
        		layout.setVerticalGroup(
        		   layout.createSequentialGroup()
        		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        		           .addComponent(c1)
        		           .addComponent(c2)
        		           .addComponent(c3))
        		      .addComponent(c4)
        		);
        */
        startButton = new JButton("Start Match");
        startButton.addActionListener(this);
        add(startButton);
        add(p1Selection);
        add(p2Selection);
        add(p3Selection);
        add(stageSelection);
        
        /*
        add(button1);
        add(button2);
        add(button3);
        
        add(player1Selection);
        add(buttonStart);
        
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        buttonStart.addActionListener(this);*/
        
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(JButton b: p1Select){
			if(e.getSource() == b)
			{
				player1 = b.getText();
				p1Selection.setText(player1);
			}
		}
		for(JButton b: p2Select){
			if(e.getSource() == b)
			{
				player2 = b.getText();
				p2Selection.setText(player2);
			}
		}
		for(JButton b: p3Select){
			if(e.getSource() == b)
			{
				player3 = b.getText();
				p3Selection.setText(player3);
			}
		}
		for(JButton b: stageSelect){
			if(e.getSource() == b)
			{
				stage = b.getText();
				stageSelection.setText(stage);
			}
		}
		
		if(e.getSource() == startButton)
		{
			CardLayout a = (CardLayout) this.getParent().getLayout();
			myGame.addPlayers(player1,player2,player3,stage);
			myGame.start();
			a.next(this.getParent());
		}
	}
	

}
