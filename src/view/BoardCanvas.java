package view;

import util.CluedoError;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.Cluedo;
import controller.Player;

/**
 * The board canvas is responsible for drawing the game.
 */
public class BoardCanvas extends Canvas {
    private static final String IMAGE_PATH = "images/";
    private static final String CHARACTER_IMAGE_PATH = "images/character_tokens/";


    private static final String[] preferredFonts = {"Courier New", "Arial", "Times New Roman"};
    private Font font;
    private Cluedo cluedo;
    private Image boardImage;
    private BoardFrame parent;

    int xClick;
    int yClick;

    /**
     * Create a new board canvas
     * @param cluedo
     * @param parent
     */
    public BoardCanvas(Cluedo cluedo, BoardFrame parent) {
        this.cluedo = cluedo;
        this.parent = parent;
        this.boardImage = loadImage("boardImage.png");
        setVisible(true);
        repaint();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!cluedo.hasPlayerRolledDice) {
                    JOptionPane.showMessageDialog(parent, "You must roll the dice before you can move", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(cluedo.getDiceRoll() == 0){
                    JOptionPane.showMessageDialog(parent, "You have no steps left to move", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                repaint();
                int x = e.getX() / 20;
                int y = e.getY() / 20;
                xClick = x;
                yClick = y;
                try {
                    cluedo.movePlayer(x, y);
                    parent.setDisplayText("You have " + cluedo.getDiceRoll() + " steps left to move");
                    repaint();

                }catch (CluedoError error){
                    JOptionPane.showMessageDialog(parent, error.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    /**
     * re-draw the graphics
     */
    public void repaint() {
        paint(getGraphics());
    }

    @Override
    public void paint(Graphics g) {
        if(g == null)
            return;

        g.fillRect(xClick, yClick, 20, 20);
        g.drawImage(boardImage, 0, 0, null);
        drawCharacterTokens(g);
    }

    /**
     * Draws the characters tokens on top of the image on the board
     * @param g
     */
    private void drawCharacterTokens(Graphics g) {
        if (cluedo.getGame() == null || cluedo.getGame().getPlayers() == null)
            return;

        for (Player player : cluedo.getGame().getPlayers()) {
            int x = player.x();
            int y = player.y();
            Image img = loadImage(player.getCharacter().toString() + ".png");
            g.drawImage(img, x * 20, y * 20, 20, 20, null);
        }
    }


    /**
     * Load an image from the file system, using a given filename.
     * @param filename
     * @return
     */
    public static Image loadImage(String filename) {
        try {
            if (filename.contains("boardImage"))
                return ImageIO.read(new File(IMAGE_PATH + filename));

            return ImageIO.read(new File(CHARACTER_IMAGE_PATH + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
