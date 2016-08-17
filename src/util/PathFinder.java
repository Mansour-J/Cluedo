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
     * Finds the shortest path between two tiles
     *
     * @param brd
     * @param fromTile
     * @param toTile
     * @return
     */
    public static List<Square> findPath(Board brd, Square fromTile, Square toTile) {
        board = brd;

        Set<Square> visited = new HashSet<>();
        Queue<SearchNode> fringe = new PriorityQueue<>();
        fringe.add(new SearchNode(fromTile, null, toTile, 0));
        SearchNode current = null;

        //Perform the A* search
        while (!fringe.isEmpty()) {
            current = fringe.poll();

            if (current.node == current.target) // At the destination
                break;

            // Have we visited it before
            if (visited.contains(current.node))
                continue;
            else
                visited.add(current.node);

            // Search surrounding tiles, and add them to the fringe if the paths valid
            for (Board.Direction d : Board.Direction.values()) {
                Square next = board.getTile(current.node, d);

                if (next != null && next.isVaccantSquare() && current.node.isTransitionAllowed(next)) {
                    if(next == null)
                        throw new CluedoError("Shouldnt be null");
                    fringe.add(new SearchNode(next, current, current.target, current.dist + 1));
                }
            }
        }

        // No path found, so throw an exception
        if (current == null || current.node != current.target)
            throw new CluedoError("No Path Found");


        // Found a path lets follow the links backwards to build the path list
        List<Square> path = new ArrayList<>();
        while (current.parent != null) {
            path.add(0, current.node);
            current = current.parent;
        }
        return path;
    }


    private static class SearchNode implements Comparable{
        public Square node;
        public SearchNode parent;
        public Square target;
        public double cost;
        public double dist;

        public SearchNode(Square node, SearchNode parent, Square target, double dist) {
            assert node != null && target != null;
            this.node = node;
            this.parent = parent;
            this.dist = dist;
            this.target = target;
            if(node == null || target == null)
                throw new CluedoError("Shouldnt be null");
            this.cost = board.cost(node, target);
        }

        public double heuristic(){
            return dist + cost;
        }

        public int compareTo(Object o) {
            if(o instanceof SearchNode){
                SearchNode s = (SearchNode) o;
                return (int)(this.heuristic() - s.heuristic());
            }
            return -1;
        }
    }
}
