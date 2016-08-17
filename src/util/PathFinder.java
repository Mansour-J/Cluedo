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
        /*
		 *This is a modified A* search, where we can have multiple start and finish nodes,
		 *and we only return the shortest route between the combination of them.
		 *
		 *For this reason, we have to maintain multiple visited sets, as we could be searching
		 *the same node multiple times from different paths.
		 */

        board = brd;

        Map<Square, Set<Square>> visitMap = new HashMap<>();
        List<SearchNode> fringe = new ArrayList<>();
        fringe.add(new SearchNode(fromTile, null, toTile, 0));
        SearchNode current = null;

        //Perform the A* search
        while (!fringe.isEmpty()) {
            current = fringe.get(0);

            if (current.node == current.target)
                break;

            Set<Square> visited = visitMap.get(current.target);
            if(visited == null)
                visited = new HashSet<>();

            fringe.remove(current);
            if (!visited.add(current.node)) // Have we visited it before?
                continue;


            // Search surrounding tiles, and add them to the fringe
            for (Board.Direction d : Board.Direction.values()) {
                Square t = board.getTile(current.node, d);
                if (t != null && t.isVaccantSquare() && current.node.isTransitionAllowed(t))
                    fringe.add(new SearchNode(t, current, current.target, current.dist + 1));

            }
        }
        List<Square> path = new ArrayList<>();

        // If it didn't reach the node, there is no path available, so return an empty path
        if (current == null || current.node != current.target) {
            return path;
        }

        // Follow the links backwards to build the path list
        while (current.parent != null) {
            path.add(0, current.node);
            current = current.parent;
        }
        return path;
    }

    private static class SearchNode {
        public Square node;
        public SearchNode parent ;
        public Square target;
        public double cost;
        public double dist;

        public SearchNode(Square node, SearchNode parent, Square target, double dist) {
            this.node = node;
            this.parent = parent;
            this.dist = dist;
            this.target = target;
            this.cost = board.cost(node, target);
        }

        public double heuristic() {
            return dist + cost;
        }
    }
}
