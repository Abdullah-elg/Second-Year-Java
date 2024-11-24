import java.util.Iterator;
import java.util.LinkedList;

/**
 * HashDictionary class implements the DictionaryADT interface using a hash table with separate chaining.
 */
public class HashDictionary implements DictionaryADT{
    private LinkedList<Data>[] table;
    private int numRecords;

    /**
     * Constructor for the HashDictionary class
     * @param size the size of the hash table
     */
    public HashDictionary(int size) {
        table = new LinkedList[size]; // set the hash table to be an array of LinkedLists of param size
        numRecords = 0; // initialize the number of records in the hash table to 0

        for (int i = 0; i < size; i++) { // initialize the array of LinkedLists
            table[i] = new LinkedList<>();
        }
    }

    /**
     * put a record in the hash table
     * @param record the record to be put in the hash table
     * @return 1 if there was a collision by the hash function, 0 otherwise
     */
    public int put(Data record) throws DictionaryException {
        String config = record.getConfiguration(); // get the configuration of the game
        int pos = hash(config); // hash the configuration to get the index in the array
        LinkedList<Data> curr = table[pos]; // get the LinkedList at the index
        Data data = curr.size() > 0 ? curr.get(0): null; // get the first element in the LinkedList if it exists
        Iterator<Data> iter = curr.iterator(); // create an iterator for the LinkedList

        while (data != null){ // iterate through the LinkedList
            if (data.getConfiguration().equals(config)) { // if the configuration already exists in the hash table throw an exception
                throw new DictionaryException();
            }
            data = iter.hasNext() ? iter.next(): null; // get the next element in the LinkedList if it exists
        } 

        curr.add(record); // add the record to the LinkedList
        numRecords++; // increment the number of records in the hash table
        return curr.size() > 1 ? 1: 0; // return 1 if there was a collision by the hash function, 0 otherwise
    }

    /**
     * remove a record from the hash table
     * @param config the configuration of the game to be removed
     */
    public void remove(String config) throws DictionaryException {
        int pos = hash(config); // hash the configuration to get the index in the array
        LinkedList<Data> curr = table[pos]; // get the LinkedList at the index
        Data data = curr.size() > 0 ? curr.get(0): null; // get the first element in the LinkedList if it exists
        Iterator<Data> iter = curr.iterator(); // create an iterator for the LinkedList

        while (data != null) { // iterate through the LinkedList
            if (data.getConfiguration().equals(config)) { // if the configuration exists in the hash table remove it
                curr.remove(data);
                numRecords--; // decrement the number of records in the hash table
                return;
            }
            data = iter.hasNext() ? iter.next(): null; // get the next element in the LinkedList if it exists
        }

        throw new DictionaryException(); // if the configuration does not exist in the hash table throw an exception
    }

    /**
     * get the score of a configuration in the hash table
     * @param config the configuration of the game
     * @return the score of the configuration
     */
    public int get(String config) {
        int pos = hash(config); // hash the configuration to get the index in the array
        LinkedList<Data> curr = table[pos]; // get the LinkedList at the index
        Data data = curr.size() > 0 ? curr.get(0): null; // get the first element in the LinkedList if it exists
        Iterator<Data> iter = curr.iterator(); // create an iterator for the LinkedList

        while (data != null) { // iterate through the LinkedList
            if (data.getConfiguration().equals(config)) { return data.getScore(); } // if the configuration exists in the hash table return the score
            data = iter.hasNext() ? iter.next(): null; // get the next element in the LinkedList if it exists
        }
        return -1; // if the configuration does not exist in the hash table return -1
    }

    /**
     * get the number of records in the hash table
     * @return the number of records in the hash table
     */
    public int numRecords() {
        return numRecords;
    }

    /**
     * hash the configuration to get the index in the array
     * @param config the configuration of the game
     * @return the index in the array
     */
    private int hash(String config) {
        int x = 11; // set the value of x to 11, small prime number that produces less collisions
        int m = table.length; // set the value of m to the size of the hash table
        int k = config.length(); // set the value of k to the length of the configuration

        int val = (int) config.charAt(k-1); // set the value of val to the last character in the configuration
        for (int i = k-2; i >= 0; i--) { // iterate through the configuration
            val = (val * x + (int) config.charAt(i)) % m; // hash the configuration using the polynomial hash equation to get the index in the array
        }
        return val; // return the index in the array
    }
}
