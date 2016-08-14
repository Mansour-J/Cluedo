package view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import cards.Card;
import cards.*;
import cards.CharacterCard;
import cluedo.Cluedo;

/**
 * Created by javahemans on 14/08/16.
 */
public class GenericDialogue extends JDialog {

	Cluedo cluedo;

	List<Card> characters;
	ImageIcon questionMark = new ImageIcon("images/misc/Questionmark.png");

	public GenericDialogue(JFrame parent, List<Card> cards, Cluedo cluedo) {

    	super(parent, "Selecting Characters ...", false);
        this.characters = cards;
        this.cluedo = cluedo;
		setSize(800, 800);
		setVisible(true);

		for (Card c : cards) {

			if (c instanceof CharacterCard) {
				JButton tmp = new JButton(new ImageIcon("images/characters/" + c.getValue() + ".png"));
				this.add(tmp);

			} else if (c instanceof RoomCard) {
				JButton tmp = new JButton(new ImageIcon("images/rooms/" + c.getValue() + ".png"));
				this.add(tmp);

			} else if (c instanceof WeaponCard) {
				JButton tmp = new JButton(new ImageIcon("images/weapons/" + c.getValue() + ".png"));
				this.add(tmp);
			}

			setLayout(new GridLayout(0, 3));

		}
	}
}
