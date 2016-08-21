// This file is part of the Multi-player Pacman Game.
//
// Pacman is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published
// by the Free Software Foundation; either version 3 of the License,
// or (at your option) any later version.
//
// Pacman is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
// the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public
// License along with Pacman. If not, see <http://www.gnu.org/licenses/>
//
// Copyright 2010, David James Pearce.
// UPDATED 03/06/2010, Terrence Miller

package view;

import cluedo.Cluedo;
import cluedo.Game;
import cluedo.Player;
import util.CluedoError;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The board canvas is responsible for drawing the game. Currently, it uses a
 * relatively primitive form of double buffering to ensure there's no flicker
 * during frame updates. This class also generates a number of images using
 * Java's graphics capabilities, which saves having to have lots of very similar
 * images for the different directions.
 *
 * @author djp
 */
public class BoardCanvas extends Canvas {
    private static final String IMAGE_PATH = "images/";
    private static final String CHARACTER_IMAGE_PATH = "images/character_tokens/";


    private static final String[] preferredFonts = {"Courier New", "Arial", "Times New Roman"};
    private Font font;
    private Cluedo cluedo;
    private Image boardImage;

    int xClick;
    int yClick;

    public BoardCanvas(Cluedo cluedo) {
        this.cluedo = cluedo;
       /* this.boardImage = loadImage("boardImageMain.jpg");*/
        this.boardImage = loadImage("boardImage.png");
        setVisible(true);
        repaint();


        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!cluedo.readyToMovePlayer)
                    return;

                int x = e.getX() / 20;
                int y = e.getY() / 20;
                xClick = x;
                yClick = y;
                try {
                    cluedo.movePlayer(x, y);
                    cluedo.readyToMovePlayer = false;
                    repaint();
                }catch (CluedoError error){
                    error.printStackTrace();
                    // TODO
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
     * Rotate an image a given number of degrees.
     *
     * @param src
     * @param angle
     * @return
     */
    public static Image rotate(Image src, double angle) {
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.rotate(Math.toRadians(angle), width / 2, height / 2);
        g.drawImage(src, 0, 0, width, height, null);
        g.dispose();
        return img;
    }


    /**
     * Load an image from the file system, using a given filename.
     *
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
