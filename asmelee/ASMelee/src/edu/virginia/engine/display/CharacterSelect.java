package edu.virginia.engine.display;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.virginia.main.Main;

public class CharacterSelect extends JPanel implements ActionListener{

	String[] characters = {"Pikachu", "Peter", "Stewie", "Trooper", "Naruto", "Goku"};
	String[] stages = {"Mario World"};
	JButton p1Select[] = new JButton[characters.length];
	JButton p2Select[] = new JButton[characters.length];
	JButton stageSelect[] = new JButton[stages.length];
	JButton startButton;
	JTextField p1Selection,p2Selection,stageSelection;
	String player1, player2 = "Goku",stage = "Mario World";
	Main myGame;
	public CharacterSelect(Main game, int width, int height)
	{
		this.setBounds(0,0,width, height);
		myGame = game;
		for(int a=0;a<characters.length;a++)
		{
			p1Select[a] = new JButton();
			p1Select[a].setText(characters[a]);
			p1Select[a].addActionListener(this);
			add(p1Select[a]);
		}
        p1Selection = new JTextField("Player 1 Choice");
        
        for(int a=0;a<characters.length;a++)
		{
			p2Select[a] = new JButton();
			p2Select[a].setText(characters[a]);
			p2Select[a].addActionListener(this);
			add(p2Select[a]);
		}
        p2Selection = new JTextField("Player 2 choice");

        for(int a=0;a<stages.length;a++)
		{
			stageSelect[a] = new JButton();
			stageSelect[a].setText(stages[a]);
			stageSelect[a].addActionListener(this);
			add(stageSelect[a]);
		}
        stageSelection = new JTextField("Stage");
        
        startButton = new JButton("Start Match");
        startButton.addActionListener(this);
        add(startButton);
        add(p1Selection);
        add(p2Selection);
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
			myGame.addPlayers(player1,player2,stage);
			myGame.start();
			a.next(this.getParent());
		}
	}
	

}
