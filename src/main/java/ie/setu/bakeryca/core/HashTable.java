package ie.setu.bakeryca.core;

import java.io.Serializable;

// generic hash table using separate chaining for collisions
public class HashTable<V> implements Serializable {

    // each chain holds key/value pairs that are hashed to the same slot
    private class Entry implements Serializable {
        String key;
        V value;
        Entry(String key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int tableSize = 53;
    private LinkedList<Entry>[] hashTable; // array of chains. Each chain is a list of Entry's
    private int size;

    public HashTable() {
        hashTable = (LinkedList<Entry>[]) new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            hashTable[i] = new LinkedList<>();
        }
        size = 0;
    }

    // turns a string key into a slot index
    private int hashFunction(String key) {
        String lower = key.toLowerCase();
        int total = 0;
        for (int i = 0; i < lower.length(); i++) {
            total += lower.charAt(i);
        }
        return total % tableSize;
    }

    public void put(String key, V value) {
        int loc = hashFunction(key);
        LinkedList<Entry> chain = hashTable[loc];
        // look through the chain in case the key is already there
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key.equalsIgnoreCase(key)) {
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
    }

    public V get(String key) {
        int loc = hashFunction(key);
        LinkedList<Entry> chain = hashTable[loc];
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key.equalsIgnoreCase(key)) {
                return chain.get(i).value;
            }
        }
        return null;  // key doesn't exist
    }

    public boolean remove(String key) {
        int loc = hashFunction(key);
        LinkedList<Entry> chain = hashTable[loc];
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key.equalsIgnoreCase(key)) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }
}