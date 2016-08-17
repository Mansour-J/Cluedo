package view;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import cluedo.Cluedo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by javahemans on 14/08/16.
 */
public class DiceDialogue extends JDialog implements ActionListener {

    private Cluedo cluedo;
    private JButton dice1;
    private JButton dice2;

    public final String pathToDice = "images/dice/dice";

    private int dice1Value;
    private int dice2Value;
    private Timer timer;

    public DiceDialogue(JFrame parent, Cluedo cluedo) {

        super(parent, "Dice Roll", false);
        setSize(300, 300);
        setVisible(true);
        setLayout(new GridLayout(0, 2));

        dice1 = new JButton(new ImageIcon(pathToDice + 4 + ".png"));
        dice1.setSize(10,10);
        dice2 = new JButton(new ImageIcon(pathToDice + 4 + ".png"));
        dice2.setSize(10,10);


        dice1.addActionListener(this);
        dice2.addActionListener(this);
        add(dice1);
        add(dice2);
        dice1.setVisible(true);
        dice2.setVisible(true);
        run();
    }

    public void run() {
        timer = new Timer(200, e -> {
            dice1Value = (int) (Math.random() * 5) + 1;
            dice2Value = (int) (Math.random() * 5) + 1;

            System.out.println(dice1Value + " " + dice2Value);
            dice1.setIcon(new ImageIcon(pathToDice + dice1Value + ".png"));
            dice2.setIcon(new ImageIcon(pathToDice + dice2Value + ".png"));
        });
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();
        int result = dice1Value + dice2Value;
        System.out.println("Total " + result);
    }
}
