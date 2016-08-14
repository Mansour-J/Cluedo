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

    JButton sc;
    JButton mustard;
    JButton peacock;
    JButton white;
    JButton green;
    JButton plum;

    int numPlayer;
    Cluedo cluedo;

    ArrayList<CharacterCard.Character> characters;
    ImageIcon questionMark = new ImageIcon("images/misc/Questionmark.png");

    public GenericDialogue(JFrame parent, ArrayList<Card> cards, Cluedo cluedo) {

    	super(parent, "Selecting Characters ...", false);
        characters = new ArrayList<>();
        this.numPlayer = numPlayer;
        this.cluedo = cluedo;


        setSize(800,800);

        for(Card c: cards){

            JButton tmp =  new JButton(new ImageIcon("images/characters/Miss Scarlet.png"));
            tmp.setActionCommand("MISS_SCARLETTE");
            this.add(sc);

        }


        setLayout(new GridLayout(0,3));





    }


}
