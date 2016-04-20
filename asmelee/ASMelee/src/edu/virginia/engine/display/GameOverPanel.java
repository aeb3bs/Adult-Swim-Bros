package edu.virginia.engine.display;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class GameOverPanel extends JPanel implements ActionListener{

	public String winner = "No one";
	JButton restart;
	JTextPane output;
	public GameOverPanel(int width,int height)
	{
		this.setBounds(0,0,width, height);
		restart = new JButton();
		output = new JTextPane();
		output.setText(winner + " has won the game!");
		add(output);
		restart.addActionListener(this);
		restart.setText("Restart Game");
		add(restart);
		
	}
	public void saveWinner(String win)
	{
		winner = win;
		output.setText(winner + " has won the game!");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		CardLayout a = (CardLayout) this.getParent().getLayout();
		a.next(this.getParent());
	}

}
