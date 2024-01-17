/*
 * FindMinPath class that finds the minimum path in a graph using Dijkstra's algorithm.
 *
 * @author Sulaiman Lateef
 * sulaimanlateef@brandeis.edu
 * December 10, 2023
 * COSI 21A PA3
 */

package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FindMinPath {
	/**
     * main method that initiates the graph, performs Dijkstra's algorithm, and prints the minimum path to a file.
     *
     * @param args
     * @throws FileNotFoundException If the specified file for output is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        GraphWrapper gw = new GraphWrapper(true);
        GraphNode home = gw.getHome();
        home.priority = 0;

        MinPriorityQueue queue = new MinPriorityQueue();
        HashMap visited = new HashMap();
        queue.insert(home);
        visited.set(home, 0);
        GraphNode goal = null;

        while (!queue.isEmpty()) {
            GraphNode cur = queue.pullHighestPriorityElement();
            if (cur.isGoalNode()) {
                goal = cur;
                break;
            }
            if (cur.hasNorth()) {
                addNeighbor(cur, cur.getNorth(), "North", cur.getNorthWeight(), visited, queue);
            }
            if (cur.hasSouth()) {
                addNeighbor(cur, cur.getSouth(), "South", cur.getSouthWeight(), visited, queue);
            }
            if (cur.hasEast()) {
                addNeighbor(cur, cur.getEast(), "East", cur.getEastWeight(), visited, queue);
            }
            if (cur.hasWest()) {
                addNeighbor(cur, cur.getWest(), "West", cur.getWestWeight(), visited, queue);
            }
        }

        if (goal == null) {
            // Print nothing has found.
            return;
        }

        List<String> directions = new ArrayList<>();
        for (GraphNode cur = goal; cur != home; cur = cur.previousNode) {
            directions.add(cur.previousDirection);
        }

        try (PrintWriter pw = new PrintWriter(new File("answer.txt"))) {
            for (int i = directions.size() - 1; i >= 0; --i) {
                pw.println(directions.get(i));
            }
        }
    }
    
    /**
     * Adds a neighbor to the priority queue and updates its properties if a shorter path is found.
     *
     * @param cur        current node.
     * @param neighbor   neighboring node to be added.
     * @param direction  direction to the neighboring node.
     * @param weight     weight of the edge to the neighboring node.
     * @param visited    hashmap to keep track of visited nodes.
     * @param queue      minimum priority queue
     */
    private static void addNeighbor(GraphNode cur, GraphNode neighbor, String direction, int weight,
            HashMap visited, MinPriorityQueue queue) {
        int priority = cur.priority + weight;
        boolean hasVisited = visited.hasKey(neighbor);
        if (hasVisited && neighbor.priority <= priority) {
            return;
        }
        neighbor.priority = priority;
        neighbor.previousNode = cur;
        neighbor.previousDirection = direction;
        if (!hasVisited) {
            queue.insert(neighbor);
            visited.set(neighbor, 0);
        } else {
            queue.rebalance(neighbor);
        }
    }
}
