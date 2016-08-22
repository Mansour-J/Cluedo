package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

import cluedo.Cluedo;

/**
 * Created by Adam on 14/08/16.
 * This simulates a dice roll. The value rolled is the number of steps the player can move in one turn.
 */
public class DiceDialogue extends JDialog implements ActionListener {

    private Cluedo cluedo;
    private JButton dice1;
    private JButton dice2;

    public final String pathToDice = "images/dice/dice";

    private int dice1Value;
    private int dice2Value;
    private Timer timer;

    /**
     * Construct a new dice
     * @param parent
     * @param cluedo
     */
    public DiceDialogue(JFrame parent, Cluedo cluedo) {

        super(parent, "Dice Roll", false);
        setSize(300, 300);
        setVisible(true);
        setLayout(new GridLayout(0, 2));

        this.cluedo = cluedo;

        dice1 = new JButton(new ImageIcon(pathToDice + 4 + ".png"));
        dice1.setSize(10, 10);
        dice2 = new JButton(new ImageIcon(pathToDice + 4 + ".png"));
        dice2.setSize(10, 10);

        dice1.addActionListener(this);
        dice2.addActionListener(this);
        add(dice1);
        add(dice2);

        dice1.setVisible(true);
        dice2.setVisible(true);
        dice1.setBorder(BorderFactory.createEmptyBorder());
        dice2.setBorder(BorderFactory.createEmptyBorder());
        run();
    }

    /**
     * Run the dice animation until the user clicks on a die.
     */
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
    /**
     * Oop would you look at that?
     * What?
     * A die was clicked!
     * I bet you aren't even reading this
     */
    public void actionPerformed(ActionEvent e) {
        // Stop timer and show dice for 100 milli-sec
        timer.stop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        // Remove buttons
        dice1.setVisible(false);
        dice2.setVisible(false);
        remove(dice1);
        remove(dice2);

        int total = dice1Value + dice2Value;

        setLayout(new GridLayout(0, 1)); // We only want one item displayed

        // Create text field and set font and match size with parent
        JTextField textField = new JTextField("You rolled: " + total);
        Font bigFont = textField.getFont().deriveFont(20f);
        textField.setFont(bigFont);
        textField.setSize(300,300);

        // Align in centre and remove the border, and add it
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(BorderFactory.createEmptyBorder());
        add(textField);
        cluedo.updateDiceRoll(total);

        cluedo.canPlayerMove = true;
        cluedo.hasPlayerRolledDice = true;

        dispose();
    }
}
