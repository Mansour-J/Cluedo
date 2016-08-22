package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import controller.Cluedo;
import controller.Player;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Adam on 8/08/16.
 * 
 * This is the entry point of Cluedo and is the frame for holding the canvas and menu pane.
 * 
 */
public class BoardFrame extends JFrame {

    public static final int BOARD_WIDTH = 730;
    public static final int BOARD_HEIGHT = 570;

    private final BoardCanvas canvas;
    private final Cluedo cluedo;

    private MenuPanel menuPanel;

    /**
     * Construct a new frame for holding a game of cluedo
     *
     * @param title
     * @param cluedo
     * @param keys
     */
    public BoardFrame(String title, Cluedo cluedo, KeyListener[] keys) {
        super(title);
        setLayout(new BorderLayout());
        this.cluedo = cluedo;
        this.canvas = new BoardCanvas(cluedo, this);
        this.menuPanel = new MenuPanel(this, cluedo);
        add(menuPanel, BorderLayout.LINE_END);

        // Display window
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setVisible(true);
        setResizable(true);
        pack();

        // Title and Menu bar
        setTitle("Cluedo");
        setMenuBar();

        // Layout
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.requestFocus();

        CharacterDialouge dialog = new CharacterDialouge(this, cluedo);
    }


    /**
     * Initilises the menu bar
     */
    public void setMenuBar() {
        JMenuBar menuBar;
        JMenu menu;

        // Create the menu bar.
        menuBar = new JMenuBar();

		/* Init the file menu. */
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        // File menu items
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setMnemonic(KeyEvent.VK_X);
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu.add(newGame);

        newGame.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
            Cluedo cluedo1 = new Cluedo();
            BoardFrame ex = new BoardFrame("Adam", cluedo1, new KeyListener[0]);
            ex.setVisible(true);
        }));

        // Quit
        JMenuItem cbMenuItem = new JMenuItem("Quit");
        cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cbMenuItem.setMnemonic(KeyEvent.VK_X);
        menu.add(cbMenuItem);

        cbMenuItem.addActionListener(arg0 -> {
            int action = JOptionPane.showConfirmDialog(BoardFrame.this,
                    "Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

            if (action == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        menu.add(cbMenuItem);

        // Option Menu
        menu = new JMenu("Options");

        // Sound
        cbMenuItem = new JCheckBoxMenuItem("Enable Sound");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }


    public void repaint() {
        canvas.repaint();
    }

    /**
     * Sets the text pane to hold the text informing the user whos turn it is and what position they are at.
     */
    public void setCurrentPlayerText() {
        Player p = cluedo.getCurrentPlayer();
        String t = "Its now: \n" + p.getCharacter().toString() + "'s turn. \n" + "You are at position: " + p.x() + ", "
                + p.y();
        menuPanel.getTextArea().setText(t);
    }

    /**
     * Set the text pane to have the text 'text'
     *
     * @param text
     */
    public void setDisplayText(String text) {
        if (menuPanel != null) {
            menuPanel.getTextArea().setText(text);
        }
    }

    /**
     * LEEEEEEEEEEEETS RUN CLUUUUUUUUUUUUUUUUUUUUUEDO!
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Cluedo cluedo = new Cluedo();
            BoardFrame ex = new BoardFrame("Adam", cluedo, new KeyListener[0]);
            ex.setVisible(true);
        });
    }
}