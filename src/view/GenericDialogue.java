package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import cards.*;
import cluedo.Cluedo;
import util.CluedoError;

/**
 * Created by javahemans on 14/08/16.
 */
public class GenericDialogue extends JDialog{

    Cluedo cluedo;

    ArrayList<CharacterCard.Character> characters;
    ImageIcon questionMark = new ImageIcon("images/misc/Questionmark.png");

    public GenericDialogue(JFrame parent, ArrayList<Card> cards, Cluedo cluedo) {

    	super(parent, "Selecting Characters ...", false);
        characters = new ArrayList<>();
        this.cluedo = cluedo;
        setSize(800,800);

        for(Card c: cards){
            JButton tmp =  new JButton(new ImageIcon("images/characters/Miss Scarlet.png"));
            tmp.setActionCommand("MISS_SCARLETTE");
        }

        setLayout(new GridLayout(0,3));
    }


}
