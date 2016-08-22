package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cards.Card;
import cards.*;
import cards.CharacterCard;
import cluedo.Cluedo;
import util.Accusation;
import util.CluedoError;

/**
 * Created by javahemans on 14/08/16.
 */
public class AccusationDialog extends JDialog implements ActionListener {

    Cluedo cluedo;
    List<Card> cards;
    int numOfCardsToSelect;
    int index = -1;
    private JFrame parent;

    // 580, 760

    public AccusationDialog(JFrame parent, List<Card> cards, Cluedo cluedo, int num) {
        super((Window) null);
        //super(parent, "Selecting Characters ...", false);
        this.cluedo = cluedo;
        this.cards = cards;
        this.numOfCardsToSelect = num;
        this.parent = parent;
        setSize(520, 720);
        setVisible(true);
        setLayout(new GridLayout(3, 3));

        for (Card c : cards) {
            if (c instanceof CharacterCard) {
                JButton tmp = new JButton(new ImageIcon("images/characters/" + c.getValue() + ".png"));
                tmp.addActionListener(this);
                tmp.setBorder(BorderFactory.createEmptyBorder());
                tmp.setActionCommand(String.valueOf(++index));
                this.add(tmp);

            } else if (c instanceof RoomCard) {
                JButton tmp = new JButton(new ImageIcon("images/rooms/" + c.getValue() + ".png"));
                tmp.addActionListener(this);
                tmp.setBorder(BorderFactory.createEmptyBorder());

                tmp.setActionCommand(String.valueOf(++index));
                this.add(tmp);

            } else if (c instanceof WeaponCard) {
                JButton tmp = new JButton(new ImageIcon("images/weapons/" + c.getValue() + ".png"));
                tmp.addActionListener(this);
                int z = ++index;
                tmp.setActionCommand(String.valueOf(z));
                tmp.setBorder(BorderFactory.createEmptyBorder());
                this.add(tmp);
            }
        }
        setModal(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Accusation.selectedCards.add(this.cards.get(Integer.valueOf(e.getActionCommand())));
        if (Accusation.selectedCards.size() == numOfCardsToSelect) {
            if(numOfCardsToSelect == 2)
                Accusation.suggest(cluedo, parent);
            else if(numOfCardsToSelect == 3)
                Accusation.accuse(cluedo, parent);
        }
        dispose();
    }


}
