/*
 * Allows to add items with certain priorities, and then be able to 
 * extract the item with the lowest priority
 *
 * @author Sulaiman Lateef
 * sulaimanlateef@brandeis.edu
 * December 10, 2023
 * COSI 21A PA3
 */

package main;
import java.util.ArrayList;
import java.util.List;

public class MinPriorityQueue {
    private final List<GraphNode> nodes = new ArrayList<>();
    private final HashMap nodeToIndex = new HashMap();

    /**
     * Inserts g into the queue with its priority.
     *
     * @param g GraphNode to be inserted.
     */
    
    public void insert(GraphNode g) {
        int index = nodes.size();
        nodes.add(g);
        nodeToIndex.set(g, index);
        fixUp(index, g);
    }

    /**
     * return and remove from the priority queue the
	 * GraphNode with the highest priority in the queue.
     *
     * @return GraphNode with the highest priority, or null if the queue is empty.
     */
    
    public GraphNode pullHighestPriorityElement() {
        if (isEmpty()) {
            return null;
        }
        GraphNode top = nodes.get(0);
        nodeToIndex.set(top, -1);
        GraphNode last = nodes.remove(nodes.size() - 1);
        if (!nodes.isEmpty()) {
            nodes.set(0, last);
            nodeToIndex.set(last, 0);
            fixDown(0, last);
        }
        return top;
    }

    /**
     * calls the heapify method
     *
     * @param g GraphNode to be rebalanced.
     */
    
    // Assume we are only going to decrease the key.
    public void rebalance(GraphNode g) {
        int index = nodeToIndex.getValue(g);
        fixUp(index, g);
    }
    
    /**
     * Checks if the MinPriorityQueue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Fixes the heap property by moving a GraphNode up the heap.
     *
     * @param index index of the GraphNode to be fixed.
     * @param g     GraphNode to be moved.
     */
    
    private void fixUp(int index, GraphNode g) {
        int oldIndex = index;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            GraphNode parent = nodes.get(parentIndex);
            if (parent.priority <= g.priority) {
                break;
            }
            nodes.set(index, parent);
            nodeToIndex.set(parent, index);
            index = parentIndex;
        }
        if (index != oldIndex) {
            nodes.set(index, g);
            nodeToIndex.set(g, index);
        }
    }

    /**
     * Fixes the heap property by moving a GraphNode down the heap.
     *
     * @param index index of the GraphNode to be fixed.
     * @param g     GraphNode to be moved.
     */
    
    private void fixDown(int index, GraphNode g) {
        int oldIndex = index;
        for (int childIndex = findSmallerChild(index); childIndex != -1; childIndex =
                findSmallerChild(index)) {
            GraphNode child = nodes.get(childIndex);
            if (g.priority <= child.priority) {
                break;
            }
            nodes.set(index, child);
            nodeToIndex.set(child, index);
            index = childIndex;
        }
        if (index != oldIndex) {
            nodes.set(index, g);
            nodeToIndex.set(g, index);
        }
    }

    /**
     * Finds the index of the smaller child of a given node in the heap.
     *
     * @param index index of the parent node.
     * @return The index of the smaller child, or -1 if there is no child.
     */
    
    private int findSmallerChild(int index) {
        int childIndex = index * 2 + 1;
        if (childIndex >= nodes.size()) {
            return -1;
        }
        if (childIndex + 1 < nodes.size()
                && nodes.get(childIndex + 1).priority < nodes.get(childIndex).priority) {
            return childIndex + 1;
        }
        return childIndex;
    }
}
