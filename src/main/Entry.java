/*
 * Entry class for storing key value pairs in a hashmap.
 *
 * @author Sulaiman Lateef
 * sulaimanlateef@brandeis.edu
 * December 10, 2023
 * COSI 21A PA3
 */

package main;
public class Entry {
    public final GraphNode key;
    public int value;
    public Entry next;

    /**
     * Constructs a new Entry with the specified key.
     *
     * @param key GraphNode key for entry.
     */
    
    public Entry(GraphNode key) {
        this.key = key;
    }
}