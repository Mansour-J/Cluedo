package util;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import cluedo.Cluedo;
import view.AccusationDialog;
import view.Board;
import cluedo.Game;
import cluedo.Player;
import squares.RoomSquare;
import view.GenericDialogue;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam on 31/07/16.
 * Util to help Cluedo with making an accusation
 */
public class Accusation {

    public static List<Card> selectedCards = new ArrayList<>();

    /**
     * Accuse a player of the crime
     */
    public static void accuse(Player currentPlayer, Cluedo cluedo, JFrame parent) {
        Game currentGame = cluedo.getGame();
        Board board = currentGame.getBoard();
        List<Card> solution = getWeaponCharacterRoom(currentPlayer, cluedo, parent);

        // Check solution
        if (currentGame.accuse(solution)) { // They are correct
            System.out.println("Well done, " + currentPlayer.getCharacter().toString() + ", you have won the game!");
            currentGame.finish();

        } else { // Incorrect
            System.out.println("You made an invalid accusation, you are now eliminated.");
            currentPlayer.elimate();
        }
    }

    /**
     * Suggest a player of the crime
     */
    public static void suggest(Player currentPlayer, Cluedo cluedo, JFrame parent) {
        Game currentGame = cluedo.getGame();
        List<Card> solution = getWeaponCharacterRoom(currentPlayer, cluedo, parent);
        Card cardProvedWrong = proveSolutionWrong(solution, currentGame.getPlayers());

        // Check solution
        if (cardProvedWrong == null) { // They are correct
            System.out.println("No one was able to disprove your suggestion.");

        } else  // Incorrect
            System.out.println("Incorrect suggestion. A player has card: " + cardProvedWrong.toString());
    }

    /**
     * Assists the Suggest and Accuse methods by getting the players room, and what weapon they believed killed another
     * character.
     *
     * @return A list of cards with length = 3, and contains the current room the player is in, the weapon they believe
     * killed the character, and the character that died
     */
    public static List<Card> getWeaponCharacterRoom(Player currentPlayer, Cluedo cluedo, JFrame parent) {

        // Character
        List<CharacterCard> characterCards = CharacterCard.generateObjects();
        final List<Card> cards = new ArrayList<>();
        for(CharacterCard c : characterCards)
            cards.add(c);
        new GenericDialogue(parent, cards, cluedo);
        while (selectedCards.size() == 0) {
            // Do nothing
            int index = 0;
            index++;
        }

        Card characterAccused = selectedCards.get(0);

        // Weapon
        List<WeaponCard> weaponCards = WeaponCard.generateObjects();
        final List<Card> cards2 = new ArrayList<>();
        weaponCards.forEach(c -> cards2.add(c));
        new AccusationDialog(parent, cards2, cluedo);
        while (selectedCards.size() == 1) {
            // Do nothing
        }
        Card weaponAccused = selectedCards.get(1);

        // RoomSquare
        int playerX = currentPlayer.x();
        int playerY = currentPlayer.y();
        RoomCard roomAccused = null;
        try {
            RoomSquare roomSquare = cluedo.getGame().getBoard().getRoom(playerX, playerY);
            roomAccused = new RoomCard(roomSquare.getName());

        } catch (CluedoError e) {
            System.out.println(e.getMessage());
            return Arrays.asList(); // Can't accuse if not in the room
        }

        // Return the three cards
        return Arrays.asList(characterAccused, weaponAccused, roomAccused);
    }

    /**
     * Prove the solution the currentPlayer provided to be wrong.
     *
     * @param solution the three solution cards
     * @param players  list of players in the game
     * @return The card that proved the player wrong, or null if they are correct
     */
    public static Card proveSolutionWrong(List<Card> solution, List<Player> players) {
        for (Player p : players) { // loop over the players excluding the currentPLayer
            List<Card> playersCards = p.getCards();
            for (Card c1 : playersCards) {
                for (Card c2 : solution)
                    if (c1.equals(c2))
                        return c1;
            }
        }
        return null;
    }
}
