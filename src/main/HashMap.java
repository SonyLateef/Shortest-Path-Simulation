/*
 * Hashmap for adding items, and then testing if they are elements of the heap.
 *
 * @author Sulaiman Lateef
 * sulaimanlateef@brandeis.edu
 * December 10, 2023
 * COSI 21A PA3
 */

package main;
public class HashMap {
    private static final int INITIAL_CAPACITY_SHIFT = 4;
    private static final double MAX_LOAD_FACTOR = 0.6;

    private Entry[] entries = new Entry[1 << INITIAL_CAPACITY_SHIFT];
    private int capacityMask = entries.length - 1;
    private int maxSizeBeforeRehash = (int) Math.floor(entries.length * MAX_LOAD_FACTOR);
    private int size = 0;

    /**
     *  check the hashmap to see if there is an Entry for the GraphNode “key”, if there is, 
     *  change its value to “value”, otherwise, add it to the hashmap with that value.
     *
     * @param key   GraphNode key.
     * @param value integer value to be associated with the key.
     */
    
    public void set(GraphNode key, int value) {
        insertOrLookup(key).value = value;
    }

    /**
     * Gets the value for the entry with g as the key.
     *
     * @param g GraphNode key.
     * @return integer value associated with the key.
     * @throws NullPointerException If the key is not found in the HashMap.
     */
    
    public int getValue(GraphNode g) {
        return lookup(g).value;
    }
    
    /**
     * Checks if the specified key is present in the hashmap.
     *
     * @param g GraphNode key.
     * @return true if the hashmap has that key.
     */
    
    public boolean hasKey(GraphNode g) {
        return lookup(g) != null;
    }

    /**
     * Looks up an entry in the HashMap based on the key.
     *
     * @param key GraphNode key.
     * @return Entry associated with the key, or null if not found.
     */
    private Entry lookup(GraphNode key) {
        int bucket = key.getId().hashCode() & capacityMask;
        for (Entry entry = entries[bucket]; entry != null; entry = entry.next) {
            if (entry.key.getId().equals(key.getId())) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Inserts or looks up an entry in the hashmap based on the key.
     *
     * @param key GraphNode key.
     * @return Entry associated with the key, or a new entry if not found.
     */
    
    private Entry insertOrLookup(GraphNode key) {
        int bucket = key.getId().hashCode() & capacityMask;
        for (Entry entry = entries[bucket]; entry != null; entry = entry.next) {
            if (entry.key.getId().equals(key.getId())) {
                return entry;
            }
        }
        Entry newEntry = new Entry(key);
        newEntry.next = entries[bucket];
        entries[bucket] = newEntry;
        if (++size > maxSizeBeforeRehash) {
            rehash();
        }
        return newEntry;
    }

    /**
     * Rehashes the hashmap when the load factor is exceeded.
     */
    private void rehash() {
        Entry[] oldEntries = entries;
        entries = new Entry[entries.length * 2];
        capacityMask = entries.length - 1;
        maxSizeBeforeRehash = (int) Math.floor(entries.length * MAX_LOAD_FACTOR);
        for (Entry e : oldEntries) {
            for (Entry next; e != null; e = next) {
                next = e.next;
                int bucket = e.key.getId().hashCode() & capacityMask;
                e.next = entries[bucket];
                entries[bucket] = e;
            }
        }
    }
}
