package util;

import cards.Card;
import cards.RoomCard;
import cluedo.Cluedo;
import view.Board;
import cluedo.Game;
import cluedo.Player;
import squares.RoomSquare;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 31/07/16.
 * Util to help Cluedo with making an accusation
 */
public class Accusation {

    /**
     * Current cards in the accusation. 2 cards for suggest (automatically adds the 3rd being the players current room),
     * 3 cards for suggest.
     */
    public static List<Card> selectedCards = new ArrayList<>();

    /**
     * Accuse a player of the crime
     */
    public static void accuse(Cluedo cluedo, JFrame parent) throws CluedoError{
        // Should have character, weapon and room card in selectedCards
        if(selectedCards.size() != 3)
            throw new CluedoError("Should be three cards here");

        Game currentGame = cluedo.getGame();
        if (currentGame.accuse(selectedCards)) {
            // They won the game
            currentGame.finish();
            JOptionPane.showMessageDialog(parent, "Congratulations, you won the game!"
                    , "Incorrect Accusation", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } else {
            // Incorrect accusation
            cluedo.getPlayers().remove(cluedo.getCurrentPlayer());
            JOptionPane.showMessageDialog(parent, "Sorry, that was an invalid accusation, you are now eliminated from the game."
                    , "Incorrect Accusation", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Suggest a player of the crime
     */
    public static void suggest(Cluedo cluedo, JFrame parent) {
        // Should have character and weapon card in selectedCards
        if(selectedCards.size() != 2)
            throw new CluedoError("Should be two cards here");

        Game currentGame = cluedo.getGame();
        try {
            selectedCards.add(getPlayersCurrentRoomCard(cluedo)); // Add the players current room
        }catch(CluedoError error){
            JOptionPane.showMessageDialog(parent, error.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Card cardProvedWrong = proveSolutionWrong(selectedCards, currentGame.getPlayers());

        // Check solution
        if (cardProvedWrong == null) {
            // Correct suggestion
            JOptionPane.showMessageDialog(parent, "No one was able to disprove your suggestion", "Suggestion",
                    JOptionPane.PLAIN_MESSAGE);

        } else {
            // Incorrect suggestion
            System.out.println("Incorrect suggestion. A player has card: " + cardProvedWrong.toString());
            JOptionPane.showMessageDialog(parent, "Incorrect suggestion. A player has card: " + cardProvedWrong.toString(),
                    "Incorrect Suggestion", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Gets the players current room
     * @return
     */
    public static RoomCard getPlayersCurrentRoomCard(Cluedo cluedo) throws CluedoError {
        int playerX = cluedo.getCurrentPlayer().x();
        int playerY = cluedo.getCurrentPlayer().y();
        RoomSquare roomSquare = cluedo.getGame().getBoard().getRoom(playerX, playerY);
        return new RoomCard(roomSquare.getName());
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
