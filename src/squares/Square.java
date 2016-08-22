package squares;

import util.CluedoError;
import cluedo.Player;

/**
 * Created by Adam on 25/07/16.
<<<<<<< HEAD
=======
 * This is a basic square that all tiles on the board extend. This is so we have some basic methods that all squares
 * can utilise and we can provide a type for the 2d array.
>>>>>>> e80fc3545cca43dd45f6438fd1d8a0133134105a
 */
public abstract class Square {
    private Player player;

    public abstract String getKey();

    /**
     * Set the player in the square
<<<<<<< HEAD
     *
=======
>>>>>>> e80fc3545cca43dd45f6438fd1d8a0133134105a
     * @param p
     */
    public void enterSquare(Player p) throws CluedoError {
        if (player != null)
            throw new CluedoError("You cannot enter that square as there is already a player in it");
        player = p;
    }

    /**
     * Remove the player from the square
<<<<<<< HEAD
     *
=======
>>>>>>> e80fc3545cca43dd45f6438fd1d8a0133134105a
     */
    public void leaveSquare() {
        player = null;
    }

    public boolean isVaccantSquare(){ return player == null; }

    public abstract boolean isTransitionAllowed(Square fromSquare);

    @Override
    public boolean equals(Object obj) {
       return this == obj;
    }
}
