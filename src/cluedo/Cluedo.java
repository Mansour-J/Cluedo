package cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import squares.Square;
import util.*;
import view.Board;

/**
 * Created by Adam on 25/07/16.
 * Cluedo is the main class and is responsible for running of the game, and calls the appropriate methods in other classes.
 */
public class Cluedo {

    private Game currentGame;
    private List<Player> players;
    private Board board;
    private Player currentPlayer;

    private int diceRoll;
    public boolean readyToMovePlayer = false;

    /**
     * Construct a new game of Cluedo
     */
    public Cluedo() {

    }

    /**
     * Starts a new Game, gets the number of players, sets the characters, creates a random solution, distributes the
     * remaining cards to all the players.
     */
    public void setupGame(List<CharacterCard.Character> choosenCharacters) {
        System.out.println(choosenCharacters);
        // Create the Card objects and init the player list
        List<CharacterCard> characters = CharacterCard.generateObjects();
        List<WeaponCard> weapons = WeaponCard.generateObjects();
        List<RoomCard> rooms = RoomCard.generateObjects();

        // Set up players
        choosenCharacters.forEach(p -> characters.remove(p));
        this.players = new ArrayList<>();
        choosenCharacters.forEach(p -> players.add(new Player(p)));

        // Shuffle the stack
        Collections.shuffle(characters);
        Collections.shuffle(weapons);
        Collections.shuffle(rooms);

        // Set up the solution
        List<Card> solution = new ArrayList<>();

        // Add random character
        int random = (int) (Math.random() * characters.size());
        solution.add(characters.get(random));

        // Add random room
        random = (int) (Math.random() * rooms.size());
        solution.add(rooms.remove(random));

        // Add random weapon
        random = (int) (Math.random() * weapons.size());
        solution.add(weapons.remove(random));

        // Deal Cards
        Card.dealCards(weapons, players);
        Card.dealCards(rooms, players);
        Card.dealCards(characters, players);

        // Create Game
        this.currentGame = new Game(players, solution, weapons, rooms, characters);
        this.board = currentGame.getBoard();
        this.currentPlayer = players.get(0);

    }

    public void updateDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
    }

    public void movePlayer(int x, int y) throws CluedoError {
        if(diceRoll == 0)
            return;

        if (currentPlayer == null)
            throw new CluedoError("No player to move");

        // Set start and end points
        Point start = new Point(currentPlayer.x(), currentPlayer.y());
        Point end = new Point(x, y);

        // Find path
        System.out.println("Finding: " + start.toString() + " " + end.toString());
        List<Point> path = PathFinder.findPath(board, start, end);
        System.out.println(path.toString());

        // Check path length is valid
        if (path.size() > diceRoll)
            throw new CluedoError("You cannot move that many steps");

        // Update dice roll
        this.diceRoll -= path.size();
        if(this.diceRoll == 0)
            readyToMovePlayer = false;

        // Update the players path
        for (Point p : path) {
            currentPlayer.setPos(p.getX(), p.getY());
        }
    }

    /**
     * Adds a message to the current players Journal. The current player should not be null when this is called.
     *
     * @param message
     */
    public void addToCurrentPlayersJournal(String message) {
        assert currentPlayer != null;
        currentPlayer.getJournal().addToJournal(message);
    }

    /**
     * Advance the currentPlayer to be the next player. This is the player after the the currentPlayer in players
     */
    public void nextPlayer() {
        if (currentPlayer == null) {
            currentPlayer = players.get(0);
        } else {
            int index = players.indexOf(currentPlayer);
            index = (index == players.size() - 1) ? 0 : index + 1;
            currentPlayer = players.get(index);
        }
        assert currentPlayer != null;
    }


    /**
     * Constructs a new Cluedo Game
     *
     * @param args
     */
    public static void main(String args[]) {
        new Cluedo();
    }

    /* Getters and setters */

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Game getGame() {
        return this.currentGame;
    }

}