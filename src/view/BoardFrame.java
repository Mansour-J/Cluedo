package view;

import cluedo.Cluedo;
import cluedo.Player;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Adam on 8/08/16.
 */
public class BoardFrame extends JFrame {

	public static final int BOARD_WIDTH = 700;
	public static final int BOARD_HEIGHT = 553;

	private final BoardCanvas canvas;
	private final Cluedo cluedo;
	private final Toolkit toolkit;

	private MenuPanel menuPanel;

	public BoardFrame(String title, Cluedo cluedo, KeyListener[] keys) {
		super(title);
		setLayout(new BorderLayout());

		this.toolkit = getToolkit();
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

		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				EventQueue.invokeLater(() -> {
					Cluedo cluedo = new Cluedo();
					BoardFrame ex = new BoardFrame("Adam", cluedo, new KeyListener[0]);
					ex.setVisible(true);
				});

			}
		});

		// Quit
		JMenuItem cbMenuItem = new JMenuItem("Quit");
		cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cbMenuItem.setMnemonic(KeyEvent.VK_X);

		menu.add(cbMenuItem);

		cbMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(BoardFrame.this,
						"Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
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

	public void setCurrentPlayerText() {
		Player p = cluedo.getCurrentPlayer();
		String t = "Its now: \n" + p.getCharacter().toString() + "'s turn. \n" + "You are at position: " + p.x() + ", "
				+ p.y();
		menuPanel.getTextArea().setText(t);
	}

	public MenuPanel getMenuPannel() {
		return this.menuPanel;
	}

	public void setDisplayText(String text){
		if(menuPanel != null){
			menuPanel.getTextArea().setText(text);
		}
	}

	public BoardCanvas getCanvas() {
		return canvas;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Cluedo cluedo = new Cluedo();
			BoardFrame ex = new BoardFrame("Adam", cluedo, new KeyListener[0]);
			ex.setVisible(true);
		});
	}

}