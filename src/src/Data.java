package src;

import java.util.ArrayList;
//anotherDrug.state, anotherDrug.name, anotherDrug.claims, anotherDrug.aggregateCost
//A node of chains

class Data<Key, Value> {
    Key key;
    Value value;

    // Reference to next node
    Data<Key, Value> next;

    // Constructor
    public Data(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
}

// Class to represent entire hash table
class Map<Key, Value> {
    // bucketArray is used to store array of chains
    private ArrayList<Data<Key, Value>> bucketArray;
    // private ArrayList<String> bucketArray;
    // Current capacity of array list
    private int numBuckets;

    // Current size of array list
    private int size;

    // Constructor (Initializes capacity, size and
    // empty chains.
    public Map() {
        bucketArray = new ArrayList<>();
        numBuckets = 10;
        size = 0;

        // Create empty chains
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // This implements hash function to find index
    // for a key
    private int getBucketIndex(Key key) {
        int hashCode = key.hashCode() & 0x7FFFFFFF;
        int index = hashCode % numBuckets;
        return index;
    }

    // Method to remove a given key
    public Value remove(Key key) {
        // Apply hash function to find index for given key
        int bucketIndex = getBucketIndex(key);

        // Get head of chain
        Data<Key, Value> head = bucketArray.get(bucketIndex);

        // Search for key in its chain
        Data<Key, Value> prev = null;
        while (head != null) {
            // If Key found
            if (head.key.equals(key))
                break;

            // Else keep moving in chain
            prev = head;
            head = head.next;
        }

        // If key was not there
        if (head == null)
            return null;

        // Reduce size
        size--;

        // Remove key
        if (prev != null)
            prev.next = head.next;
        else
            bucketArray.set(bucketIndex, head.next);

        return head.value;
    }

    // Method find number of node in chain by a given key
    public int chainSize(Key key) {
        // Apply hash function to find index for given key
        int bucketIndex = getBucketIndex(key);
        int chainSize = 0;
        // Get head of chain
        Data<Key, Value> head = bucketArray.get(bucketIndex);
        // If key was not there
        if (head == null)
            chainSize = -1; // return -1 if there is no such item

        // Search for key in its chain

        Data<Key, Value> prev = null;
        while (head != null) {

            // Else keep moving in chain
            chainSize = chainSize + 1;
            prev = head;
            head = head.next;
        }

        return chainSize;
    }

    // Method find sum of claims in chain( in a state) by a given key
    public float claimsTot(Key key) {
        // Apply hash function to find index for given key
        int bucketIndex = getBucketIndex(key);
        int claimSum = 0;
        // Get head of chain
        Data<Key, Value> head = bucketArray.get(bucketIndex);
        // If key was not there
        if (head == null)
            claimSum = -1; // return -1 if there is no such item

        // Search for key in its chain

        Data<Key, Value> prev = null;
        while (head != null) {

            // Else keep moving in chain
            Drug myValue = (Drug) head.value;
            claimSum = claimSum + myValue.getClaims();
            prev = head;
            head = head.next;
        }
        return claimSum;
    }

    // Returns value for a key
    public Value get(Key key) {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        Data<Key, Value> head = bucketArray.get(bucketIndex);

        // Search key in chain
        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }

        // If key not found
        return null;
    }

    // Adds a key value pair to hash
    public void add(Key key, Value value) {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        Data<Key, Value> head = bucketArray.get(bucketIndex);

        // Check if key is already present
        while (head != null) {
            if (head.key.equals(key)) {
                // head.value = value;
                //System.out.println(" key is already present: " + key);
                // return; original code
            }

            // to do add the key
            // else {}
            head = head.next;
        }

        // Insert key in chain
        size++;
        head = bucketArray.get(bucketIndex);
        Data<Key, Value> newNode = new Data<Key, Value>(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);

        // If load factor goes beyond threshold, then
        // double hash table size
        if ((1.0 * size) / numBuckets >= 0.7) {
            ArrayList<Data<Key, Value>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(null);

            for (Data<Key, Value> headNode : temp) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }

    }

    // Driver method to test Map class
    // used geeksforgeeks site for separate chaining hashing function

    /*public static void main(String[] args) {
        Map<String, Drug> state = new Map<>();

        //ReadFile();

        Drug Alabama1 = new Drug("Alabama", "AMILORIDE HCL", 1141, 4686548);

        Drug Alabama2 = new Drug("Alabama", "AMILORIDE-HYDROCHLOROTHIAZIDE",
                1908, 3532871);
        // Drug Alabama3 = ;
        // Drug Alabama4 = ;
        // Drug California = new
        // Drug("California","AMIODARONE HCL",67942,73346);
        state.add("Alabama", Alabama1);
        state.add("Alabama", Alabama2);
        state.add("Alabama", new Drug("Alabama", "AMINOCAPROIC ACID", 80,
                4444966));
        state.add("Alabama", new Drug("Alabama", "AMOXAPINE", 466, 207423));
        state.add("Alabama",
                new Drug("Alabama", "AMOXICILLIN", 126845, 2424404));
        state.add("California", new Drug("California", "AMINOSYN II", 13,
                3267187));
        state.add("Toronto", new Drug("Toronto", "AMINOSYN II", 13, 3267187));
        // state.add("Alabama5-5",Alabama5 );
        // System.out.println(" Map size: "+ state.size());
        // System.out.println(state.remove("AMILORIDE HCL"));
        // System.out.println(map.remove("this"));
        System.out.println(" Map Column size : " + state.size());
        System.out.println(" Is it Empty : " + state.isEmpty());
        // System.out.println(" chainSize : " + chainSize(7));
        System.out.println(" chainSize : " + state.chainSize("Alabama"));
        System.out.println(" chainSize : " + state.chainSize("Toronto"));
        System.out.println(" claim totals : " + state.claimsTot("Alabama"));

        System.out.println(" hashByName:  " + Alabama1.hashByName());
        System.out.println(" hashByState:  " + Alabama1.hashByState());
        System.out.println(" hashByName:  " + Alabama2.hashByName());
        System.out.println(" hashByState:  " + Alabama2.hashByState());
        // System.out.println(" hashByName:  " + Alabama1.hashByName());
        // System.out.println(" hashByState:  " + Alabama1.hashByState());
        // System.out.println(" get:  " + state.get("Drug-1"));
        // System.out.println(" hash:  " + Alabama1.hash());

    }*/
}
