package squares;

/**
 * Created by Adam on 29/07/16.
 * This is a type of square that players spawn on
 */
public class SpawnSquare extends Square {

    private int x;
    private int y;


    /**
     * Construct a new spawn square
      * @param x
     * @param y
     */
    public SpawnSquare(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String getKey() {
        return "S";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        if(fromSquare instanceof HallwaySquare)
            return true;
        return false;
    }
}
