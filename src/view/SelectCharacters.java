package view;

import cards.CharacterCard;
import cluedo.Cluedo;
import util.CluedoError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by javahemans on 14/08/16.
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
	JFrame parent;

	ArrayList<CharacterCard.Character> characters;
	ImageIcon questionMark = new ImageIcon("images/misc/Questionmark.png");

	public SelectCharacters(JFrame parent, int numPlayer, Cluedo cluedo) {
		super(parent, "Selecting Characters ...", false);
		characters = new ArrayList<>();
		this.numPlayer = numPlayer;
		this.cluedo = cluedo;
		this.parent = parent;

		setSize(800, 800);

		sc = new JButton(new ImageIcon("images/characters/MISS_SCARLETTE.png"));
		sc.setActionCommand("MISS_SCARLETTE");
		sc.addActionListener(this);

		mustard = new JButton(new ImageIcon("images/characters/COLENEL_MUSTARD.png"));
		mustard.setActionCommand("COLENEL_MUSTARD");
		mustard.addActionListener(this);

		peacock = new JButton(new ImageIcon("images/characters/MRS_PEACOCK.png"));
		peacock.setActionCommand("MRS_PEACOCK");
		peacock.addActionListener(this);

		white = new JButton(new ImageIcon("images/characters/MRS_WHITE.png"));
		white.setActionCommand("MRS_WHITE");
		white.addActionListener(this);

		plum = new JButton(new ImageIcon("images/characters/PROFESSOR_PLUM.png"));
		plum.setActionCommand("PROFESSOR_PLUM");
		plum.addActionListener(this);

		green = new JButton(new ImageIcon("images/characters/THE_REVEREND_GREEN.png"));
		green.setActionCommand("THE_REVEREND_GREEN");
		green.addActionListener(this);

		setLayout(new GridLayout(0, 3));

		this.add(sc);
		this.add(mustard);
		this.add(peacock);
		this.add(white);
		this.add(green);
		this.add(plum);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		numPlayer--;
		switch (e.getActionCommand()) {
		case "MISS_SCARLETTE":

			characters.add(CharacterCard.Character.MISS_SCARLETTE);
			sc.setIcon(questionMark);
			sc.setActionCommand("none");
			break;
		case "COLENEL_MUSTARD":

			characters.add(CharacterCard.Character.COLENEL_MUSTARD);
			mustard.setIcon(questionMark);
			mustard.setActionCommand("none");
			break;
		case "MRS_WHITE":

			characters.add(CharacterCard.Character.MRS_WHITE);
			white.setIcon(questionMark);
			white.setActionCommand("none");
			break;
		case "THE_REVEREND_GREEN":

			characters.add(CharacterCard.Character.THE_REVEREND_GREEN);
			green.setIcon(questionMark);
			green.setActionCommand("none");
			break;
		case "MRS_PEACOCK":

			characters.add(CharacterCard.Character.MRS_PEACOCK);
			peacock.setIcon(questionMark);
			peacock.setActionCommand("none");
			break;
		case "PROFESSOR_PLUM":

			characters.add(CharacterCard.Character.PROFESSOR_PLUM);
			plum.setIcon(questionMark);
			plum.setActionCommand("none");
			break;
		default:
			numPlayer++;
			// custom title, error icon
			JOptionPane.showMessageDialog(this, "This character had been choosen", "Invalid Selection",
					JOptionPane.ERROR_MESSAGE);
		}
		if (numPlayer == 0) {
			dispose();
			this.cluedo.setupGame(characters);

		}
	}
}
