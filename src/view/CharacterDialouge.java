package view;

import cluedo.Cluedo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CharacterDialouge extends JDialog{
	private BoardFrame frame;

	public CharacterDialouge(BoardFrame frame, Cluedo cluedo) {
		this.frame = frame;
		Object[] possibilities = { "3", "4", "5", "6" };

		String numPlayer = (String) JOptionPane.showInputDialog(frame, "How many players you want to play with", "Players",1,null,possibilities,"3");

		int numPlayers = Integer.parseInt(numPlayer);

		SelectCharacters mansour = new SelectCharacters(frame, numPlayers, cluedo);
		mansour.setVisible(true);
	}
}
