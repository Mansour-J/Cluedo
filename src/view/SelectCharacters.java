package view;

import util.CluedoError;

import javax.swing.*;

import Model.CharacterCard;
import controller.Cluedo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by javahemans on 14/08/16.
 * This is used to select the characters. Currently it is used at the start of the game for setting up the players
 */
public class SelectCharacters extends JDialog implements ActionListener {

	JButton sc;
	JButton mustard;
	JButton peacock;
	JButton white;
	JButton green;
	JButton plum;

	int numPlayer;
	Cluedo cluedo;
	BoardFrame parent;

	ArrayList<CharacterCard.Character> characters;
	ImageIcon questionMark = new ImageIcon("images/misc/Questionmark.png");

	/**
	 *
	 * @param owner
	 * @param numPlayer
	 * @param cluedo
     */
	public SelectCharacters(BoardFrame owner, int numPlayer, Cluedo cluedo) {
		super(owner, "Selecting Characters ...", false);
		characters = new ArrayList<>();
		this.numPlayer = numPlayer;
		this.cluedo = cluedo;
		this.parent = owner;

		setSize(520, 500);

		sc = new JButton(new ImageIcon("images/characters/MISS_SCARLETTE.png"));
		sc.setActionCommand("MISS_SCARLETTE");

		mustard = new JButton(new ImageIcon("images/characters/COLENEL_MUSTARD.png"));
		mustard.setActionCommand("COLENEL_MUSTARD");

		peacock = new JButton(new ImageIcon("images/characters/MRS_PEACOCK.png"));
		peacock.setActionCommand("MRS_PEACOCK");

		white = new JButton(new ImageIcon("images/characters/MRS_WHITE.png"));
		white.setActionCommand("MRS_WHITE");

		plum = new JButton(new ImageIcon("images/characters/PROFESSOR_PLUM.png"));
		plum.setActionCommand("PROFESSOR_PLUM");

		green = new JButton(new ImageIcon("images/characters/THE_REVEREND_GREEN.png"));
		green.setActionCommand("THE_REVEREND_GREEN");

		setLayout(new GridLayout(0, 3));

		java.util.List<JButton> buttons  = Arrays.asList(sc, mustard, peacock, white, plum, green);
		for(JButton b : buttons) {
			b.addActionListener(this);
			b.setBorder(BorderFactory.createEmptyBorder());
			add(b);
			b.setSize(40,40);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		numPlayer--;
		switch (e.getActionCommand()) {
		case "MISS_SCARLETTE":

			characters.add(CharacterCard.Character.MISS_SCARLETTE);
			sc.setIcon(questionMark);
			sc.setActionCommand("none");
			sc.setBorder(BorderFactory.createEmptyBorder());
			break;
		case "COLENEL_MUSTARD":

			characters.add(CharacterCard.Character.COLENEL_MUSTARD);
			mustard.setIcon(questionMark);
			mustard.setActionCommand("none");
			mustard.setBorder(BorderFactory.createEmptyBorder());

			break;
		case "MRS_WHITE":

			characters.add(CharacterCard.Character.MRS_WHITE);
			white.setIcon(questionMark);
			white.setActionCommand("none");
			white.setBorder(BorderFactory.createEmptyBorder());

			break;
		case "THE_REVEREND_GREEN":

			characters.add(CharacterCard.Character.THE_REVEREND_GREEN);
			green.setIcon(questionMark);
			green.setActionCommand("none");
			green.setBorder(BorderFactory.createEmptyBorder());

			break;
		case "MRS_PEACOCK":

			characters.add(CharacterCard.Character.MRS_PEACOCK);
			peacock.setIcon(questionMark);
			peacock.setActionCommand("none");
			peacock.setBorder(BorderFactory.createEmptyBorder());

			break;
		case "PROFESSOR_PLUM":

			characters.add(CharacterCard.Character.PROFESSOR_PLUM);
			plum.setIcon(questionMark);
			plum.setActionCommand("none");
			plum.setBorder(BorderFactory.createEmptyBorder());

			break;
		default:
			numPlayer++;
			// custom title, error icon
			JOptionPane.showMessageDialog(this, "This character had been chosen", "Invalid Selection",
					JOptionPane.ERROR_MESSAGE);
		}

		// Finished adding characters
		if (numPlayer == 0) {
			dispose();
			this.cluedo.setupGame(characters);
			parent.repaint();
			parent.setCurrentPlayerText();
		}
	}
}
