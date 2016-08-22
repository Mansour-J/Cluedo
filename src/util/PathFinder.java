package util;

import cards.RoomCard;
import squares.RoomSquare;
import squares.Square;
import view.Board;

import java.util.*;

/**
 * Created by Adam on 14/08/16.
 */
public class PathFinder {

    static Board board;

    /**
     * Finds a path from the source to the destination using a modified version of A* so it works in a 2D array.
     * @param brd
     * @param src
     * @param dest
     * @return
     */
    public static List<Point> findPath(Board brd, Point src, Point dest) {
        board = brd;

        Set<Point> visited = new HashSet<>();
        Queue<SearchNode> fringe = new PriorityQueue<>();
        fringe.add(new SearchNode(src, null, dest, 0));
        SearchNode current = null;

        //Perform the A* search
        while (!fringe.isEmpty()) {
            current = fringe.poll();

            if (current.node.equals(current.target)) // At the destination
                break;

            // Have we visited it before
            Point c = current.node;
            Square currentNode = brd.getBoard()[c.getX()][c.getY()];
            if (visited.contains(current.node)) {
                continue;
            } else
                visited.add(current.node);

            // Search surrounding tiles, and add them to the fringe if the paths valid
            for (Board.Direction d : Board.Direction.values()) {
                Point next = board.getTile(current.node, d);

                if (next == null)
                    continue;

                Square nextSquare = board.getBoard()[next.getX()][next.getY()];
                if (nextSquare != null &&
                        nextSquare.isVaccantSquare() &&
                        currentNode.isTransitionAllowed(nextSquare)) {
                    fringe.add(new SearchNode(next, current, current.target, current.dist + 1));
                }
            }
        }

        // No path found, so throw an exception
        if (current == null || !current.node.equals(current.target))
            throw new CluedoError("No path found, are you moving to a correct square?");

        // Found a path lets follow the links backwards to build the path list
        List<Point> path = new ArrayList<>();
        while (current.parent != null) {
            Point p = new Point( current.node.getX() , current.node.getY());
            path.add(0, p); // Add to start
            current = current.parent;
        }
        return path;
    }


    /**
     * This is a static inner class used for assisting with the searching paths on the board
     */
    private static class SearchNode implements Comparable {
        public Point node;
        public SearchNode parent;
        public Point target;
        public double cost;
        public double dist;

        public SearchNode(Point node, SearchNode parent, Point target, double dist) {
            assert node != null && target != null;
            this.node = node;
            this.parent = parent;
            this.dist = dist;
            this.target = target;
            if (node == null || target == null)
                throw new CluedoError("Shouldn't be null");
            this.cost = board.cost(node, target);
        }

        public double heuristic() {
            return dist + cost;
        }

        public int compareTo(Object o) {
            if (o instanceof SearchNode) {
                SearchNode s = (SearchNode) o;
                return (int) (this.heuristic() - s.heuristic()); // TODO is this correct?
            }
            return -1;
        }
    }
}
