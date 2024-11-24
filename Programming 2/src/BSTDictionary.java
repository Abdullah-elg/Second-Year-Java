// Class BSTDictionary to store the root of the tree and perform operations on the tree
public class BSTDictionary implements BSTDictionaryADT {
    private BinarySearchTree tree;
    
    /**
     * Constructor for the BSTDictionary class.
     */
    public BSTDictionary() {
        this.tree = new BinarySearchTree();
    }

    /**
     * Returns the record with the given key.
     * @param k the key to search for
     * @return the record with the given key
     */
    public Record get (Key k) {
        BSTNode temp = this.tree.get(this.tree.getRoot(), k); // get the node with the key
        return temp != null ? temp.getRecord() : null; // return the record in the node if it exists
    }

    /**
     * Inserts a record into the dictionary.
     * @param d the record to insert
     * @throws DictionaryException if the key already exists in the dictionary
     */
    public void put (Record d) throws DictionaryException {
        try {
            this.tree.insert(this.tree.getRoot(), d); // insert the record into the tree
        } catch (DictionaryException e) {
            throw new DictionaryException("Key already exists in dictionary"); // throw an exception if the key already exists
        }
    }

    /**
     * Removes a record from the dictionary.
     * @param k the key of the record to remove
     * @throws DictionaryException if the key does not exist in the dictionary
     */
    public void remove (Key k) throws DictionaryException {
        try {
            this.tree.remove(this.tree.getRoot(), k); // remove the record with the key from the tree
        } catch (DictionaryException e) {
            throw new DictionaryException("Key does not exist in dictionary"); // throw an exception if the key does not exist
        }
    }

    /**
     * Returns the record with the smallest key.
     * @param k the key to search for
     * @return the record with the smallest key
     */
    public Record successor (Key k) {
        BSTNode temp = this.tree.successor(this.tree.getRoot(), k); // get the successor of the key
        if(temp == null || temp.isLeaf()) { // if the successor is null or a leaf, return null
            return null; 
        }
        return temp.getRecord();
    }

    /**
     * Returns the record with the largest key.
     * @param k the key to search for
     * @return the record with the largest key
     */
    public Record predecessor (Key k) {
        BSTNode temp = this.tree.predecessor(this.tree.getRoot(), k); // get the predecessor of the key
        if(temp == null || temp.isLeaf()) { // if the predecessor is null or a leaf, return null
            return null;
        }
        return temp.getRecord();
    }

    /**
     * Returns the record with the smallest key.
     * @return the record with the smallest key
     */
    public Record smallest () {
        BSTNode temp = this.tree.smallest(this.tree.getRoot()); // get the smallest node in the tree
        return temp.isLeaf() ? null : temp.getRecord(); // return the record in the node if it is not a leaf
    }

    /**
     * Returns the record with the largest key.
     * @return the record with the largest key
     */
    public Record largest () {
        BSTNode temp = this.tree.largest(this.tree.getRoot()); // get the largest node in the tree
        return temp.isLeaf() ? null : temp.getRecord(); // return the record in the node if it is not a leaf
    }
}
