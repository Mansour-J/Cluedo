package util;

/**
 * Created by Adam on 29/07/16.
 * A basic point that contains an x and y point.
 */
public class Point {
    private int x;
    private int y;

    /**
     * Create a new Point
     * @param x
     * @param y
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
