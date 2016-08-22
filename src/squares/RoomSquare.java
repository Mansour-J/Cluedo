package squares;

import cards.RoomCard;
import cluedo.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The room square is a square on the board that represents a room
 */
public class RoomSquare extends Square implements Walkable{
    private RoomCard.Room name;

    /**
     * Construct a new roomn square
     * @param name
     */
    public RoomSquare(RoomCard.Room name){
        this.name = name;
    }

    @Override
    public String getKey() {
        return "R";
    }

    public RoomCard.Room getName(){ return this.name;}

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        if(fromSquare instanceof StairSquare || fromSquare instanceof DoorSquare || fromSquare instanceof RoomSquare)
            return true;
        return false;
    }
}
