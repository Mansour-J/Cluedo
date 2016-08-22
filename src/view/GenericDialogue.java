package view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cards.Card;
import cards.*;
import cards.CharacterCard;
import cluedo.Cluedo;
import util.CluedoError;

/**
 * Created by javahemans on 14/08/16.
 */
public class GenericDialogue extends JDialog {

	Cluedo cluedo;

	List<Card> characters;
	ImageIcon questionMark = new ImageIcon("images/misc/Questionmark.png");
	
	public GenericDialogue(JFrame parent, List<Card> cards, Cluedo cluedo) {

    	super(parent, "", false);
        this.characters = cards;
        this.cluedo = cluedo;
		setSize(520, 720);
		setVisible(true);
		setLayout(new GridLayout(3, 4));

		for (Card c : cards) {
			JButton tmp;
			String path;

			if (c instanceof CharacterCard) {
				path = "images/characters/" + c.getValue() + ".png";

			} else if (c instanceof RoomCard) {
				path = "images/rooms/" + c.getValue() + ".png";

			} else if (c instanceof WeaponCard) {
				path = "images/weapons/" + c.getValue() + ".png";

			} else{
				throw new CluedoError("Unexpected card");
			}

			ImageIcon imageIcon = new ImageIcon(path);
			tmp = new JButton(imageIcon);
			tmp.setSize(20,70);
			tmp.setBorder(BorderFactory.createEmptyBorder());
			add(tmp);
		}
	}
}
